<template>
  <br>
  <br>
  <h1>메인 페이지</h1>
  <button @click="clickBtn">click</button>
</template>

<script>
import axios from "@/utils/axios.js";
export default {
  name: "SsauctionHome",
  data() {
    return {};
  },
  mounted() {},
  methods: {
    clickBtn: () => {
      console.log("click");
      const obj = { name: "giyeon", age: 26, userno: 1, desc: "hi, everyone" };
      axios.post("hello", JSON.stringify(obj)).then(({ data }) => {
        alert(data);
      });
      console.log("end");
    },
  },
};
</script>
<style></style>