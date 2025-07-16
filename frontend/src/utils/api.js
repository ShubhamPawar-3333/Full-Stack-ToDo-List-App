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
  if (token) {
    api.defaults.headers.common['Authorization'] = `Bearer ${token}`
  } else {
    delete api.defaults.headers.common['Authorization']
  }
}

export default api