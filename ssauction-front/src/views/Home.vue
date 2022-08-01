<template>
  <br />
  <h1>메인 페이지</h1>
  <button @click="clickBtn">click</button>
  <h1>{{ userInfo }}</h1>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import axios from "@/utils/axios.js";

function useUser() {
  const store = useStore();
  const userInfo = computed(() => store.getters["user/userInfo"]);
  return {
    userInfo,
  };
}
export default {
  name: "SsauctionHome",

  data() {
    return {
      ...useUser(),
    };
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
