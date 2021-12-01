#### About

Tala Banking is a REST API built using Spring MVC framework. It exposes 
endpoints for banking transactions (Balance Inquiry, Withdrawal and Deposits)
over HTTP protocol. 



#### Prerequisites

Before running the application, ensure you have installed and configured
the following tools

- JDK 1.7 or above.
- MySQL Server 5.5+/XAMPP/LAMP
- Maven 3.3.9 or later. Maven is a build automation tool for Java. Read more at https://maven.apache.org/
- Application server (e.g Tomcat, JBOSS EAP,WebLogic, WebSphere, etc) for deploying the application.
- Rest client (e.g Chrome Advanced Rest Client, Postman, etc) or curl for consuming the API endpoints.



#### Installation
1. Navigate to the project root.

   ```
   $ cd bankingtala
   ```

2. Navigate to the sql resources directory (bankingtala\src\main\resources\sql) in a command line. 
   The sql directory contains the database schema for the project.
   
3. Import the database script.

   ```
   $ mysql -u username -p taladb < taladb.sql
   ```
   
   >The sql script inserts two account numbers by default for testing: 01103455 for Collins Liysosi
   >and 01106711 for Magna Sundstrom.
   >use these account numbers for testing the endpoints.
   
4. If the SQL script runs successfuly, navigate in a shell/command line to the root folder of the project 
   containing pom.xml file: bankingtala\.
   
5. Run mvn compile war:war in a shell. 
    ```
    $ mvn compile war:war 
    ```
	
   mvn compile war:war is a maven goal that compiles and packages
   the application as WAR file.
  
6. Start the application server installed in the Prerequisites section.

7. Open the administration console of the application server and deploy the war file (tala.war) located in bankingtala\target directory.



#### API Usage

The API consists of three endpoints: balance, withdrawal, and deposit. To consume
any of these endpoints, follow these instructions.


##### Balance

To do a balance inquiry, paste the following URL in a Rest Client.

```
$ curl http://[hostname]:[port]/tala/api/v1/balance/{accountNumber}
```

Where
- [hostname] and [port] represent the host name and port of your application server, respectively.
- {accountNumber} is a placeholder for account number.
    


##### Deposits

To make a deposit, paste the following URL in a Rest Client.

```
$ curl --request PATCH http://[hostname]:[port]/tala/api/v1/deposit/{accountNumber}/{amount}
```

Where
- {amount} is the amount to deposit.

##### Withdrawal

To make a withdrawal, paste the following URL in a Rest Client.
```
$ curl --request PATCH http://[hostname]:[port]/tala/api/v1/withdrawal/{accountNumber}/{amount}
```

Where
- {amount} is the amount to withdraw.

#### Test
To run unit tests, run mvn test in a shell/command line from the project root.
  ```
  $ mvn test
  ```
 
 
 

#### Javadoc 
To generate javadocs, run mvn javadoc:javadoc goal in a shell from the project root.
  ```
  $ mvn javadoc:javadoc
  ```

The generated javadoc is located in target/site/apidocs
  
  