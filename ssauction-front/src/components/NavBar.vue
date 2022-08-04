<template>
    <div>
      <div class="brand" style="d-flex justify-content-start padding-left:15px">SSAUCTION</div>
       
      <!--로그인 한 상태에서 보여주는 인사메시지, 로그아웃버튼-->
        <b v-if="user.isLogin" style="d-flex justify-content-end; padding-top: 20px; padding-right:5px;">
          {{ user.loginUser.userNickname }}님 반갑습니다😎
        </b>&nbsp; &nbsp;
        <a v-if="user.isLogin" @click="logout" style="margin-right: 15px;"><img src="@\assets\imgs\logout.png" alt="Logout" class="icon2"></a>&nbsp; &nbsp;

      <!-- 로그아웃 한 상태에서는 로그인, 회원가입 버튼만 노출됨 -->
         <div v-else style="d-flex justify-content-end; padding-top: 20px; padding-right:5px;">
        <router-link to="/login"><img src="@\assets\imgs\login.png" alt="Login" class="icon2"></router-link> &nbsp; &nbsp;
        <router-link to="/signup"><img src="@\assets\imgs\signup.png" alt="Signup" class="icon3"></router-link> &nbsp; &nbsp;
        </div>
    </div>


      <!--테스트용 refresh-->
      <!-- <button @click="refresh">refresh</button>
       -->
      
<!-- 
    <div class="wrap">
      <router-link to="/test">Test</router-link> &nbsp;
      <router-link to="/findid">FindId</router-link> &nbsp;
      <router-link to="/resetpwd">ResetPwd</router-link> &nbsp; -->
      <!--권한설정 테스트용-->
      <!-- <router-link to="/authorizationTest">authorizationTest</router-link>
      &nbsp;
      <router-link to="/login" v-if="!user.isLogin"
        ><img src="@\assets\imgs\login.png" alt="Login" class="icon2"
      /></router-link>
      &nbsp; &nbsp;
      <router-link to="/signup" v-if="!user.isLogin"
        ><img src="@\assets\imgs\signup.png" alt="Signup" class="icon3"
      /></router-link>
      &nbsp; &nbsp; -->


  <!-- </div> -->
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "NavBar",
  computed: {
    ...mapState(["user"]),
  },
  methods: {
    logout() {
      this.$store.dispatch("user/userLogout");
    },
    refresh() {
      console.log(this.user.loginUser.userNo);
      this.$store.dispatch("user/refreshToken", this.user.loginUser.userNo);
    },
  },
};
</script>

<style>
      

.brand {
  /* display: inline; */
  font-size: 50px;
  color: rgb(94, 94, 94);
  padding-left: 15px;
}

.icon2 {
  height: 30px;
  width: 30px;
}

.icon3 {
  height: 35px;
  width: 35px;
}
</style>
