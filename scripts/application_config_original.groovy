DEVELOPMENT.bagmeal.connection="jdbc:mysql://localhost.huds.harvard.edu:3306/dining"
DEVELOPMENT.bagmeal.driver="com.mysql.jdbc.Driver"

PRODUCTION.bagmeal.connection="jdbc:mysql://dsdiningdb.huds.harvard.edu:3306/dining"
PRODUCTION.bagmeal.driver="com.mysql.jdbc.Driver"

DEVELOPMENT.ifmn.connection="jdbc:oracle:thin:@ccdbp.vpcs.harvard.edu:8303:IFMN"
DEVELOPMENT.ifmn.driver="oracle.jdbc.driver.OracleDriver"

PRODUCTION.ifmn.connection="jdbc:oracle:thin:@ccdbp.vpcs.harvard.edu:8303:IFMN"
PRODUCTION.ifmn.driver="oracle.jdbc.driver.OracleDriver"

DEVELOPMENT.bbts.soapURL="http://ccappt.vpcs.harvard.edu:8080/tibet/TIAWebService"
PRODUCTION.bbts.soapURL="http://ccappd.vpcs.harvard.edu:8080/tibet/TIAWebService"



mail.transport.protocol="smtp"
mail.host="mailhub.harvard.edu"
mail.smtp.port=25

mail.cstl.from="vpcs_webmaster@harvard.edu"
mail.cstl.recipients= ["swathi_sammeta@harvard.edu","jeffrey_cuppett@harvard.edu",
  "scott_aldort@harvard.edu","saim_rafique@harvard.edu"]

//mail.cstl.to=

mail.cstl.body.start = '''
Hello,
<br/>
This message is to inform you that the following Bag Meal orders for the below Card Numbers could not be processed during this morning's scheduled job. 
<br/><br/>
Card Number Exceptions:
<br/>
'''

mail.cstl.body.end = '''
<br/>
An error occurred in BBTS when processing virtual TIA board adjustments. Please make sure of the following in BBTS:
<br/>
- The Card Number exists
<br/>
- The Card is connected to an Active Customer Record
<br/>
- The Customer has a correct meal plan assigned
<br/>
- If eligible, then create the adjustment manually in BBTS
<br/>
- If ineligible, contact the Customer to prevent future use of the Bag Meal web application
<br/><br/>
  '''

periodLookup{
  Breakfast="5"
  Lunch="13"
  Dinner="17"
}

locationLookup{
  FD{
    name='Annenberg'
    tia='BAG HUDSRES ANNENBERG'
//    email='dining_annenberg@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  AD{
    name='Adams'
    tia='BAG HUDSRES ADAMS HOUSE'
//    email='dining_adams@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  CB{
    name='Cabot'
    tia='BAG HUDSRES CABOT HOUSE'
//    email='dining_quad@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  CU{
    name='Currier'
    tia='BAG HUDSRES CURRIER HOUSE'
//    email='dining_currier@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  DN{
    name='Dunster'
    tia='BAG HUDSRES DUNSTER HOUSE'
//    email='dining_dunster_mather@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  EL{
    name='Eliot'
    tia='BAG HUDSRES ELIOT HOUSE'
//    email='dining_eliot_kirkland@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  KI{
    name='Kirkland'
    tia='BAG HUDSRES KIRKLAND HOUSE'
//    email='dining_eliot_kirkland@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  LE{
    name='Leverett'
    tia='BAG HUDSRES LEVERETT HOUSE'
//    email='dining_leverett@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  LO{
    name='Lowell'
    tia='BAG HUDSRES LOWELL HOUSE'
//    email='dining_lowell_winthrop@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  MA{
    name='Mather'
    tia='BAG HUDSRES MATHER HOUSE'
//    email='dining_dunster_mather@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  PF{
    name='Pforzheimer'
    tia='BAG HUDSRES PFORZHEIMER HOUSE'
//    email='dining_quad@harvard.edu'
    email="vpcs_webmaster@harvard.edu"

  }
  QU{
    name='Quincy'
    tia='BAG HUDSRES QUINCY HOUSE'
//    email='dining_quincy@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
  WI{
    name='Winthrop'
    tia='BAG HUDSRES WINTHROP HOUSE'
//    email='dining_lowell_winthrop@harvard.edu'
    email="vpcs_webmaster@harvard.edu"
  }
}