import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import 'v-calendar/dist/style.css';
import VCalendar from 'v-calendar';
import store from "./store";
import axios from "axios";
import { computed } from "vue";


const app = createApp(App);
app.use(router).use(store).use(VCalendar).mount("#app");

axios.interceptors.response.use(
    (response) => {
        console.log("여기는 리스펀스")
        return response;
    },
    async (error) => {
        console.log("여기는 에러")
        const {
            config,
            response: { status },
        } = error;
        if (status === 401) {
            console.log("여기 들어오나?")
            console.log(error.response.data)
            // if (error.response.data.message === "TokenExpiredError") {
            const originalRequest = config;
            const userNo = computed(() => store.getters["user/userNo"]);
            console.log(userNo)
            // token refresh 요청
            await axios.post("/users/refresh", userNo);
            // 401로 요청 실패했던 요청 새로운 accessToken으로 재요청
            return axios(originalRequest);
        }
        // }
        return Promise.reject(error);
    }
);