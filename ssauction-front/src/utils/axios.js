import axios from "axios";
axios.defaults.headers.post["Content-Type"] = "application/json";
axios.defaults.headers.put["Content-Type"] = "application/json";
axios.defaults.baseURL = 'https://52.78.53.155:8080/ssauction';
export default axios;

