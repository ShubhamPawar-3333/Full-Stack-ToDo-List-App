# Todo List Application

## Overview
This is a full-stack Todo List application built with React (frontend), Spring Boot (backend), and PostgreSQL (database, using H2 for development). The application allows users to manage personal task lists with CRUD operations and secure authentication.

## Project Status
- **Phase**: Integration
- **Completed Tasks**:
  - Defined requirements (see `docs/requirements.md`).
  - Designed architecture (see `docs/architecture.md`).
  - Set up Git repository with `.gitignore` for Java and Node.js.
  - Set up development environment (see `docs/dev-environment.md`).
  - Planned CI/CD pipeline (see `docs/ci-cd-pipeline.md`).
  - Set up Spring Boot project in `backend/` directory.
  - Designed database schema for Users and Tasks tables using JPA, Lombok, and TaskStatus enum.
  - Implemented REST APIs for task CRUD operations (`POST /tasks`, `GET /tasks`, `GET /tasks/{id}`, `PUT /tasks/{id}`, `DELETE /tasks/{id}`).
  - Set up modern JWT authentication with `/auth/register` and `/auth/login` endpoints using Spring Security and jjwt 0.12.6.
  - Integrated H2 database with JPA/Hibernate for User and Task entities, supporting authentication and CRUD operations.
  - Added input validation for user and task inputs using @Valid and custom validators, with global exception handling.
  - Wrote unit tests for controllers, services, and repositories using JUnit 5 and Mockito in `com.portfolio.todolist.ToDoListApplication`.
  - Set up React project with Vite, TailwindCSS, Axios, and React Router in `frontend/` directory.
  - Designed UI components (`TaskCard`, `LoginForm`, `RegisterForm`, `TaskList`, `Header`).
  - Implemented state management with React Context (`TaskContext`, `UserContext`).
  - Set up routing for `/login`, `/register`, `/tasks` using React Router.
  - Integrated API calls with Axios for task CRUD and authentication.
  - Added client-side form validation for task creation and user inputs.
  - Ensured responsiveness with TailwindCSS.
  - Configured CORS in `SecurityConfig.java` to allow requests from `http://localhost:5173` for `/auth/**` and `/tasks/**`.
  - Integrated authentication with JWT token handling and local storage.
- **Current Task**: Test End-to-End Flow (Phase 4).

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd todo-list-app
   ```
2. **Install Dependencies**:
   - Follow instructions in `docs/dev-environment.md` for Node.js, Java, and Maven setup.
   - For backend:
     ```bash
     cd backend
     mvn install
     ```
     - Ensure your IDE supports Lombok (e.g., enable Annotation Processing in IntelliJ IDEA or install the Lombok plugin).
   - For frontend:
     ```bash
     cd frontend
     npm install
     ```
3. **Run the Application**:
   - Start the backend:
     ```bash
     cd backend
     mvn spring-boot:run
     ```
     - Runs on `http://localhost:8080`.
     - Access the H2 console at `http://localhost:8080/h2-console` (username: `sa`, password: empty).
   - Start the frontend:
     ```bash
     cd frontend
     npm run dev
     ```
     - Runs on `http://localhost:5173` (Vite default port).
   - **CORS Configuration**:
     - The backend allows cross-origin requests from `http://localhost:5173` for `/auth/**` and `/tasks/**`.
     - Ensure the frontend runs on `http://localhost:5173` to avoid CORS issues.
4. **Verify Database**:
   - Check `USERS` table (columns: `ID`, `USERNAME`, `PASSWORD`, `TASKS`).
   - Check `TASKS` table (columns: `ID`, `TITLE`, `DESCRIPTION`, `STATUS`, `USER_ID`).
5. **Test Authentication Flow**:
   - Open `http://localhost:5173` in a browser.
   - Navigate to `/register`, enter a username (min 3 characters) and password (min 6 characters, with a letter and number), and submit.
   - On success, you’re redirected to `/tasks`. If registration fails (e.g., username taken), an error displays.
   - Log out via the header’s “Logout” button, clearing the JWT token.
   - Navigate to `/login`, enter credentials, and submit to access `/tasks`.
   - Verify protected routes (e.g., `/tasks`) require authentication; unauthenticated users are redirected to `/login`.
6. **Test APIs**:
   - Use tools like Postman or curl to test authentication and task endpoints:
     ```bash
     # Register a user (stores in USERS table)
     curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d '{"username":"testuser","password":"testpass"}'
     # Login to get JWT token
     curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"username":"testuser","password":"testpass"}'
     # Save the returned JWT token
     TOKEN="your_jwt_token_here"
     # Create a task (stores in TASKS table with USER_ID)
     curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d '{"title":"Test Task","description":"Test Description","status":"PENDING"}'
     # Get all tasks for the authenticated user
     curl http://localhost:8080/tasks -H "Authorization: Bearer $TOKEN"
     # Get a task by ID
     curl http://localhost:8080/tasks/1 -H "Authorization: Bearer $TOKEN"
     # Update a task
     curl -X PUT http://localhost:8080/tasks/1 -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d '{"title":"Updated Task","description":"Updated Description","status":"COMPLETED"}'
     # Delete a task
     curl -X DELETE http://localhost:8080/tasks/1 -H "Authorization: Bearer $TOKEN"
     ```
   - Test input validation (expect HTTP 400 for invalid inputs):
     ```bash
     # Invalid user registration (empty username)
     curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d '{"username":"","password":"testpass"}'
     # Expected response: {"username":"Username is required"}
     # Invalid user registration (password too short)
     curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d '{"username":"testuser","password":"short"}'
     # Expected response: {"password":"Password must be between 6 and 100 characters"}
     # Invalid task creation (empty title)
     curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d '{"title":"","description":"Test Description","status":"PENDING"}'
     # Expected response: {"title":"Title is required"}
     # Invalid task creation (missing status)
     curl -X POST http://localhost:8080/tasks -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -d '{"title":"Test Task","description":"Test Description"}'
     # Expected response: {"status":"Status is required"}
     ```
   - Run unit tests:
     ```bash
     cd backend
     mvn test
     ```
     - Tests cover `AuthController`, `TaskController`, `UserService`, `TaskService`, `UserRepository`, and `TaskRepository` in `com.portfolio.todolist.ToDoListApplication`.
     - Use `mvn test -Dtest=com.portfolio.todolist.ToDoListApplication.controller.AuthControllerTest` to run specific tests.
     - Verify test coverage using JaCoCo (to be integrated in CI/CD pipeline).
7. **Documentation**:
   - Requirements: `docs/requirements.md`.
   - Architecture: `docs/architecture.md`.
   - Development environment: `docs/dev-environment.md`.
   - CI/CD pipeline: `docs/ci-cd-pipeline.md`.

## Contribution Guidelines
- **Branching Strategy**:
  - `main`: Production-ready code, protected branch.
  - `develop`: Integration branch for feature development.
  - `feature/<task-name>`: Feature branches for specific tasks (e.g., `feature/integrate-authentication`).
  - `bugfix/<issue-id>`: Branches for bug fixes.
- **Git Workflow**:
  1. Create a feature branch from `develop`: `git checkout develop && git checkout -b feature/<task-name>`.
  2. Commit changes with clear messages: `git commit -m "Add <description>"`.
  3. Push branch to remote: `git push origin feature/<task-name>`.
  4. Create a pull request (PR) to `develop` for review.
  5. After approval, merge PR and delete the feature branch.
- **Commit Message Guidelines**:
  - Use present tense (e.g., "Add unit tests" instead of "Added").
  - Reference task or issue numbers if applicable.
- Ensure all changes are documented in `docs/` where applicable.
- Run local tests before pushing changes: `mvn test` (backend), `npm run lint` (frontend).