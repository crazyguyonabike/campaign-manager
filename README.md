# campaign-manager
Example POC using Google Place API and Twilio SMS service

Requires Java 7 or higher.

You'll need an API KEY from [Google](https://developers.google.com/maps/web/)
and an account SID, auth token and phone number from [Twilio](https://www.twilio.com/docs/api/rest)

Copy these values to src/main/resources/app.properties.template and
rename the file to src/main/resources/app.properties

You also need to create an identifier (arbitrary) and add your
identifier and YOUR phone number to
src/main/resources/test-data.sql.template and copy that file to
src/main/resources/test-data.sql

Then you can run:

    mvn clean package jetty:run

a curl command like:

    curl -v 'http://localhost:8080/?location=41.5223919,-88.1225628&id=<identifier>'

might cause something to be texted to YOUR phone number.

The test data (dairyqueen.campaign) is all the Dairy Queen's around
the Chicago area. A degree of movement is about 69 miles.