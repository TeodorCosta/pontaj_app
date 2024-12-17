
# Pontaj App  

Pontaj App is a time tracking application built using the Spring Boot Framework, Thymeleaf, and Spring Security. It follows the Model-View-Controller (MVC) architecture to provide a robust and maintainable solution for managing employee work hours.  

## Features  
- Add and manage employees and projects.  
- Record hours worked by employees on specific projects on specific dates.  
- Generate detailed reports:  
  - View reports for individual employees.  
  - View reports for specific projects.  
  - View an overall summary report.  
- Export data to Excel for further processing or record-keeping.  
- Secure login system using Spring Security.  

## Technologies Used  
- **Backend**: Spring Boot (with Spring Security for authentication and authorization)  
- **Frontend**: Thymeleaf templating engine  
- **Database**: MySql Database 
- **Build Tool**: Maven  

## Prerequisites  
- Java 17 or higher  
- Maven 3.8+  
- A web browser  

## Installation  
1. Clone the repository:  
   ```bash  
   git clone <repository-url>  
   cd pontaj-app  
Update the database configuration in application.properties:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/pontajdb
spring.datasource.username=root
spring.datasource.password=password123
spring.resources.cache.cachecontrol.no-cache=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
Build and run the application:

bash
Copy code
mvn spring-boot:run  
Open your browser and go to http://localhost:80.

Usage
Login: Access the application using secure login credentials.
Add Employees and Projects: Navigate to the respective sections to add new employees or projects.
Track Hours: Log hours worked by employees on projects with specific dates.
View Reports: Access detailed reports based on projects, employees, or overall data.
Export Data: Export reports or raw data to Excel files for offline use.
Screenshots
Include screenshots here for key features like the dashboard, report generation, and data export.

Future Enhancements
Add support for project timelines and deadlines.
Implement advanced filtering and search for reports.
Enhance UI/UX with modern styling frameworks like Bootstrap.
Contributing
Contributions are welcome! Please fork the repository and submit a pull request with your improvements or bug fixes.

License
This project is licensed under the MIT License.

Acknowledgments
Built with the support of the Spring Boot and Thymeleaf communities.
Inspired by the need for an efficient time-tracking solution.
