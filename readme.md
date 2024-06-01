1. **Prerequisites**: Make sure you have the following installed on your system:
    - Java Development Kit (JDK) 11 or later
    - Maven
    - An IDE like IntelliJ IDEA

2. **Open the Project**: Open IntelliJ IDEA and select `Open`. 
Navigate to the directory where you cloned the project and select the project to open it.

3. **Import Maven Dependencies**: The project is a Maven project, 
so you need to import the Maven dependencies. IntelliJ IDEA usually does this automatically, 
but if it doesn't, you can manually trigger it by right-clicking on the `pom.xml` file and selecting `Maven` -> `Reimport`.

4. **Set up the Database**: As the project uses SQL, 
you need to set up a database. The details for setting up the database should be in the `application.properties`file.
Mysql is used in this project.

5. **Access the Application**: 
If the application starts successfully, you should be able to access it. If it's a web application,
You can access http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/ to see the API documentation and test the endpoints.
Or you can import api-docs.json file to postman to see the endpoints.