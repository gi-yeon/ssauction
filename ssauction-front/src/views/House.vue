<template>
  <br />
  <br />
  <h1>경매방 만들기</h1>
  <div class="row house-input">
    <div class="col-4 label">
      <h3>제목</h3>
    </div>
    <div class="col-8 data">
      <input
        v-model="house.houseTitle"
        class="form-control"
        type="text"
        required
      />
    </div>
  </div>
  <div class="row house-input">
    <div class="col-4 label">
      <h3>경매 날짜</h3>
    </div>
    <div class="col-2 data">
      <input
        v-model="this.houseDate"
        class="form-control"
        type="text"
        required
      />
    </div>
    <div class="col-2 label">
      <h3>경매 시간</h3>
    </div>
    <div class="col-2 data">
      <input
        v-model="this.houseTime"
        class="form-control"
        type="text"
        required
      />
    </div>
  </div>
  <div class="row house-input">
    <div class="col-4 label">
      <h3>매물 이름</h3>
    </div>
    <div class="col-8 data">
      <input
        v-model="item.itemName"
        class="form-control"
        type="text"
        required
      />
    </div>
  </div>
  <div class="row house-input">
    <div class="col-4 label">
      <h3>모델 번호</h3>
    </div>
    <div class="col-8 data">
      <input
        v-model="item.itemModelNo"
        class="form-control"
        type="text"
        required
      />
    </div>
  </div>
  <div class="row house-input">
    <div class="col-4 label">
      <h3>카테고리</h3>
    </div>
    <div class="col-8 data">
      <input
        v-model="item.itemCategory"
        class="form-control"
        type="text"
        required
      />
    </div>
  </div>
  <div class="row house-input">
    <div class="col-4 label">
      <h3>매물상태</h3>
    </div>
    <div class="col-8 data">
      <input v-model="item.itemQuality" class="form-control" type="text" />
    </div>
  </div>
  <div class="row house-input">
    <div class="col-4 label">
      <h3>매물설명</h3>
    </div>
    <div class="col-8 data">
      <textarea
        v-model="item.itemDesc"
        class="form-control"
        type="text"
      ></textarea>
    </div>
  </div>
  <div class="row house-input">
    <div class="col-4 label">
      <h3>시작가격</h3>
    </div>
    <div class="col-8 data">
      <input
        v-model="item.itemStartPrice"
        class="form-control"
        type="text"
        required
      />
    </div>
  </div>
  <div class="row house-input">
    <div class="col-4 label">
      <h3>사진</h3>
    </div>
    <div class="col-8 data">
      <input class="form-control" type="text" />
    </div>
  </div>
  <div class="row house-input">
    <button @click="createHouse">경매방 생성</button>
  </div>
</template>

<script>
import axios from "@/utils/axios";

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

<style>
.house-input {
  padding: 10px;
}
</style>
