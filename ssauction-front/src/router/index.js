import { createWebHistory, createRouter } from "vue-router";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import Signup from "@/views/Signup.vue";
import Test from "@/views/Test.vue";
import FindId from "@/views/FindId.vue";
import ResetPwd from "@/views/ResetPwd.vue";
import ResetPwd2 from "@/views/ResetPwd2.vue";


const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup,
  },
  {
    path: "/test",
    name: "Test",
    component: Test,
  },
  {
    path: "/findid",
    name: "FindId",
    component: FindId,
  },
  {
    path: "/resetpwd",
    name: "ResetPwd",
    component: ResetPwd,
  },
  {
    path: "/resetpwd2",
    name: "ResetPwd2",
    component: ResetPwd2,
    props: true,
  },
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});
export default router;
