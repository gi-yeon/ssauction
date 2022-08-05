import { createWebHistory, createRouter } from "vue-router";
// import { useCookies } from "vue3-cookies";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import Signup from "@/views/Signup.vue";
import Test from "@/views/Test.vue";
import FindId from "@/views/FindId.vue";
import ResetPwd from "@/views/ResetPwd.vue";
import ResetPwd2 from "@/views/ResetPwd2.vue";
import House from "@/views/House.vue";
import Profile from "@/views/Profile.vue";
import Zzim from "@/views/Zzim.vue";
import Settings from "@/views/Settings.vue";
import Sessions from "@/views/Sessions.vue";
import ProfileUpdate from "@/views/ProfileUpdate.vue";
import authorizationTest from "@/views/authorizationTest.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/authorizationTest",
    name: "authorizationTest",
    component: authorizationTest,
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
  {
    path: "/house",
    name: "House",
    component: House,
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/sessions/:houseNo",
    name: "Sessions",
    component: Sessions,
  },
  {
    path: "/profile/update",
    name: "ProfileUpdate",
    component: ProfileUpdate,
  },
  {
    path: "/zzim",
    name: "Zzim",
    component: Zzim,
  },
  {
    path: "/settings",
    name: "Settings",
    component: Settings,
  },
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});
export default router;
