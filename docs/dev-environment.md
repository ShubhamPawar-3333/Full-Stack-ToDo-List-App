# Todo List Application Development Environment Setup

## Overview
This document outlines the setup process for local development environments for the Todo List application. The frontend uses React (Node.js), and the backend uses Spring Boot (Java/Maven) with an H2 in-memory database for local development. The instructions ensure developers can set up consistent environments for coding, testing, and debugging.

## Prerequisites
- **Git**: Version control system (version 2.30 or higher).
- **Node.js**: LTS version (e.g., 20.x) for frontend development.
- **Java**: JDK 17 for Spring Boot backend.
- **Maven**: Version 3.8 or higher for dependency management.
- **IDE**: IntelliJ IDEA, VS Code, or equivalent (with plugins for Java, JavaScript, and React).
- **PostgreSQL**: Optional for local testing (H2 is used by default for simplicity).
- **Docker**: Optional for containerized development (to be configured later).

## Frontend Development Environment (React)
1. **Install Node.js**:
   - Download and install Node.js LTS from [nodejs.org](https://nodejs.org).
   - Verify installation: `node --version` and `npm --version`.
2. **Set Up Initial Project Structure**:
   - Create a `frontend` directory in the project root (to be initialized in the frontend development phase).
   - Placeholder command (to be executed later): `npx create-react-app frontend` or `npm create vite@latest frontend`.
3. **Install VS Code Extensions** (recommended):
   - ESLint: For JavaScript linting.
   - Prettier: For code formatting.
   - React Snippets: For faster React development.
4. **Environment Variables**:
   - Create a `.env.local` file in the `frontend` directory (to be added later) for API URLs (e.g., `REACT_APP_API_URL=http://localhost:8080`).

## Backend Development Environment (Spring Boot)
1. **Install Java JDK 17**:
   - Download from [Adoptium](https://adoptium.net) or [Oracle](https://www.oracle.com/java).
   - Verify installation: `java --version`.
2. **Install Maven**:
   - Download from [maven.apache.org](https://maven.apache.org).
   - Verify installation: `mvn --version`.
3. **Set Up Initial Project Structure**:
   - Create a `backend` directory in the project root (to be initialized in the backend development phase).
   - Placeholder command (to be executed later): Use Spring Initializr to generate a project with dependencies (Web, Security, JPA, H2).
4. **Install IntelliJ IDEA Plugins** (recommended):
   - Spring Boot: For enhanced Spring support.
   - Lombok: For annotation-based code generation.
   - Checkstyle: For code style enforcement.
5. **Environment Variables**:
   - Create a `.env` file in the `backend` directory (to be added later) for database and JWT configurations (e.g., `SPRING_DATASOURCE_URL=jdbc:h2:mem:tododb`).

## Local Database (H2)
- **H2 Database**: Included as a dependency in the Spring Boot project (to be added later).
- **Access**: H2 console available at `http://localhost:8080/h2-console` (once backend is set up).
- **Configuration**: Use in-memory database for local development to simplify setup.

## Optional: Docker Setup
- Docker can be used to run PostgreSQL locally for testing (to be configured in later phases).
- Install Docker Desktop from [docker.com](https://www.docker.com).
- Placeholder command: `docker run -p 5432:5432 -e POSTGRES_DB=tododb postgres`.

## Verification
- Ensure Node.js, Java, and Maven are correctly installed by running version commands.
- Confirm IDEs are set up with recommended plugins.
- Verify Git is configured: `git config --global user.name "Your Name"` and `git config --global user.email "your.email@example.com"`.

## Notes
- The `frontend` and `backend` directories will be created in their respective development phases.
- Environment variables will be finalized during integration to avoid hardcoding sensitive data.
- This setup ensures consistency across developer machines and prepares for containerized development with Docker.