# Todo List Application

The Todo List Application is a full-stack web application designed to help users manage tasks efficiently with user authentication and CRUD operations. This project follows an enterprise-grade development roadmap, ensuring scalability, maintainability, and a responsive user experience. The frontend is built with React, Vite, and Tailwind CSS, while the backend (to be implemented) will use Spring Boot and PostgreSQL.

## Project Overview

This application allows users to:
- Register and log in securely.
- Create, read, update, and delete tasks.
- View tasks in a responsive, user-friendly interface.

The project is currently in **Phase 3: Frontend Development**, with reusable UI components, state management, and client-side routing implemented. Future phases will include backend integration and deployment.

## Technology Stack

- **Frontend**:
  - **React**: Component-based JavaScript library for building the user interface.
  - **Vite**: Fast build tool with Hot Module Replacement (HMR) for development.
  - **Tailwind CSS**: Utility-first CSS framework for responsive styling.
  - **Axios**: Promise-based HTTP client for API calls (to be integrated).
  - **React Router**: For client-side navigation.
- **Build Tools**:
  - **ESLint**: For code linting and consistency.
  - **Prettier**: For code formatting.
- **DevOps**:
  - **Git**: Version control with a branching strategy (`main`, `develop`, feature branches).
  - **GitHub**: Repository hosting and CI/CD with GitHub Actions (to be configured).

## Setup Instructions

### Prerequisites
- **Node.js**: Version 18.x or higher.
- **npm**: Version 8.x or higher.
- **Git**: For version control.

### Installation
1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd frontend
   ```
2. **Install Dependencies**:
   ```bash
   npm install
   ```
3. **Run the Development Server**:
   ```bash
   npm run dev
   ```
   The app will be available at `http://localhost:5173` (or another port if specified).
4. **Build for Production**:
   ```bash
   npm run build
   ```
5. **Preview Production Build**:
   ```bash
   npm run preview
   ```
6. **Run Linting**:
   ```bash
   npm run lint
   ```

### Project Structure
```
frontend/
├── public/                   # Static assets (e.g., to-do-list-icon.svg)
├── src/
│   ├── assets/               # Static assets (e.g., react.svg)
│   ├── components/           # Reusable React components
│   ├── context/              # React Context for state management
│   ├── App.jsx               # Main application component
│   ├── index.css             # Global styles (Tailwind CSS)
│   └── main.jsx              # Entry point for React
├── README.md                 # Project documentation
├── eslint.config.js          # ESLint configuration
├── index.html                # HTML entry point
├── package.json              # Dependencies and scripts
└── vite.config.js            # Vite configuration
```

## UI Components

The following reusable UI components have been implemented in `src/components/`:
- **Header.jsx**: A responsive navigation bar with a hamburger menu for mobile devices, including links to `/login`, `/register`, and `/tasks` using React Router.
- **TaskCard.jsx**: Displays a single task with its title, description, and status in a card layout. Styled with Tailwind CSS, it includes color-coded status indicators (red for To Do, yellow for In Progress, green for Done) and hover effects.
- **LoginForm.jsx**: Renders a login form with username and password fields, styled for responsiveness with Tailwind CSS. Includes client-side state management for form inputs.
- **RegisterForm.jsx**: Renders a registration form with username and password fields, styled similarly to `LoginForm` with Tailwind CSS. Integrated with `UserContext` for state management.
- **TaskList.jsx**: Renders a list of tasks using `TaskCard` components in a responsive grid layout (1-3 columns based on screen size). Displays a fallback message when no tasks are available.

## State Management

State management is implemented using **React Context** in `src/context/`:
- **TaskContext.jsx**: Manages the task state (array of tasks) with actions to add, update, and delete tasks. Used by `TaskList` to display tasks.
- **UserContext.jsx**: Manages user authentication state (username, isAuthenticated) with actions for login and logout. Used by `LoginForm` and `RegisterForm` to handle user state.
- The `App.jsx` component wraps the application with `TaskProvider` and `UserProvider` to make these contexts available to all components.

## Routing

Client-side routing is implemented using **React Router** in `src/App.jsx`:
- **Routes**:
  - `/`: Displays a welcome message and instructions to log in or register.
  - `/login`: Renders the `LoginForm` component for user login.
  - `/register`: Renders the `RegisterForm` component for user registration.
  - `/tasks`: Renders the `TaskList` component to display tasks.
- The `Header.jsx` component uses `Link` from `react-router-dom` for navigation to ensure a seamless single-page application experience.

## Contribution Guidelines

1. **Branching Strategy**:
   - Use `main` for production-ready code.
   - Use `develop` for integration of completed features.
   - Create feature branches (e.g., `feature/setup-routing`) for new tasks.
   - Example:
     ```bash
     git checkout -b feature/<task-name>
     git add .
     git commit -m "Describe changes"
     git push origin feature/<task-name>
     ```
   - Open a pull request to merge into `develop` for review.

2. **Code Style**:
   - Follow ESLint and Prettier rules (configured in `eslint.config.js`).
   - Use JSDoc comments for all React components and context providers.
   - Ensure Tailwind CSS is used for styling to maintain consistency.

3. **Commit Messages**:
   - Use clear, descriptive messages (e.g., "Set up React Router for navigation").
   - Reference related tasks or issues if using a project management tool (e.g., Jira, Trello).

4. **Pull Requests**:
   - Include a description of changes and their purpose.
   - Ensure all linting and tests (if applicable) pass before submitting.

## Current Status

The project is in **Phase 3: Frontend Development**, with the following completed:
- Initialized React project with Vite and Tailwind CSS.
- Implemented reusable UI components (`Header`, `TaskCard`, `LoginForm`, `RegisterForm`, `TaskList`) with responsive Tailwind CSS styling.
- Implemented state management using React Context for tasks and user authentication.
- Configured client-side routing with React Router for `/login`, `/register`, and `/tasks`.
- Configured ESLint and Prettier for code quality.

Next steps include integrating API calls, backend integration, and deployment, as outlined in the project roadmap.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details (to be added).