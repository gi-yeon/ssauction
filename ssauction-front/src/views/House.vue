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
        v-model="house.houseDate"
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
        <input type="file" ref="itemImage" @change="imageUpload" multiple />
      </div>
    </div>
  </div>
  <div class="row" style="height: 320px">
    <div class="col-4"></div>
    <div class="text-start preview col-8">
      <item-image-preview
        v-for="(file, index) in itemImages"
        :key="file.lastModified"
        :previewfile="file"
        :index="index"
        @delete-img="deleteImg"
      />
    </div>
  </div>
  <div class="row house-input p-5">
    <button @click="createHouse">경매방 생성</button>
  </div>
</template>

<script>
import axios from "@/utils/axios";
import ItemImagePreview from "@/components/ItemImagePreview.vue";

export default {
  name: "SsauctionHouse",
  components: { ItemImagePreview },
  data() {
    return {
      item: {
        itemName: null,
        itemModelNo: null,
        itemQuality: "S",
        itemStartPrice: null,
        itemDesc: null,
        itemCategory: null,
        itemSellerNo: null,
        itemDealStatus : "SELL",
        userNo: this.$store.state.user.userNo,
      },
      house: {
        houseTitle: null,
        // Datepicker 관련 구현이 아직 완벽하지 않아
        // houseAucTime, houseStatus 값을 우선 하드코딩된 값으로 대체했다.
        houseAucTime: "2022-02-22T22:22:22",
        houseStatus: 0,
      },
      houseDate: new Date(),
      modelConfig: {
        type: "string",
        mask: "YYYY-MM-DDThh:mm:ss",
      },
      itemImages: [],
    };
  },

  mounted() {},

  methods: {
    imageUpload() {
      // $refs를 통해 DOM에 있는 input의 files에 직접 접근하면 FileList 객체가 반환된다.
      // FileList 객체는 read only이므로 다루기 어렵다.
      // 아래의 코드를 통해 this.itemImages 배열에 files에 있는 File 객체를 그대로 복사한다.
      Array.prototype.push.apply(this.itemImages, this.$refs.itemImage.files);
      console.log(this.itemImages);
      this.$refs.itemImage.value = "";
    },
    createHouse() {
      // this.house.houseAucTime = this.houseAucTime;
      console.log(this.house);

      // file은 multipart/form-data 형식으로 전송되어야 한다.
      // multipart/form-data 형식으로 요청하면 JSON을 바로 body에 넣는 방식을 사용할 수 없다.
      // 따라서 json Blob 객체로 만들어 파일 형식으로 전달한다.
      const housejson = JSON.stringify(this.house);
      const itemjson = JSON.stringify(this.item);
      const houseblob = new Blob([housejson], { type: "application/json" });
      const itemblob = new Blob([itemjson], { type: "application/json" });

      // html의 form 태그를 이용해 submit하면 formData 객체와 multipart/form-data 형식으로 전달된다.
      // form 태그를 이용하고 있지 않지만 이용한 것처럼 요청하기 위해 새 formData 객체를 만든다.
      let formData = new FormData();
      formData.append("itemDto", itemblob);
      formData.append("houseDto", houseblob);

      // Spring에서 여러 file을 자동으로 배열로 받기 위해서는 Json 리스트를 그대로 전달하면 안된다.
      // 같은 이름을 가진 여러 개의 file을 전송한다.
      for (let img of this.itemImages) {
        formData.append("files", img);
      }

      axios
        .post("/houses", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((data) => {
          console.log(data);
        });
      // this.sendFile();
      this.itemImages.splice(0);
    },

    deleteImg(index) {
      this.itemImages.splice(index, 1);
      console.log(this.$refs.itemImage);
    },
  },
};
</script>

<style>
.house-input {
  padding: 10px;
}
.preview {
  overflow-x: auto;
  white-space: nowrap;
}
</style>
