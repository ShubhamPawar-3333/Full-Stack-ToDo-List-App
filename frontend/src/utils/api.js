import axios from 'axios'

/**
 * Configured Axios instance for making API calls to the backend.
 * @type {import('axios').AxiosInstance}
 */
const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
})

/**
 * Add JWT token to request headers if available.
 * @param {string} token - JWT token for authentication
 */
export const setAuthToken = (token) => {
  if (typeof token === 'string' && token.trim()) {
    api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    console.debug('JWT token set in Authorization header');
  } else {
    delete api.defaults.headers.common['Authorization'];
    console.debug('JWT token cleared from Authorization header');
  }
}

export default api