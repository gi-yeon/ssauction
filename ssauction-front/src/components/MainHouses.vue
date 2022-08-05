<template>
  <div class="container main-house" @click="joinSession">
    <div class="row">
      <div class="col-6">
        <img
          class="itemimg d-block m-3"
          :src="`http://localhost:8080/file/item/${imgNameList[0]}`"
          alt=""
        />
      </div>
      <div class="col-6">
        <div class="row">{{ this.hot.house.houseTitle }}</div>
        <div class="row">{{ this.hot.item.sellerNickname }}</div>
        <div class="row" v-if="isSelling">{{ participantNum }}</div>
        <div class="row" v-if="!isSelling">
          {{ this.hot.house.houseAucTime }}
        </div>
        <div class="row" v-if="isSelling">
          <div class="col">{{ houseAuctionCurrentPrice }}</div>
          <div class="col">{{ houseAuctionRemainingTime }}</div>
        </div>
        <div class="row" v-if="!isSelling">
          <div>{{ this.hot.house.houseDesc }}</div>
        </div>
        <div><button @click="like">찜하기</button></div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '@/utils/axios';
export default {
  components: {},
  name: "MainHouses",

  data() {
    return {
      participantNum: 123,
      houseAuctionCurrentPrice: 100,
      houseAuctionRemainingTime: "01:00",
      isSelling: false,
      imgNameList: [],
      userNo:0
    };
  },
  props: {
    hot: Object,
  },
  mounted() {
    for (let img of this.hot.itemImgList) {
      this.imgNameList.push(img.itemImgUri);
    }
    console.log(this.imgNameList);
    this.userNo=this.$store.state.user.userNo;
  },
  methods: {
    joinSession() {
      console.log(`houseNo : ${this.hot.house.houseNo}`);
      // this.$router.push({
      //   name: "Sessions",
      //   params: { houseNo: this.hot.house.houseNo },
      // });
      window.open(
        `http://localhost:8083/sessions/${this.hot.house.houseNo}?isInSession=true`,
        "_blank"
      );
      this.$route.push({ name: "sessionRedirect" });
    },
    like : async function(){
      console.log("like start");
      const obj={
        userNo:this.userNo,
        houseNo:this.hot.house.houseNo
      }
      axios.post("/likes", obj).then(({data})=>{
        console.log(data);
      })
    }
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
