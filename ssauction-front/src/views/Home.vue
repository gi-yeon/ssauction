<template>
<div style="margin-top: 100px">
  <div>
    <h1>Hot Deals</h1>
    <div>
      <main-houses v-for="(hot, index) in hotDeals" :key="index" />
    </div>
  </div>
  <div>
    <h1>Comming Soon</h1>
    <div></div>
  </div>
  <h1>{{ userInfo }}</h1>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import MainHouses from "@/components/MainHouses.vue";
// import axios from "@/utils/axios.js";

function useUser() {
  const store = useStore();
  const userInfo = computed(() => store.getters["user/userInfo"]);
  return {
    userInfo,
  };
}
export default {
  name: "SsauctionHome",
  components: {
    MainHouses,
  },
  MainHousesdata() {
    return {
      ...useUser(),
    };
  },

  mounted() {},
  methods: {},
};
</script>
<style></style>
