<template>
  <div class="col-sm-3">
    <div class="card" style="margin-bottom: 10px; margin-top: 10px">
      <div class="card-body p-2" style="padding: 0.5rem; padding-bottom: 0rem">
        <div class="mainsell-container">
          <img
            class="itemimg d-block m-3"
            :src="'data:image/png;base64,' + hot.itemImgList[0].img"
            alt=""
          />
          <br />
          {{ this.hot.houseTitle }}
          <br />
          <div v-if="isSelling">{{ participantNum }}</div>
          <div v-if="!isSelling">
            {{ getAuctionStartTime(this.hot.houseAucTime) }}
          </div>
          <div v-if="isSelling">
            <div class="col">{{ houseAuctionCurrentPrice }}</div>
            <div class="col">{{ houseAuctionRemainingTime }}</div>
          </div>
          <div v-if="!isSelling">
            <div>{{ this.hot.houseDesc }}</div>
          </div>
          <div><button @click="like">찜하기</button></div>
        </div>
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
.mainsell-container {
  /* border-radius: 90%; */
  overflow: hidden;
  margin: auto;
}
/* .sellImgContainer {
  width: 70%;
  height: 70%;
  object-fit: cover;
  object-fit: contain;
} */
</style>
