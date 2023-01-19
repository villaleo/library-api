# Library API
An API for checking out and returning books built with Java using the SpringBoot framework. This is a project for
CST 438 (Software Engineering) at CSU Monterey Bay.

This assignment did not require unit tests to be coded. Instead, a document (LibraryAPI Unit Test Report.pdf) covering
the different use cases and paths can be found in the root project directory.

## Usage
### Setup the environment
1. Download and open the repository.
2. Check `pom.xml` for the project dependencies and download them.
3. Create the config file `application.properties` in the path `src/main/resources`. This is where database
configurations will go. There's an example of how I have mine configured.

```properties
# database properties
spring.datasource.url = jdbc:mysql://localhost:3306/library_api
spring.datasource.username = <YOUR USERNAME>
spring.datasource.password = <YOUR PASSWORD>
spring.jpa.show-sql = true

server.servlet.session.persistent = false
spring.jpa.hibernate.ddl-auto = update
spring.datasource.hikari.maximum-pool-size = 5
```

### Creating the database tables
There are two tables used in this project: a `Book` and `Patron` table.
The code to generate these tables are in the file `src/main/resources/data.sql`. This file will create a new schema
`library_api` with the respective tables.

If you run the file, the tables and schema will be created. If no action is
done at this stage, I believe the database tables will automatically be created when the program is executed. This is 
not recommended, however since the tables will be placed in the current default schema set in MySQL.

### Building and Running
In this project, the main method is found in
`src/main/java/com/libraryapi/LibraryApiApplication.java`. Since the project is built using the SpringBoot framework,
the main method must be annotated with `@SpringBootApplication`.

Execute the main method to run the application. 

## Endpoints
I recommend using a tool like Postman to execute the requests.
* `book/{book_id}/checkout/{patron_id}`: Check out a book
* `book/{book_id}/return`: Return a book
* `patron/{patron_id}/checkouts`: List a patron's checkouts
* `patron/{patron_id}`: View a patron's information

**Additional endpoint information can be found by reading the code for the controller found at 
`src/main/java/com/libraryapi/controller/LibraryController.java`.**
