<!--권한(role)으로 걸러지는지 테스트-->
<template>
  <div>
    <button @click="getProfile">getProfile</button>
  </div>
</template>

<script>
import axios from "@/utils/axios";
export default {
  name: "authorizationTest",

  methods: {
    getProfile() {
      axios.get("/users/1").then(({ data }) => {
        console.log(data);
      });
    },
  },
};
</script>

<style>
</style>