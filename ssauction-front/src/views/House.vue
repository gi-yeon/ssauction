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
      <h3>경매 일시</h3>
    </div>
    <div class="col-2 data">
      <v-date-picker
        v-model="houseDate"
        mode="dateTime"
        locale="ko-KR"
        :model-config="modelConfig"
        is24hr
      >
        <template v-slot="{ inputValue, inputEvents }">
          <input
            class="bg-white border px-2 py-1 rounded"
            :value="inputValue"
            v-on="inputEvents"
          />
        </template>
      </v-date-picker>
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
      <div class="mb-3">
        <input
          class="form-control"
          type="file"
          id="formFileMultiple"
          multiple
        />
      </div>
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
      houseDate: new Date(),
      modelConfig: {
        type: "string",
        mask: "YYYY-MM-DDThh:mm",
      },
    };
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
