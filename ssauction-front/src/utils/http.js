import http from "axios";
http.defaults.headers.post["Content-Type"] = "application/json";
export default http;
