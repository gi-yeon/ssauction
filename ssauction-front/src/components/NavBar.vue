<template> 
    <div id="App" class="nav">
      <div class="brand fw-bold">SSAUCTION</div>
      
      <div class="jb-wrap">
        <router-link to="/">Home</router-link> &nbsp; 
        <router-link to="/login">Login</router-link> &nbsp; 
        <router-link to="/signup">Signup</router-link> &nbsp; 
      </div>

    </div>
</template>

<script>
  export default {
    name: 'NavBar',
    computed: {
    },
  }
</script>

<style>
.jb-wrap {
  padding: 20px 10px;
}
      
.brand {
  display: inline;
  font-size: 50px;
  color:rgb(94, 94, 94);
  padding-left: 20px;
  margin: 0px;
  background-color: rgb(255, 170, 165);
}

.nav {
  background-color: rgb(255, 170, 165);
  display: flex;
  justify-content: space-between;
}
</style>
