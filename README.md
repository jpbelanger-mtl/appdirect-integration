# appdirect-integration

build with: ./gradlew build jacocoTestReport

## Spring profiles:
* dev: runs a local hsql database
* junit: runs a in memory database
* prod: must provide a application-prod.properties based on the template
 
Required parameters with dev profile:
* -DoauthKey=${Your appdirect key} -DoauthSecret=${your app direct secret} -Dspring.profiles.active=dev

