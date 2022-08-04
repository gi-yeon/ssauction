import { createApp } from "vue";
import App from "./App.vue";

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import router from "./router";
import 'v-calendar/dist/style.css';
import VCalendar from 'v-calendar';
import store from "./store";
// import axios from "axios";
// import VueCookies from "vue3-cookies";


const app = createApp(App);
app.use(router).use(store).use(VCalendar).use(ElementPlus).mount("#app");


// axios.interceptors.request.use(async function (config) {
//     // Do something before request is sent
//     config.headers.token = VueCookies.get('accessToken');
//     config.headers.refresh_token = VueCookies.get('refreshToken');

//     console.log(config);
//     return config;
// }, function (error) {
//     // Do something with request error
//     return Promise.reject(error);
// });

// // Add a response interceptor
// axios.interceptors.response.use(function (response) {
//     // Any status code that lie within the range of 2xx cause this function to trigger
//     // Do something with response data
//     return response;
// }, async function (error) {
//     // Any status codes that falls outside the range of 2xx cause this function to trigger
//     // Do something with response error
//     console.log('에러일 경우', error.config);
//     const errorAPI = error.config;
//     // if (error.response.data.message === "fail" && errorAPI.retry === undefined) {
//     errorAPI.retry = true;
//     console.log('토큰이 이상한 오류일 경우');
//     await this.$store.dispatch("user/refreshToken()");
//     return await axios(errorAPI);
//     // }
//     // return Promise.reject(error);
// });


// export default axios;