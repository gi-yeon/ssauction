import axios from "axios";
axios.defaults.headers.post["Content-Type"] = "application/json";
axios.defaults.headers.put["Content-Type"] = "application/json";
axios.defaults.baseURL = 'http://52.78.53.155/ssauction';
export default axios;

