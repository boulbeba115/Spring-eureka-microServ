import axios from 'axios';

const HTTP = axios.create({
  baseURL: 'http://localhost:9900/',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export default HTTP;
