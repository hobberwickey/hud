/**
 * Sample runtime configuration:
 * Script parameters:   environment  bagmealuser bagmealpassword printername
 * Example:             PRODUCTION me password balto.cictr.com
 * 
 * Arguments:
 * @param ENVIRONMENT (PRODUCTION, DEVELOPMENT)
 *  - TEST and PRODUCTION utilize HUIT proxy
 * @param BAGMEALUSER
 * @param BAGMEALPASSWORD 
 * @param PRINTERNAME
 */

@Grab(group='org.apache.pdfbox', module='pdfbox', version='2.0.4')
@Grab(group='com.craigburke.document', module='pdf', version='0.5.0')
@Grab('mysql:mysql-connector-java:5.1.39')
@GrabConfig(systemClassLoader=true)

import java.awt.print.PrinterJob;
import java.io.File;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.text.SimpleDateFormat;
import groovy.sql.Sql

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.craigburke.document.builder.PdfDocumentBuilder

if (args.length != 4) {
  println "NEED TO DEFINE ENVIRONMENT ARGUMENTS... EXITING | $args"
  System.exit(-1)
}

def config = new ConfigSlurper().parse(new File('application_config.groovy').toURL())
def env = this.args[0]
def printerName = this.args[3]
Sql bagMealSql

SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

bagMealSql = Sql.newInstance(config."$env".bagmeal.connection,args[1], args[2] , config."$env".bagmeal.driver)

def orders = []
//Query DB
bagMealSql.rows('''
  SELECT 
    DATE_FORMAT(op.pickup_date, '%Y-%m-%d') AS delivery_date, 
    DATE_FORMAT(op.pickup_time,'%H:%i:%s') AS delivery_time, 
    o.id AS order_number,
    u.huid AS user_id, 
    u.first_name AS user_first_name,
    u.last_name AS user_last_name,
    dh.code AS location_id, 
    dh.name AS location_name, 
    m.name AS meal_type
  FROM order_pickup AS op
  LEFT JOIN orders AS o ON op.orders_id = o.id
  LEFT JOIN dining_hall AS dh ON o.dining_hall_id = dh.id
  LEFT JOIN menu AS mn ON o.menu_id = mn.id
  LEFT JOIN user AS u ON o.user_id = u.id
  LEFT JOIN meal AS m ON mn.meal_id = m.id
  WHERE 
    date(op.pickup_date) = CURDATE() AND
    o.canceled != true
'''
).each { order ->
  order["selections"] = []
  orders.push(order)

  bagMealSql.rows('''
    SELECT 
      ms.id AS id,
      mi.name AS menu_item,
      GROUP_CONCAT(mo.name ORDER BY mo.id) AS item_options
    FROM menu_selection AS ms
    LEFT JOIN menu_item AS mi ON ms.menu_item_id = mi.id
    LEFT JOIN menu_selection_menu_item_options AS msmio on msmio.menu_selection_id = ms.id
    LEFT JOIN menu_item_option AS mo ON msmio.menu_item_option_id = mo.id
    WHERE ms.orders_id = ?
    GROUP BY ms.id
    ORDER BY ms.order_index, ms.snack_index
  ''',[order.order_number]
  ).each { selection ->
    order["selections"].push(selection)
  }
}

bagMealSql.close()

//Build PDF
def builder = new PdfDocumentBuilder(new File('./example-order.pdf'))

if (orders.size() == 0){
  //TODO: Handle no orders
  return
}

String LOGO_URL = "https://s3.amazonaws.com/mediumbold-huds/images/logo.png"
byte[] logo = new URL(LOGO_URL).bytes

def customTemplate = {
    'document' font: [family: 'Helvetica', size: 12.pt], margin: [top: 1.5.inches] 
    'paragraph' font: [color: '#000000'], margin: [top: 0.inches, left: 0.inches, right: 0.inches, bottom: 0.inches] 
    'heading1' margin: [top: 0.inches, left: 0.inches, right: 0.inches, bottom: 0.25.inches]
    'heading2' margin: [top: 0.inches, left: 0.inches, right: 0.inches, bottom: 0.inches]
}

builder.create {
  document(template: customTemplate, font: [family: 'Helvetica', size: 14.pt], margin: [top: 0.75.inches]) {
    paragraph {
      image(data: logo, height: 18.px, width: 411.px, name: 'logo.png')
    }

    heading1 "Orders (By HUID)", font: [size: 20.pt]

    orders.eachWithIndex{ order, index ->
      heading2 "HUID: ${ order.user_id }", font: [size: 12.pt, bold: true]
      heading2 "${ order.location_name }, ${ order.meal_type } at ${ order.delivery_time } on ${ order.delivery_date }", font: [size: 14.pt, italic: true]
      
      paragraph(margin: [bottom: 0.25.inches]) {
        text order.selections.collect { selection ->
          def name = selection.menu_item
          def options = selection.item_options != null && selection.item_options != "" ? "(${ selection.item_options.split(',').join(', ') })" : ""

          return "${ name } ${ options }"
        }.join(", ")    
      }
    }
  }
}


orders.each{
  println it.user_last_name + ", " + it.user_first_name + ": " + it.selections.size()
}

PDDocument document = PDDocument.load(new File("./example-order.pdf"));

// PrintService myPrintService = findPrintService(printerName);

// PrinterJob job = PrinterJob.getPrinterJob();
// job.setPageable(new PDFPageable(document));
// job.setPrintService(myPrintService);
// job.print();
document.close();

def findPrintService(String pName) {
  PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
  for (PrintService printService : printServices) {
    println printService.getName().trim()
    if (printService.getName().trim().equals(pName)) {
      return printService;
    }
  }
  return null;
}
