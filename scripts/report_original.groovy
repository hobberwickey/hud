/**
 * Sample runtime configuration:
 * Groovy parameters:   -Djsse.enableCBCProtection=false -Dhttp.proxyHost=sec-bc.soc.harvard.edu -Dhttp.proxyPort=8080 
 *              -Dhttps.proxyHost=sec-bc.soc.harvard.edu -Dhttps.proxyPort=8080
 * Class path:        mailapi.jar:smtp-1.4.5.jar:ojdbc7.jar:mysql-connector-java-5.0.8-bin.jar
 * Script parameters:   environment  bagmealuser bagmealpassword csgolduser csgoldpassword
 * Example:         PRODUCTION me password sys_insadmin password
 * 
 * Arguments:
 * @param ENVIRONMENT (PRODUCTION, DEVELOPMENT)
 *  - TEST and PRODUCTION utilize HUIT proxy
 * @param BAGMEALUSER
 * @param BAGMEALPASSWORD 
 * @param IFMNUSER
 * @param IFMNPASSWORD
 */

@Grab(group='com.github.groovy-wslite', module='groovy-wslite', version='1.1.0')
//@Grab(group='joda-time', module='joda-time', version='2.8.2')
@Grab(group='joda-time', module='joda-time', version='2.5')


import groovy.sql.Sql

import java.text.SimpleDateFormat;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import wslite.soap.*

import org.joda.time.DateTime

//Make sure the correct amt of arguments are given
if(args.length != 5 ) {
  println "NEED TO DEFINE ENVIRONMENT ARGUMENTS... EXITING | $args"
  System.exit(-1)
}
def config = new ConfigSlurper().parse(new File('ApplicationProperties.groovy').toURL())
def String bbtsSoapAction = "http://ws.web.tibet.services.blackboard.com/TIAWebService/boardRequest"

def env = this.args[0]
Sql bagMealSql
Sql ifmnSql

SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
def bagclients = new SOAPClient(config."$env".bbts.soapURL)

bagMealSql = Sql.newInstance(config."$env".bagmeal.connection,args[1],
    args[2] , config."$env".bagmeal.driver)

ifmnSql = Sql.newInstance(config."$env".ifmn.connection,args[3],
    args[4] , config."$env".ifmn.driver)

def soapEntryList = []
def soapUserErrors = [:]
def exceptions
//Query DB
bagMealSql.eachRow('''
      select DISTINCT user_id, delivery_date, delivery_time, location_id, meal_type
      from orders where delivery_date = CURDATE() AND cancel_date IS NULL
    '''
    ){ bagMealrow ->
      println bagMealrow
      def configElt = config.locationLookup.(bagMealrow.location_id)
      if (!configElt.tia){
        exceptions += "Location could not be cross referenced for ID ${bagMealrow.location_id}"
      }
      else{
        ifmnSql.eachRow('''
          SELECT CARD_NUMBER from CLONE.CLONE_CARD 
          WHERE PERSON_NUMBER = ?
        ''',[bagMealrow.user_id]){ ifmnRow ->
              if (!ifmnRow.CARD_NUMBER){
                exceptions += "Could not look up ISO # for ${bagMealrow.user_id}"
              }
              else{
                println ifmnRow

                int mealTypeTranslation = config.periodLookup.get(bagMealrow.MEAL_TYPE).toInteger()
                DateTime postDate = new DateTime(bagMealrow.delivery_date).withHourOfDay(mealTypeTranslation)

                soapEntryList << [tiaKey:configElt.tia, card:ifmnRow.CARD_NUMBER,
                  mealPeriod:sdf.format(postDate.toDate()),email:configElt.email]
              }
            }
      }
    }

bagMealSql.close()
ifmnSql.close()


if (exceptions){
  exceptions.each{ println it }
  System.exit(-1)
}
println "Transferring data through TIA..."
soapEntryList.each{soapEntryInstance->
  String p = soapEntryInstance.email
  def response = bagclients.send(SOAPAction:bbtsSoapAction) {
    envelopeAttributes "xmlns:ws":"http://ws.web.tibet.services.blackboard.com/"
    body {

      "ws:board"{
        client("mealsauditor")
        tiakey(soapEntryInstance.tiaKey)
        card(soapEntryInstance.card)
        tender("4")
        amount("1")
        offline("true")
        date(soapEntryInstance.mealPeriod)
      }
    }
  }
  //def list = new XmlSlurper().parseText(response.boardResponse as String)
  if (response.boardResponse.toString().contains("<success>false</success>")){
    println "Card: ${soapEntryInstance} | ${response.boardResponse}"

    def n = soapUserErrors.get("${p}")
    if (n){
      n << soapEntryInstance.card
    }
    else{
      soapUserErrors += ["${p}":[soapEntryInstance.card]]
    }
  }
}
println soapUserErrors


if (env == "PRODUCTION"){
  soapUserErrors.each{
    StringBuilder message = new StringBuilder(config.mail.cstl.body.start )

    it.value.each{ message.append "<strong>$it</strong><br/>" }

    message.append(config.mail.cstl.body.end)

    List recipients = config.mail.cstl.recipients
    recipients += it.key //add appropriate dining hall mailbox in addition to global recipients

    Session lSession = Session.getDefaultInstance(config.toProperties(),null);
    MimeMessage msg = new MimeMessage(lSession);

    ArrayList emailTos = new ArrayList()
    recipients.each{
      emailTos.add(new InternetAddress(it));
    }

    InternetAddress[] to = new InternetAddress[emailTos.size()];
    to = (InternetAddress[]) emailTos.toArray(to);
    msg.setRecipients(MimeMessage.RecipientType.TO,to);
    msg.setFrom(new InternetAddress(config.mail.cstl.from));
    msg.setSubject("Bag Meal Processing Error");
    msg.setContent(message.toString(),"text/html; charset=utf-8")

    Transport transporter = lSession.getTransport("smtp");
    transporter.connect();
    transporter.send(msg);
  }

}