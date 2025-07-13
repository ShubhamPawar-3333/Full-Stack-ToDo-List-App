# Todo List Application

## Overview
This is a full-stack Todo List application built with React (frontend), Spring Boot (backend), and PostgreSQL (database). The application allows users to manage personal task lists with CRUD operations and secure authentication.

## Project Status
- **Phase**: Planning
- **Completed Tasks**:
  - Defined requirements (see `docs/requirements.md`).
  - Designed architecture (see `docs/architecture.md`).
  - Set up Git repository with `.gitignore` for Java and Node.js.
- **Current Task**: Ongoing planning phase.

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd todo-list-app
   ```
2. **Install Dependencies**:
   - Backend: Java 17, Maven (to be set up in later phases).
   - Frontend: Node.js, npm (to be set up in later phases).
3. **Documentation**:
   - Requirements are documented in `docs/requirements.md`.
   - Architecture is documented in `docs/architecture.md`.

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