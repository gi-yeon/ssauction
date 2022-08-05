<template>
  <nav-bar></nav-bar>
  <div class="container">
    <div class="row">
      <side-bar class="col-xs-1"></side-bar>
      <div class="col-xs-11">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import SideBar from "@/components/SideBar.vue";

export default {
  name: "App",
  components: { NavBar, SideBar },
  //쿠키에서 logininfo를 가져온다.
  mounted() {
    this.$store.dispatch("user/getLoginInfo");
  },
};
</script>

<style>
@font-face {
  font-family: "NanumSquareRoundB";
  src: url("assets/font/NanumSquareRoundB.ttf") format("truetype");
  font-weight: 300;
  color: #f6f6f6;
}

#app {
  font-family: NanumSquareRoundB, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
