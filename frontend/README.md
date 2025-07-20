# Todo List Application

The Todo List Application is a full-stack web application designed to help users manage tasks efficiently with user authentication and CRUD operations. This project follows an enterprise-grade development roadmap, ensuring scalability, maintainability, and a responsive user experience. The frontend is built with React, Vite, and Tailwind CSS, while the backend (to be integrated) uses Spring Boot and PostgreSQL.

## Project Overview

This application allows users to:
- Register and log in securely.
- Create, read, update, and delete tasks.
- View tasks in a responsive, user-friendly interface.

The project is currently in **Phase 3: Frontend Development**, with reusable UI components, state management, client-side routing, API integration, form validation, and responsiveness implemented. Future phases will include full backend integration and deployment.

## Technology Stack

- **Frontend**:
  - **React**: Component-based JavaScript library for building the user interface.
  - **Vite**: Fast build tool with Hot Module Replacement (HMR) for development.
  - **Tailwind CSS**: Utility-first CSS framework for responsive styling.
  - **Axios**: Promise-based HTTP client for API calls.
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
- **Backend Server**: Spring Boot backend running at `http://localhost:8080` (for API calls).

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
│   ├── utils/                # Utility functions (e.g., Axios configuration)
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
- **Header.jsx**: A responsive navigation bar with a hamburger menu for mobile devices, optimized for all screen sizes with Tailwind CSS. Includes links to `/login`, `/register`, and `/tasks` using React Router.
- **TaskCard.jsx**: Displays a single task with title, description, and status in a card layout. Styled with Tailwind CSS for responsiveness, with adjusted font sizes and spacing for mobile and desktop.
- **LoginForm.jsx**: Renders a login form with validated username (3+ characters) and password (6+ characters, one letter, one number) fields, styled with Tailwind CSS for responsive layouts and touch-friendly inputs.
- **RegisterForm.jsx**: Renders a registration form with validated username (3+ characters) and password (6+ characters, one letter, one number) fields, styled with Tailwind CSS for responsive layouts and touch-friendly inputs.
- **TaskList.jsx**: Renders a list of tasks using `TaskCard` components in a responsive grid layout (1 column on mobile, 2 on tablet, 3 on desktop). Includes a validated form for adding tasks (title: 3+ characters, status: valid option) with responsive styling.

## State Management

State management is implemented using **React Context** in `src/context/`:
- **TaskContext.jsx**: Manages the task state (array of tasks) with actions to add, update, and delete tasks via API calls. Used by `TaskList` to display and manage tasks.
- **UserContext.jsx**: Manages user authentication state (username, isAuthenticated) with actions for login, registration, and logout via API calls. Used by `LoginForm` and `RegisterForm` to handle user state.
- The `App.jsx` component wraps the application with `TaskProvider` and `UserProvider` to make these contexts available to all components.

## Routing

Client-side routing is implemented using **React Router** in `src/App.jsx`:
- **Routes**:
  - `/`: Displays a welcome message and instructions to log in or register.
  - `/login`: Renders the `LoginForm` component for user login.
  - `/register`: Renders the `RegisterForm` component for user registration.
  - `/tasks`: Renders the `TaskList` component to display and manage tasks.
- The `Header.jsx` component uses `Link` from `react-router-dom` for navigation to ensure a seamless single-page application experience.

## API Integration

API calls are integrated using **Axios** in `src/utils/api.js`:
- **Configuration**: `api.js` sets up an Axios instance with a base URL (`http://localhost:8080`) and an interceptor to include JWT tokens in headers for authenticated requests.
- **Authentication APIs** (in `UserContext.jsx`):
  - `POST /auth/login`: Authenticates a user and stores the JWT token.
  - `POST /auth/register`: Registers a new user and stores the JWT token.
- **Task APIs** (in `TaskContext.jsx`):
  - `GET /tasks`: Fetches all tasks for the authenticated user.
  - `POST /tasks`: Creates a new task.
  - `PUT /tasks/{id}`: Updates an existing task.
  - `DELETE /tasks/{id}`: Deletes a task.
- Error handling is implemented with error messages displayed in `LoginForm`, `RegisterForm`, and `TaskList` using Tailwind CSS for styling.

## Form Validation

Client-side form validation is implemented in `src/components/`:
- **LoginForm.jsx** and **RegisterForm.jsx**:
  - Username: Must be at least 3 characters.
  - Password: Must be at least 6 characters, including at least one letter and one number.
- **TaskList.jsx**:
  - Title: Must be at least 3 characters and non-empty.
  - Status: Must be one of "To Do", "In Progress", or "Done".
- Validation errors are displayed below input fields using Tailwind CSS for styling, with red borders on invalid inputs.

## Responsiveness

All UI components are optimized for responsiveness using **Tailwind CSS**:
- **Mobile (<640px)**: Full-width layouts, compact spacing, larger touch targets for buttons and inputs, and a functional hamburger menu in `Header`.
- **Tablet (640px–1024px)**: Adjusted font sizes, balanced spacing, and 2-column grid layouts for tasks in `TaskList`.
- **Desktop (>1024px)**: Wider layouts with 3-column grids for tasks, larger fonts, and optimized spacing.
- Font sizes, padding, and margins scale with screen size using Tailwind’s responsive utilities (e.g., `sm:`, `md:`, `lg:`).

## Contribution Guidelines

1. **Branching Strategy**:
   - Use `main` for production-ready code.
   - Use `develop` for integration of completed features.
   - Create feature branches (e.g., `feature/ensure-responsiveness`) for new tasks.
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
   - Use JSDoc comments for all React components, context providers, and utilities.
   - Ensure Tailwind CSS is used for styling to maintain consistency.

3. **Commit Messages**:
   - Use clear, descriptive messages (e.g., "Enhance UI components for responsiveness").
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
- Integrated Axios for API calls to handle task CRUD operations and user authentication.
- Added client-side form validation for task creation and user inputs.
- Ensured responsiveness across mobile, tablet, and desktop devices using Tailwind CSS.
- Configured ESLint and Prettier for code quality.

Next steps include full backend integration and deployment, as outlined in the project roadmap.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details (to be added).