import { createWebHistory, createRouter } from "vue-router";
import Home from "@/views/Home.vue";
import Login from "@/views/Login.vue";
import Signup from "@/views/Signup.vue";
import House from "@/views/House.vue";
import Profile from "@/views/Profile.vue";
import Zzim from "@/views/Zzim.vue";
import Settings from "@/views/Settings.vue";

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
