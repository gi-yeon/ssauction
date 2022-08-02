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
      <router-link to="/login"
        ><img src="@\assets\imgs\login.png" alt="Login" class="icon2"
      /></router-link>
      &nbsp; &nbsp;
      <router-link to="/signup"
        ><img src="@\assets\imgs\signup.png" alt="Signup" class="icon3"
      /></router-link>
      &nbsp; &nbsp;
    </div>
  </div>
</template>

<script>
export default {
  name: "NavBar",
  computed: {},
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
