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
      this.house.houseAucTime = this.houseAucTime;
      console.log(this.house);
      // axios
      //   .post(
      //     "/houses",
      //     JSON.stringify({
      //       item: this.item,
      //       house: this.house,
      //     })
      //   )
      //   .then((data) => {
      //     console.log(data);
      //   });
      this.sendFile();
      this.itemImages.splice(0);
    },
    sendFile() {
      if (this.itemImages !== null && this.itemImages.length > 0) {
        for (let img of this.itemImages) {
          let formData = new FormData();
          formData.append("file", img);
          formData.append("type", "item");
          axios
            .post("file", formData, {
              headers: { "Content-Type": "multipart/form-data" },
            })
            .then((data) => {
              console.log(data);
            })
            .catch((error) => {
              console.log(error);
            });
        }
      }
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
