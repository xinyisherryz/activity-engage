# Activity Engage

Group: cs5500-fire-emblem Members: Xinyi Zhang, Zeyu Shen

Hello! This is Activity Engage, a fitness tracking application. The application supports adding datapoints and a range of queries to help users access fitness activities and insights.

## System Architecture

The system architecture, data design, and technology stack consist of three main components:

1. Frontend: Developed using React ( code in frontend folder)
2. Backend: Built on the Spring Framework ( code in sprintCode folder)
3. Database: MongoDB

### Technology Stack

1. MongoDB: Chosen for its flexibility in handling unstructured data and deployed on MongoDB Atlas, a cloud-based database service, for seamless integration and accessibility.
2. Axios HTTP Library: Used for API requests
3. JWT Authentication Service: Used for user authentication

### Data Design

1. Our data design is illustrated through a UML graph, highlighting MongoDB Atlas as our preferred cloud-based database service.
2. We chose MongoDB as our database for this project due to its flexibility and ability to handle unstructured data. MongoDB‘s schema-free design enables easy adaptation to changes in data structure, providing high performance and scalability.

   ![Data UML](/readmeimg/data_uml.jpg "data uml")

## Features

We have created a seamless and engaging user experience through various pages, including the Landing Page, Activities List Page, Activity Details, and Dashboard. These pages allow users to interact effectively with the fitness data.

1. The Landing Page: designed to be engaging, showcases an overview of the website's features, enticing users to explore further and serving as an excellent starting point for familiarizing themselves with the application.

![Landing](/readmeimg/landing_page.jpg "landing")

2. Activities List Page: Our comprehensive activities page displays a complete list of activities along with key metrics for easy tracking. Users can view individual activities, providing them with various interaction options. Before login, client can see all the records, after login, user can see the records that belong to themselves.

![activity list no user](/readmeimg/activityListbefore.jpg "activity list no user")

Activity Page after login:

![Activity Page after login](/readmeimg/activity%20List%20after%20login.jpg "Activity Page after login")

3. Dashboard Page: The user dashboard, built using React, presents a detailed breakdown and analytics of user activities for a holistic view. Users can monitor their top 10 activity type based on duration and top 10 visited location. Similar to Activities List, before login, client can see all the records, after login, user can see the records that belong to themselves.

![dashbaord no user](/readmeimg/dashboard.jpg "dashbaord no user")

Dashboard Page after Login:

![Dashboard Page after Login](/readmeimg/Dashboard%20after%20login.jpg "Dashboard Page after Login")

4. Activity Page: This page allows users to view activity details for a specific day, enabling them to analyze their daily performance in-depth. This helps users understand their preferences and daily routines.

![activity detail](/readmeimg/activityDetail.jpg "activity detail")

5. Add Record Page: This page allows users to add activities to the database. (only visible after login, after refresh the page)

![Add Record Page](/readmeimg/addrecord.jpg "Add Record Page")

6. Update Record Page: This page enables users to search for personal records based on date and allows them to modify or delete the searched results. (only visible after login)

![Update Record Page](/readmeimg/updaterecord.jpg "Update Record Page")

7. Login Page/ SignUp Page: allows client to create new user and log in to see personal records
   ![Login](/readmeimg/login.jpg "Login")
   ![SignUp](/readmeimg/signup.jpg "SignUp")

## RESTful APIs

### Activity:

1. GET all activities

   `/api/v1/activities`

2. GET a single activity by ID

   `/api/v1/activities/id/{id}`

3. GET a single activity by date

   `/api/v1/activities/date/{date}`

4. POST create a new activity (parameters passed by JSON)

   `/api/v1/activities/post`

5. DELETE an activity

   `/api/v1/activities/delete/{id}`

6. PUT update an activity

   `/api/v1/activities/update/{id}`

7. GET the duration of a specific activity for each day

   `/api/v1/activities/durations/{activity}`

8. GET all types of activities

   `/api/v1/activities/activitytypes`

9. GET activity types and the sum of their durations

   `/api/v1/activities/activitydurations`

10. GET all locations

    `/api/v1/activities/locations`

11. GET top 10 most visited locations

    `/api/v1/activities/locations/top10`

12. GET simplified activities list

    `/api/v1/activities/simplified`

13. GET activities by user ID

    `/api/v1/activities/user/{userId}`

14. GET activities by user ID and date

    `/api/v1/activities/user/{userId}/date/{date}`

15. GET all activity types with durations by user ID

    `/api/v1/activities/activitydurations/user/{userId}`

### User:

1. GET all users

   `/api/v1/users`

2. GET a single user by ID

   `/api/v1/users/id/{id}`

3. POST create a new user (parameters passed by JSON)

   `/api/v1/users/post`

4. DELETE a user

   `/api/v1/users/delete/{id}`

5. PUT update a user

   `/api/v1/users/update/{id}`

6. POST login a user (parameters passed by JSON)

   `/api/v1/users/login`

7. POST logout a user

   `/api/v1/users/logout`

## Deployment On AWS:

1. Frontend: Amazon S3 bucket
2. Backend: AWS Elastic Beanstalk
   ![aws](/readmeimg/aws.jpg "aws")

## Updated Code/Test Metrics:

1. We conducted end-to-end tests that simulated user interactions to ensure a seamless user experience and demonstrate our commitment to providing a high-quality product
2. We Utilized Postman and Insomnia to test backend APIs, verifying their functionality and performance
3. We utilized CodeMR to assess the code quality of the application
   1. The evaluation revealed that the codebase exhibits strong and robust characteristics
   2. The codebase demonstrated good encapsulation, low complexity, high cohesion, and a manageable size
   3. The well-structured code makes it easy for developers to work with and maintain the application
   4. Code quality assessment with CodeMR confirms that the application's foundation is solid
      ![codemr](/readmeimg/codemr.jpg "codemr")

## CI Pipeline

1. We have created a ".github/workflows" directory in our GitHub repository and added a new YAML file that contains our CI pipeline configuration for a Java project with Maven. We saved and committed the YAML file to our repository. Then, we can edit the YAML file to define tasks, such as trigger events (like push or pull requests), runtime environment, dependencies, and scripts to run.
   ![ci](/readmeimg/cipipeline.jpg "ci")

## How to build the code

### Frontend:

Make sure you have the following prerequisites installed:

1. Node.js, version 16.x.x or higher
2. npm, version 7.x.x or higher

#### Dependencies

This project uses the following dependencies:

```
@emotion/react: ^11.10.6
@emotion/styled: ^11.10.6
@fortawesome/free-solid-svg-icons: ^6.4.0
@fortawesome/react-fontawesome: ^0.2.0
@mui/material: ^5.11.16
@testing-library/jest-dom: ^5.16.5
@testing-library/react: ^13.4.0
@testing-library/user-event: ^13.5.0
axios: ^1.3.5
bootstrap: ^5.2.3
jwt-decode: ^3.1.2
react: ^18.2.0
react-bootstrap: ^2.7.2
react-dom: ^18.2.0
react-material-ui-carousel: ^3.4.2
react-player: ^2.12.0
react-router-dom: ^6.10.0
react-scripts: 5.0.1
web-vitals: ^2.1.4
```

#### How to Run/Setup/Install the product

1. Clone this repository to your local machine.
2. Navigate to the project directory and run npm install to install all dependencies.
3. Once the dependencies are installed, you can start the development server by running npm start.
4. To run tests, execute npm test in the project directory.
5. To create a production build, run npm run build in the project directory. This will generate a build folder containing the production-ready files.
6. To eject the project configuration (not recommended unless necessary), run npm run eject. Note that this operation is irreversible.

### Backend:

#### Prerequisite

Make sure you have the following prerequisites installed:

1. Java, version 17 or higher
2. Maven, version 3.x.x or higher

#### Dependencies

This project uses the following dependencies:

```
spring-boot-starter-parent: 3.0.2
spring-boot-starter-data-mongodb
spring-boot-starter-web
spring-boot-devtools (runtime scope, optional)
lombok (optional)
spring-boot-starter-test (test scope)
spring-dotenv: 2.5.4
jjwt-api: 0.11.2
jjwt-impl: 0.11.2 (runtime scope)
jjwt-jackson: 0.11.2 (runtime scope)
spring-boot-starter-security
javax.servlet-api: 4.0.1 (provided scope)
```

#### How to Run

1. Clone this repository to your local machine.
2. Navigate to the project directory and run mvn clean install to install all dependencies and build the project.
3. Once the build is successful, you can start the application by running mvn spring-boot:run.

   ```
     .   ____          _            __ _ _
   /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
   ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
    \\/ **_)| |_)| | | | | || (\_| | ) ) ) )
   ' |\_\_**| .**|_| |_|_| |_\__, | / / / /
   =========|_|==============|\_**/=/_/_/\_/
   :: Spring Boot :: (v3.0.2)

   ...
   ...
   ...

   2023-04-27 15:25:37.483 INFO 19620 --- [main] cs5500.fireemblem.sprint.Application : Started Application in 3.185 seconds (JVM running for 3.529)
   ```

4. To run tests, execute mvn test in the project directory.
   After running the tests, you should expect to see output similar to the following:

   ```
   [INFO] -------------------------------------------------------
   [INFO]  T E S T S
   [INFO] -------------------------------------------------------
   [INFO] Running com.example.demo.MyTests
   ...
   ...
   ...
   [INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
   [INFO] ------------------------------------------------------------------------
   [INFO] BUILD SUCCESS
   [INFO] ------------------------------------------------------------------------
   [INFO] Total time:  10.223 s
   [INFO] Finished at: 2023-04-27T15:26:00-07:00
   [INFO] ------------------------------------------------------------------------

   ```

5. To package the application into a JAR file, run mvn package in the project directory. This will generate a target folder containing the JAR file.

6. To run the packaged JAR file, navigate to the target folder and execute java -jar <jar-file-name>.jar. Replace <jar-file-name> with the actual name of the JAR file generated in the previous step.

### Database Connection

1. Create your own MongoDB atlas host
2. Create a .env file like this:
   ```
   MONGO_DATABASE="sprint"
   MONGO_USER="sprint"
   MONGO_PASSWORD="fireemblem"
   MONGO_CLUSTER="cluster0.mdrrqyn.mongodb.net"
   ```

## Known Problems

1. We plan to improve the efficiency of GET requests for retrieving all records, as the current implementation is somewhat time-consuming. This may involve optimizing the database queries or introducing caching mechanisms to reduce the response time.

2. We plan to ensure a more secure and stable service by allowing logged-in users to only view their records. This will require revisions to the useEffect hooks and proper authentication and authorization checks on both the frontend and backend.

3. We plan to add filtering and ranking functionality to the Activity List page, which will help users better understand and manage their activities and records. This feature will allow users to sort and filter their records based on various criteria such as date, duration, or type of activity.
