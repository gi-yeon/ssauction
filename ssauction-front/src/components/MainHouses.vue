<template>
  <div class="container main-house">
    <div class="row">
      <div class="col-6">
        <img
          class="itemimg d-block m-3"
          :src="'data:image/png;base64,' + hot.itemImgList[0].img"
          alt=""
        />
      </div>
      <div class="col-6">
        <div class="row">{{ hot.house.houseTitle }}</div>
        <!-- <div class="row">{{ this.hot.item.sellerNickname }}</div> -->
        <div class="row">
          {{ getAuctionStartTime(hot.house.houseAucTime) }}
        </div>
        <div class="row">
          <div>{{ this.hot.house.houseDesc }}</div>
        </div>
        <div class="row">
          {{ this.hot.item.sellerNickname }}
        </div>
        <div><button @click="like">찜하기</button></div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios";
export default {
  components: {},
  name: "MainHouses",

  data() {
    return {
      isSelling: false,
      imgNameList: [],
      userNo: null,
    };
  },
  props: {
    hot: Object,
  },
  mounted() {
    this.userNo = this.$store.getters["user/userNo"];
    // for (let img of this.hot.itemImgList) {
    //   this.imgNameList.push(img.itemImgUri);
    // }
    // console.log(this.imgNameList);
    // this.userNo = this.$store.state.user.userNo;\
    console.log(this.hot);
  },
  methods: {
    like: async function () {
      console.log("like start");
      const obj = {
        userNo: this.userNo,
        houseNo: this.hot.houseNo,
      };
      axios.post("/likes", obj).then(({ data }) => {
        console.log(data);
      });
    },
    getAuctionStartTime(timestamp) {
      let datetest = new Date(timestamp);
      console.log(datetest);
      let [date, time] = timestamp.split("T");
      time = time.split(".")[0];
      return `${date} ${time}`;
    },
  },
};
</script>

<style>
.main-house {
  display: inline-block;
  width: 25%;
  height: 100%;
}
.itemimg {
  height: auto;
  width: 90%;
}
</style>
