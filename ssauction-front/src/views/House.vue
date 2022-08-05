<template>
  <div style="margin-top: 100px">
    <br>
    <h1>경매방 만들기</h1>
    <br>
    <div class="row" style="padding:10px;">
      <label class="col-4" for="houseTitle">방 제목</label>
      <div class="col-8">
        <el-input
          id="houseTitle"
          v-model="house.houseTitle"
          type="text"
          required>
      </div>
      
      <label class="col-4" for="">경매 날짜</label>
      <div class="col-8">
      <el-date-picker
        v-model="value2"
        type="date"
        placeholder="경매 날짜를 선택하세요"
        :shortcuts="shortcuts"
        :size="size"
      />

      </div>

    </div>

  </div>
</template>

<script>
import axios from "@/utils/axios";
import { ref } from 'vue';

const value1 = ref('')
const value2 = ref('')

const shortcuts = [
  {
    text: '오늘',
    value: new Date(),
  },
  {
    text: '내일',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() + 3600 * 1000 * 24)
      return date
    },
  },
  {
    text: '다음 주',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() + 3600 * 1000 * 24 * 7)
      return date
    },
  },
]

const disabledDate = (time: Date) => {
  return time.getTime() > Date.now()
}

export default {
  name: "SsauctionHouse",

  data() {
    return {
      item: {
        itemName: null,
        itemModelNo: null,
        itemQuality: null,
        itemStartPrice: null,
        itemDesc: null,
        itemCategory: null,
        itemSellerNo: null,
        userNo: 1,
      },
      house: {
        houseTitle: null,
        houseAucTime: null,
        houseStatus: null,
      },
      houseTime: null,
      houseDate: null,
    };
  },
  computed: {
    houseAucTime() {
      return this.houseDate + "T" + this.houseTime;
    },
  },

  mounted() {},

  methods: {
    createHouse() {
      this.house.houseAucTime = this.houseAucTime;
      console.log(this.house);
      axios
        .post(
          "/houses",
          JSON.stringify({
            item: this.item,
            house: this.house,
          })
        )
        .then((data) => {
          console.log(data);
        });
    },
  },
};
</script>


<style scoped>
el-input {
  width: 500px;
  height: 50px;
  border: 0;
  background-color: rgb(230, 230, 230);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  padding-left: 30px;
}

label {
  text-align: center;
}
</style>