<template>
  <br>
  <br>
  <h1>{{ userNickname }}님의 프로필</h1>
  <br>
  <br>
  <h3>유저번호 : {{userNo}} </h3><br>
  <h3>유저등급 : {{userGrade}} </h3><br>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
// import axios from "@/utils/axios.js";

function useUser() {
  const store = useStore();
  const userNickname = computed(() => store.getters["user/userNickname"]);
  const userNo = computed(() => store.getters["user/userNo"]);
  const userGrade = computed(() => store.getters["user/userGrade"]);

  return {
    userNickname,
    userNo,
    userGrade,
  };
}

export default {
  name: "SsauctionProfile",

  data() {
    return {
      ...useUser(),
    };
  },

  mounted() {},

  methods: {},
};
</script>

<style></style>