# campaign-manager
Example POC using Google Place API and Twilio SMS service

Requires Java 7 or higher.

Copy these values to src/main/resources/app.properties.template and rename the file to src/main/resources/app.properties
Then you can run:

    mvn clean package jetty:run

a curl command like:

    curl -v http://localhost:8080/campaign?location=&id=

might cause something to be texted to 
