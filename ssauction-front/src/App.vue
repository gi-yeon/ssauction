<template>
  <nav-bar></nav-bar>
  <router-view />
</template>

<script>
import NavBar from '@/components/NavBar.vue'

export default {
  name: "App",
  components: {NavBar},
};
</script>

<style>
@font-face {
  font-family:'NanumSquareRoundB';
  src: url('assets/font/NanumSquareRoundB.ttf') format('truetype');
  font-weight: 300;
  color: #f6f6f6
}

#app {
  font-family: NanumSquareRoundB, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
