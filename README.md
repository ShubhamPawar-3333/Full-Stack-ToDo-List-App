# Todo List Application

## Overview
This is a full-stack Todo List application built with React (frontend), Spring Boot (backend), and PostgreSQL (database). The application allows users to manage personal task lists with CRUD operations and secure authentication.

## Project Status
- **Phase**: Backend Development
- **Completed Tasks**:
  - Defined requirements (see `docs/requirements.md`).
  - Designed architecture (see `docs/architecture.md`).
  - Set up Git repository with `.gitignore` for Java and Node.js.
  - Set up development environment (see `docs/dev-environment.md`).
  - Planned CI/CD pipeline (see `docs/ci-cd-pipeline.md`).
  - Set up Spring Boot project in `backend/` directory.
  - Designed database schema for Users and Tasks tables using JPA, Lombok, and TaskStatus enum.
- **Current Task**: Ongoing backend development.

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
3. **Run the Backend**:
   - Start the Spring Boot application:
     ```bash
     cd backend
     mvn spring-boot:run
     ```
   - Access the H2 console at `http://localhost:8080/h2-console` (username: sa, password: <empty>).
   - Verify the schema: Check for `USERS` and `TASKS` tables in the H2 console.
4. **Documentation**:
   - Requirements are documented in `docs/requirements.md`.
   - Architecture is documented in `docs/architecture.md`.
   - Development environment setup is documented in `docs/dev-environment.md`.
   - CI/CD pipeline plan is documented in `docs/ci-cd-pipeline.md`.

## Contribution Guidelines
- **Branching Strategy**:
  - `main`: Production-ready code, protected branch.
  - `develop`: Integration branch for feature development.
  - `feature/<task-name>`: Feature branches for specific tasks (e.g., `feature/backend-crud`).
  - `bugfix/<issue-id>`: Branches for bug fixes.
- **Git Workflow**:
  1. Create a feature branch from `develop`: `git checkout develop && git checkout -b feature/<task-name>`.
  2. Commit changes with clear messages: `git commit -m "Add <description>"`.
  3. Push branch to remote: `git push origin feature/<task-name>`.
  4. Create a pull request (PR) to `develop` for review.
  5. After approval, merge PR and delete the feature branch.
- **Commit Message Guidelines**:
  - Use present tense (e.g., "Add task CRUD endpoints" instead of "Added").
  - Reference task or issue numbers if applicable.
- Ensure all changes are documented in `docs/` where applicable.
- Run local tests (to be set up in later phases) before pushing changes.