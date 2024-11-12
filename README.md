- *Java Development Kit (JDK)*: Java 8 or higher.
- *Apache Tomcat*: Version 9 or higher for optimal compatibility.
- *Database*: The application requires a database for storing user and product information. Supported databases may vary, but the configuration should be adjusted in Configuration.java to match the specifics of your chosen database.

## Getting Started

### Apache Tomcat Setup

To deploy OnlineShopApp on Apache Tomcat:

1. *Download and Install Tomcat*: Get Apache Tomcat from [https://tomcat.apache.org/](https://tomcat.apache.org/), following installation instructions for your OS.
2. **Place Files in webapps Directory**: Move the project files into the webapps directory within Tomcat.
3. *Configure Database Settings*: Update the Configuration.java file with the database URL, username, and password to connect to your database instance.
4. *Start Tomcat*:
   ```bash
   ./catalina.sh start
