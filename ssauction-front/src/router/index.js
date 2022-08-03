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
import authorizationTest from "@/views/authorizationTest.vue"



//네비게이션 가드((뷰 라우터로 URL 접근에 대해서 처리할 수 있음)
// router.beforeEach(async (to, from, next) => { //여기서 모든 라우팅이 대기 상태가 됨
//   const { VueCookies } = useCookies;
//   const userNo = this.$store.getters['user/userNo'];
//   /**
//    * to: 이동할 url 정보가 담긴 라우터 객체
//    * from: 현재 url 정보가 담긴 라우터 객체
//    * next: to에서 지정한 url로 이동하기 위해 꼭 호출해야 하는 함수
//    * next() 가 호출되기 전까지 화면 전환되지 않음
//    */
//   if (VueCookies.get('accessToken') === null && VueCookies.get('refreshToken') !== null) {
//     //refreshToken은 있고 accessToken이 없을 경우 토큰 재발급 요청
//     await this.$store.dispatch('user/refreshToken', userNo);
//   }
//   if (VueCookies.get('accessToken')) {
//     //accessToken이 있을 경우 진행
//     return next();
//   }
//   if (VueCookies.get('accessToken') === null && VueCookies.get('refreshToken') === null) {
//     //2개 토큰이 모두 없을 경우 로그인페이지로
//     return next({ name: 'Login' });
//   }
//   return next();
// })

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
