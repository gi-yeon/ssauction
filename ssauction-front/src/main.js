import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import 'v-calendar/dist/style.css';
import VCalendar from 'v-calendar';
import store from "./store";


const app = createApp(App);
app.use(router).use(store).use(VCalendar).mount("#app");
