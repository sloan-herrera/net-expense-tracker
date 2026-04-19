# NetExpenseTracker
NetExpenseTracker is a full-stack web application that allows users to securely manage and track their personal
expenses. Users can register, log in, and perform CRUD (create, read, update, and delete) operations on expenses
with support for pagination and filtering. The application is built using a Spring Boot backend, a React frontend,
a MySQL database, and is deployed using Render and Vercel. This project demonstrates full-stack development, secure
session-based authentication, and production deployment using a custom domain with HTTPS.

## Live Demo

https://netexpensetracker.dev

Users can register a new account to begin using the application.

## Features

- User registration and login using session-based authentication.
- Secure logout functionality with proper session invalidation.
- Create, read, update, and delete expenses.
- View expenses in a responsive layout (table on desktop, cards on mobile).
- Filter expenses by category, month, and year.
- Pagination support for navigating large sets of expenses.
- Expenses are displayed in descending order by date (most recent first).
- Frontend and backend validation for user and expense input.
- Secure password storage using BCrypt hashing.
- CSRF protection enabled for secure requests.
- Responsive design for desktop and mobile devices.
- Contact page for user support.

## How to Use

1. Open the application:  
    https://netexpensetracker.dev

2. Register a new account:
    - Navigate from the Sign In page to the Register page by clicking "Register Here" link below the form.
    - Enter a valid email and password. Password must be at least 12 characters long.
    - Submit the form to create an account. On account creation, you will be redirected to the Sign In page.

3. Log in:
    - Enter your email and password.
    - After logging in, you will be redirected to the dashboard.

4. Create an expense:
    - Enter the required fields:
        - Name (Name of expense)
        - Amount (Dollar amount)
        - Category (Dropdown selection)
        - Date (Date selection)
    - Click "Create Expense".
    - Expense will appear in the table (desktop) / cards (mobile) below the form.

5. View expenses:
    - Expenses are displayed in a table on desktop or larger screens and in card layout on mobile or smaller screens.
    - Each expense displays its name, amount, category, date, and action buttons for editing or deleting the specific expense.

6. Filter expenses:
    - Select category, month, and/or year from the dropdown menus. Month and year must be included together.
    - Click "Apply Filters" to filter the results.
    - Click "Clear Filters" to reset all filters.

7. Update an expense:
    - Click "Edit" on an expense.
    - Modify the fields in the modal.
    - Click "Update Expense" to update the expense.
    - Updated expense will appear in the table/card layout.

8. Delete an expense:
    - Click "Delete" on an expense.
    - Deleted expense will disappear from the table/card layout.

9. Contact support:
    - Navigate to the contact page in the navigation bar.
    - Fill out message form and click submit. 
    - Your default email client will open with a pre-filled message.
    - Send the email to contact support.

10. Log out:
    - Click "Logout" link in the navigation bar.
    - This will securely end your session.
    - You must be logged in to access the dashboard or contact page.

## Technologies Used

### Frontend
- React (TypeScript & Vite)
- React Bootstrap
- React Router
- Axios

### Backend
- Spring Boot
- Spring Security
- JPA / Hibernate

### Database
- MySQL

### Containerization
- Docker (Backend & Database)

### Deployment
- Vercel (Frontend)
- Render (Backend & Database)
- Namecheap (Domain & DNS)

### Code Editor
- Visual Studio Code

## Running Locally

1. Prerequisites:
    - Java JDK 25
    - Maven (or use mvnw)
    - Node.js (version 24)
    - NPM (comes with Node)
    - Docker & Docker Desktop

    NOTE:
    This project was developed and tested using the versions listed above.
    Using earlier versions may cause compatibility issues with dependencies or build configuration.

2. Create the following files:
    - .env (Root Directory)
    - .env (/frontend Directory)
    - application-local.properties (backend/src/main/resources Directory)

3. Configure environment variables:
    - Root .env (Docker & MySQL):
        ```env
        MYSQL_DATABASE=netexpensetracker
        MYSQL_USER=netexpenseuser
        MYSQL_PASSWORD=netexpensepassword
        MYSQL_ROOT_PASSWORD=rootpassword
        MYSQL_PORT=3306
        ```
    - Frontend .env:
        ```env
        VITE_API_URL=http://localhost:8080
        ```
    - application-local.properties:
        ```env
        spring.datasource.url=jdbc:mysql://localhost:3306/netexpensetracker?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
        spring.datasource.username=netexpenseuser
        spring.datasource.password=netexpensepassword

        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true
        ```

4. Start MySQL using Docker:
    - To start MySQL container in terminal:
        ```bash
        docker compose up -d
        ```
    
    WARNING: Docker Desktop must be running in the background on Windows.  
    WARNING: If you already have MySQL running on port 3306, stop the service.

    - To stop the container:
        ```bash
        docker compose down
        ```

    - To stop the container and remove all data from the database:
        ```bash
        docker compose down -v
        ```

5. Install all dependencies and run the backend:
    - To run the backend:
        ```bash
        cd ./backend
        ./mvnw clean install
        ./mvnw spring-boot:run
        ```

    - To stop running the backend:
        Ctrl + C in terminal running backend.

    - The backend will run on:  
        http://localhost:8080

6. Install all dependencies and run the frontend:
    - To run the frontend:
        ```bash
        cd ./frontend
        npm install
        npm run dev
        ```

    - To stop running the frontend:
        Ctrl + C in the terminal running frontend.

    - The frontend will run on:  
        http://localhost:5173

7. Open the application:
    - In a web browser, enter:  
        http://localhost:5173

## Future Improvements

- Dynamically retrieve categories, months, and years from backend.
- Add password reset functionality.
- Prompt user if they are sure they want to delete an expense.

## Author
Sloan Herrera  
B.S. Computer Science - Wilmington University

## License
This project was created for educational purposes.