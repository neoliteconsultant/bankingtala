Author: Tony Afula Maganga
Project: Tala Banking API
Email: tonyafula@gmail.com
Date: July 27, 2017


### About

Tala Banking API is a RESTful web service built using Spring MVC framework. It is used to 
conduct banking transactions (Balance Inquiry, Withdrawal and Deposits)
over HTTP protocol for Tala Bank. 



### Prerequisites

Before running the application, ensure you have installed and configured
the following tools

- JDK 1.7 or above.
  Ensure JAVA_HOME environment variable is set and points to your JDK installation.
  
- MySQL Server 5.5+/XAMPP/LAMP
- Maven 3.3.9 or later. Maven is a build automation tool for Java. Read more at https://maven.apache.org/
- Application server (e.g Tomcat, JBOSS EAP,WebLogic, WebSphere, etc) for deploying the application.
- Rest client (e.g Chrome Advanced Rest Client, Postman, etc) or curl for consuming the API endpoints.



### Installation
1. Copy the project,bankingtala, to any directory of your choice.

2. Navigate to the sql resources directory (bankingtala\src\main\resources\sql) in a command line. The sql directory
   contains the database schema for the project.
   
3. Import the sql script(taladb.sql) in a shell/command line or use a database administration
   tool such as phpmyadmin.
   
   NB:
   ==
   The sql script inserts two account numbers by default for testing: 01103455 for Collins Liysosi
   and 01106711 for Magna Sundstrom.
   use these account numbers for testing the endpoints.
   
4. If the SQL script runs successfuly, navigate in a shell/command line to the root folder of the project 
   containing pom.xml file: bankingtala\
   
5. Run mvn compile war:war in a shell. 
    $ mvn compile war:war 
	
   mvn compile war:war is a maven goal that compiles and packages
   the application as WAR file.
   

6. Start the application server installed in the Prerequisites section.


7. Open the administration console of the application server and deploy the war file (tala.war) located in bankingtala\target directory.



### API Usage

The API consists of three endpoints: balance, withdrawal, and deposit. To consume
any of these endpoints, follow these instructions.


### Balance

To do a balance inquiry, paste the following URL in a Rest Client.


GET http://[hostname]:[port]/tala/api/v1/balance/{accountNumber}

NB
-[hostname] and [port] represent the host name and port of your application server in step (7.), respectively
-{accountNumber} is a placeholder for account number replace it with
 a real accountNumber (01103455 or 01106711). i.e.,
   
   http://localhost:8080/tala/api/v1/balance/01103455



### Deposits

To make a deposit, paste the following URL in a Rest Client.


PATCH http://[hostname]:[port]/tala/api/v1/deposit/{accountNumber}/{amount}

Where 
-[hostname] and [port] represent the host name and port of your application server, respectively
-{accountNumber} and {amount} are placeholders for account number and amount, respectively.



### Withdrawal

To make a withdrawal, paste the following URL in a Rest Client.

PATCH http://[hostname]:[port]/tala/api/v1/withdrawal/{accountNumber}/{amount}

Where 
-[hostname] and [port] represent the host name and port of your application server, respectively
-{accountNumber} and {amount} are placeholders for account number and amount, respectively.







### Test
To run unit tests, run mvn test in a shell/command line from the project root.
  $ mvn test
 
 
 

### Javadoc 
To generate javadocs, run mvn javadoc:javadoc goal in a shell from the project root.
  $ mvn javadoc:javadoc

The generated javadoc is located in target/site/apidocs
  



### Troubleshooting

If you encounter any problems while deploying or using
the application try the following steps


### 404 error when calling an endpoint

Check proper formatting of the URL and ensure that it matches
the URL specified in the documentation

### 405 - Request method not supported

Check if you are using the correct HTTP request method for the 
API endpoint.


### 'mvn' is not recognised as an internal or external command.

Add the bin directory of the maven installation to the PATH environment variable.


### Access denied for user 'talauser'@'localhost' (using password: YES)

You probably have an anonymous user ''@'localhost' or ''@'127.0.0.1'.

As per the manual:

When multiple matches are possible, the server must determine which of them to use. It resolves this issue as follows: (...)

When a client attempts to connect, the server looks through the rows [of table mysql.user] in sorted order.
The server uses the first row that matches the client host name and user name.
(...) The server uses sorting rules that order rows with the most-specific Host values first. Literal host names [such as 'localhost'] and IP addresses are the most specific.
Hence, such an anonymous user would "mask" any other user like '[any_username]'@'%' when connecting from localhost.

'talauser'@'localhost' does match 'talauser'@'%', but would match (e.g.) ''@'localhost' beforehands.

The recommended solution is to drop this anonymous user (this is usually a good thing to do anyways).

   
   





#######################################
 END
#######################################