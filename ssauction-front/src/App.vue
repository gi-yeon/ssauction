<template>
  <nav-bar v-if="!isInSession"></nav-bar>
  <div class="container">
    <div class="row">
      <side-bar v-if="!isInSession" class="col-xs-1"></side-bar>
      <div v-if="!isInSession">
        <router-view />
      </div>
      <div v-else class="col-xs-11">
        <router-view/>
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
  data() {
    return {
      isInSession: false,
    };
  },
  mounted() {
    const URLParams = new URL(window.location).searchParams;
    console.log(URLParams.get("isInSession"));
    this.isInSession = URLParams.get("isInSession");
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
