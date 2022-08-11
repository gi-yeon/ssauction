<template>
  <div class="container">
    <div class="row">
      <div class="row">
        <h1>Hot Deals</h1>
        <!-- <div id="hotdeal-container">
          <main-houses
            v-for="(hot, index) in hotDeals"
            :key="index"
            :hot="hot"
          />
        </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import MainHouses from "@/components/MainHouses.vue";
//import axios from "@/utils/axios.js";

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
  data() {
    return { hotDeals: null };
  },
  mounted() {
    // axios.get("/houses/searchAll").then((response) => {
    //   console.log(response.data);
    //   this.hotDeals = response.data;
    //   console.log(this.hotDeals);
    // });
  },
  methods: {},
};
</script>
<style>
#hotdeal-container {
  overflow-x: auto;
  white-space: nowrap;
  height: auto;
}
</style>
