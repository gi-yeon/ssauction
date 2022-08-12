import { createApp } from "vue";
import App from "./App.vue";

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import router from "./router";
import 'v-calendar/dist/style.css';
import VCalendar from 'v-calendar';
import store from "./store";
import axios from "axios";



const app = createApp(App);
app.use(router).use(store).use(VCalendar).use(ElementPlus).mount("#app");


// axios interceptor
// 토큰을 확인하여 refresh는 유효하고 access는 만료되었으면 refresh를 호출한다.
// 권한이 없다면 권한없음 alert
// access, refresh token 모두가 만료됐다면 재로그인 alert
axios.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        const {
            config,
            response: { status },
        } = error;
        let accessToken = null; //access token
        let refreshToken = null; //refresh token
        const originalRequest = config;

        console.log(status);
        //쿠키에서 token을 가져오는 api 호출하여 토큰 값에 담아줌
        await axios.get("/users/token").then(({ data }) => {
            accessToken = data.accessToken;
            refreshToken = data.refreshToken;
        })

        //access는 만료, refresh는 존재한다면
        if (status === 401 && accessToken == null && refreshToken != null) {
            //userNo를 가져와서
            const userNo = store.getters["user/userNo"];
            // token refresh 요청
            await axios.post("/users/refresh", userNo).then((res) => {
                console.log(res);
                store.dispatch("user/setLoginState", res)
                store.dispatch("user/setLoginCookie")

             });
            



            // 401로 요청 실패했던 요청 새로운 accessToken으로 재요청
            return axios(originalRequest);

        }
        //token이 아예 없다면 재로그인 해야함
        else if (status === 401 && accessToken == null && refreshToken == null) {
            alert("다시 로그인해주세요.")

        }
        //로그인 시 회원 정보가 없다면
        else if (status != 401 && accessToken == null && refreshToken == null) {
            alert("이메일을 확인해주세요.");
        }

        //권한이 없다면
        else {
            alert("권한이 없습니다.")
        }

        return Promise.reject(error);
    }
);
