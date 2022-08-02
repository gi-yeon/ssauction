<template>
  <div id="App" class="nav">
    <div class="brand fw-bold">SSAUCTION</div>

    <div class="wrap">
      <router-link to="/test">Test</router-link> &nbsp;
      <router-link to="/findid">FindId</router-link> &nbsp;
      <router-link to="/resetpwd">ResetPwd</router-link> &nbsp;
      <!--권한설정 테스트용-->
      <router-link to="/authorizationTest">authorizationTest</router-link>
      &nbsp;
      <router-link to="/login" v-if="!user.isLogin"
        ><img src="@\assets\imgs\login.png" alt="Login" class="icon2"
      /></router-link>
      &nbsp; &nbsp;
      <router-link to="/signup" v-if="!user.isLogin"
        ><img src="@\assets\imgs\signup.png" alt="Signup" class="icon3"
      /></router-link>
      &nbsp; &nbsp;
      <!--로그인 한 상태-->
      <b v-if="user.isLogin">
        {{ user.loginUser.userNickname }}님 반갑습니다*^^*
      </b>
      <button v-if="user.isLogin" @click="logout">logout</button>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "NavBar",
  computed: {
    ...mapState(["user", ["isLogin"]]),
  },
  methods: {
    logout() {
      this.$store.dispatch("user/userLogout");
    },
  },
};
</script>

<style>
.wrap {
  padding: 20px 10px;
}

.brand {
  display: inline;
  font-size: 50px;
  color: rgb(94, 94, 94);
  padding-left: 15px;
  margin: 4px;
  background-color: rgb(255, 170, 165);
}

.nav {
  background-color: rgb(255, 170, 165);
  display: flex;
  justify-content: space-between;
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
