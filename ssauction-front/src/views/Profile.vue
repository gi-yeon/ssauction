<template>
  <div>
    <h2>{{ user.loginUser.userNickname }}님의 프로필</h2>
    <br />
    <div v-show="user.isLogin">
      <br />
      <b-modal
        id="modal-3"
        scrollable
        title="Update item"
        size="lg"
        v-model="isHide"
      >
        <div class="flex m-10">
          <draggable
            class="dragArea list-group w-full"
            v-model="updateImgs"
            @change="log"
          >
            <div
              class="list-group-item bg-gray-300 m-1 p-3 rounded-md text-center"
              v-for="(element, idx) in updateImgs"
              :key="element"
            >
              <img
                class="resize"
                v-bind:src="'data:image/png;base64,' + element.img"
              />
              <button @click="deleteImgs(idx)">delete</button>
            </div>
          </draggable>
        </div>
        <div class="mb-3">
          <el-upload
            class="avatar-uploader"
            action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
          </el-upload>

          <input
            type="file"
            ref="addImage"
            @change="imageUpload"
            multiple
            style="margin-bottom: 0.5rem"
          />
          <item-image-preview
            v-for="(file, index) in addImages"
            :key="file.lastModified"
            :previewfile="file"
            :index="index"
            @delete-img="deleteImg"
            style="margin-bottom: 0.5rem; margin-left: 0.5rem"
          />
        </div>
        {{ updateImgs }}
        <p class="my-4">Hello from modal!</p>
        <label>item name</label>
        <br />
        <input type="text" :value="info.itemName" />
        {{ info.itemName }}
        <br />
        <label>item model number</label>
        <br />
        <input type="text" :value="info.modelNo" />
        {{ info.modelNo }}
        <br />
        <label>item start price</label>
        <br />
        <input type="text" :value="info.startPrice" />
        {{ info.startPrice }}
        <br />
        <label>item register time</label>
        <br />
        <input type="text" :value="info.regTime" />
        {{ info.regTime }}
        <br />
        <label>item auction time</label>
        <br />
        <input type="text" :value="info.auctionTime" />
        {{ info.auctionTime }}
        <br />
        <label>item deal status</label>
        <br />
        <input type="text" :value="info.dealStatus" />
        {{ info.dealStatus }}
        <br />
        <label>item describtion</label>
        <br />
        <input type="text" :value="info.desc" />
        {{ info.desc }}
        <template v-slot:footer="{ cancel }">
          <b-button @click="cancel" variant="danger" data-baz="buz"
            >delete
          </b-button>
          <b-button @click="updateProfile()" variant="primary" data-baz="buz"
            >Update
          </b-button>
        </template>
      </b-modal>
      <div class="card">
        <div
          class="card-body p-2"
          style="padding: 0.5rem; padding-bottom: 0rem"
        >
          <h4 class="card-title" style="display: inline-block">기본 정보</h4>
          <br />
          <div class="row">
            <div class="col-sm-4">
              <div class="card">
                <div
                  class="card-body"
                  style="padding: 0.5rem; padding-bottom: 0rem"
                >
                  <div class="img-container">
                    <div>유저사진</div>
                    <img
                      class="resize"
                      v-if="hasImg"
                      v-bind:src="'data:image/png;base64,' + userMainImg"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-8">
              <div class="card">
                <div
                  class="card-body"
                  style="padding: 0.5rem; padding-bottom: 0rem"
                >
                  <div class="row">
                    <div
                      class="col-sm-6"
                      style="padding: 0rem; padding-left: 0.5rem"
                    >
                      <button
                        type="button"
                        class="btn btn-secondary btn-sm col-sm-4"
                        disabled
                      >
                        아이디 (이메일)
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-8"
                        disabled
                      >
                        {{ userEmail }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-6"
                      style="padding: 0rem; padding-right: 0.5rem"
                    >
                      <button
                        type="button"
                        class="btn btn-secondary btn-sm col-sm-4"
                        disabled
                      >
                        닉네임
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-8"
                        disabled
                      >
                        {{ user.loginUser.userNickname }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-6"
                      style="padding: 0rem; padding-left: 0.5rem"
                    >
                      <button
                        type="button"
                        class="btn btn-secondary btn-sm col-sm-4"
                        disabled
                      >
                        휴대폰 번호
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-8"
                        disabled
                      >
                        {{ userPhoneNo }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-6"
                      style="padding: 0rem; padding-right: 0.5rem"
                    >
                      <button
                        type="button"
                        class="btn btn-secondary btn-sm col-sm-4"
                        disabled
                      >
                        유저 등급
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-8"
                        disabled
                      >
                        {{ user.loginUser.userGrade }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-12"
                      style="
                        padding: 0rem;
                        padding-left: 0.5rem;
                        padding-right: 0.5rem;
                      "
                    >
                      <button
                        type="button"
                        class="btn btn-secondary btn-sm col-sm-2"
                        disabled
                      >
                        상태 메시지
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-10"
                        disabled
                      >
                        {{ userComment }}
                      </button>
                    </div>
                    <br />
                    <div
                      class="col-sm-12"
                      style="
                        padding: 0rem;
                        padding-left: 0.5rem;
                        padding-right: 0.5rem;
                      "
                    >
                      <button
                        type="button"
                        class="btn btn-secondary btn-sm col-sm-2"
                        disabled
                      >
                        자기 소개
                      </button>
                      <button
                        type="button"
                        class="btn btn-light btn-sm col-sm-10"
                        disabled
                      >
                        {{ userDesc }}
                      </button>
                    </div>
                    <br />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <br />
      <div class="card">
        <div
          class="card-body p-2"
          style="padding: 0.5rem; padding-bottom: 0rem"
        >
          <h4 class="card-title">판매 내역</h4>
          <div class="row">
            <div
              v-b-modal.modal-1
              class="col-sm-4"
              v-for="index in sellItem"
              :key="index"
              @click="getItemDetail(index)"
            >
              <b-modal scrollable id="modal-1" title="Selling item" size="lg">
                <Carousel :images="images" />
                <p class="my-4">Hello from modal!</p>
                <br />
                {{ info.itemName }}
                <br />
                {{ info.modelNo }}
                <br />
                {{ info.startPrice }}
                <br />
                {{ info.regTime }}
                <br />
                {{ info.auctionTime }}
                <br />
                {{ info.dealStatus }}
                <br />
                {{ info.desc }}
                <template v-slot:footer="{ ok }">
                  <b-button
                    v-b-modal.modal-3
                    @click="ok"
                    variant="primary"
                    data-baz="buz"
                    >Update</b-button
                  >
                </template>
              </b-modal>
              <div>
                <div class="card">
                  <div
                    class="card-body"
                    style="padding: 0.5rem; padding-bottom: 0rem"
                  >
                    <div class="sell-container">
                      <img
                        class="resize"
                        v-bind:src="
                          'data:image/png;base64,' +
                          index.itemImgs[getItemImgsIdx(index)].img
                        "
                      />

                      <br />
                      {{ index.item.itemName }}
                      <br />
                      {{ index.item.modelNo }}
                      <br />
                      {{ index.item.startPrice }}
                      <br />
                      {{ index.item.regTime }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-body" style="padding: 0.5rem; padding-bottom: 0rem">
          <h5 class="card-title">Purchase Items</h5>
          <div class="row">
            <div
              v-b-modal.modal-2
              class="col-sm-4"
              v-for="index in buyItem"
              :key="index"
              @click="getItemDetail(index)"
            >
              <b-modal
                scrollable
                id="modal-2"
                title="Purchased item"
                ok-title="update"
                size="lg"
              >
                <Carousel :images="images" />
                <p class="my-4">Hello from modal2!</p>
                <br />
                {{ info.itemName }}
                <br />
                {{ info.modelNo }}
                <br />
                {{ info.startPrice }}
                <br />
                {{ info.regTime }}
                <br />
                {{ info.auctionTime }}
                <br />
                {{ info.dealStatus }}
                <br />
                {{ info.desc }}
                <template v-slot:footer="{ ok }">
                  <b-button
                    v-b-modal.modal-3
                    @click="ok"
                    variant="primary"
                    data-baz="buz"
                    >Update</b-button
                  >
                </template>
              </b-modal>
              <div>
                <div class="card">
                  <div
                    class="card-body"
                    style="padding: 0.5rem; padding-bottom: 0rem"
                  >
                    <div class="sell-container">
                      <img
                        class="resize"
                        v-bind:src="
                          'data:image/png;base64,' +
                          index.itemImgs[getItemImgsIdx(index)].img
                        "
                      />
                      <br />
                      {{ index.item.itemName }}
                      <br />
                      {{ index.item.startPrice }}원 /
                      {{ index.item.regTime.substr(0, 10) }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapState } from "vuex";
import { defineComponent } from "vue";
import Carousel from "@/components/CarouselComp.vue";
import { VueDraggableNext } from "vue-draggable-next";
import ItemImagePreview from "@/components/ItemImagePreview.vue";

export default defineComponent({
  name: "SsauctionProfile",
  components: {
    Carousel,
    draggable: VueDraggableNext,
    ItemImagePreview,
  },
  computed: {
    ...mapState(["user"]),
  },
  data() {
    return {
      userNo: 0,
      userEmail: "",
      userPhoneNo: "",
      userDesc: "",
      userComment: "",
      userMainImg: [],
      sellItem: [],
      buyItem: [],
      hasImg: false,
      images: [],
      info: {},
      enabled: true,
      updateImgs: {},
      delImgs: [],
      dragging: true,
      isHide: false,
      addImages: [],
    };
  },

  mounted() {
    this.userNo = this.$store.getters["user/userNo"];
    this.getUserInfo();
    this.getUserImg();
    this.getItemInfo();
  },

  methods: {
    deleteImg(index) {
      this.addImages.splice(index, 1);
      console.log(this.$refs.addImage);
    },
    getUserInfo: async function () {
      await axios
        .get("/users/profile/" + this.userNo)
        .then(({ data }) => {
          console.log(data);
          this.userDesc = data.userDesc;
          this.userComment = data.userComment;
          this.userImgUri = data.userImgUri;
          this.userEmail = data.userEmail;
          this.userPhoneNo = data.userPhoneNo;
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    getUserImg: async function () {
      await axios
        .get("/users/profile/img/" + this.userNo)
        .then(({ data }) => {
          console.log(data);
          this.hasImg = data.userMainImg !== null;
          if (this.hasImg) this.userMainImg = data.userMainImg;
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },

    getItemInfo: async function () {
      console.log(this.userNo);
      await axios
        .get("/houses/profile/" + this.userNo)
        .then(({ data }) => {
          console.log(data);
          console.log(data[0]);
          if (data === null) {
            console.log("null data");
          } else {
            this.sellItem = data.sellItemsList;
            this.buyItem = data.buyItemsList;
            console.log(this.sellItem[0].itemImgs);
            console.log(this.sellItem);
            console.log(this.buyItem);
          }
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    // updateItem:async function(){
    //   //await axios.put("/houses/update/")
    // },
    getItemDetail: async function (index) {
      console.log(index.item);
      this.images = index.itemImgs;
      this.updateImgs = index.itemImgs;
      this.info = index.item;
    },
    ok: async function (index) {
      console.log(index);
      console.log("here is ok");
    },
    log(event) {
      console.log(event);
    },
    clicklist() {
      console.log(this.images);
    },
    deleteImgs(idx) {
      this.delImgs.push(this.updateImgs[idx].imgNo);
      this.updateImgs.splice(idx, 1);
      console.log(this.delImgs);
    },
    updateProfile: async function () {
      const deleteArr = [];
      for (let no of this.delImgs) {
        console.log(this.delImgs);
        console.log(no);
        deleteArr.push(no);
      }
      const idxs = [];
      for (let idx of this.updateImgs) {
        console.log(idx.imgNo);
        idxs.push(idx.imgNo);
      }
      const deletejson = JSON.stringify({ indexs: deleteArr });
      const sortjson = JSON.stringify({ indexs: idxs });
      console.log(deletejson);
      console.log(sortjson);
      const deleteblob = new Blob([deletejson], { type: "application/json" });
      const sortblob = new Blob([sortjson], { type: "application/json" });
      // html의 form 태그를 이용해 submit하면 formData 객체와 multipart/form-data 형식으로 전달된다.
      // form 태그를 이용하고 있지 않지만 이용한 것처럼 요청하기 위해 새 formData 객체를 만든다.
      let formData = new FormData();
      formData.append("deleteDto", deleteblob);
      formData.append("sortDto", sortblob);

      for (let img of this.addImages) {
        console.log(img);
        formData.append("files", img);
      }

      await axios
        .put("/houses/update/" + this.info.houseNo, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then(({ data }) => {
          console.log(data);
          alert("사진 수정 완료");
          this.isHide = false;
        });
    },
    imageUpload() {
      // $refs를 통해 DOM에 있는 input의 files에 직접 접근하면 FileList 객체가 반환된다.
      // FileList 객체는 read only이므로 다루기 어렵다.
      // 아래의 코드를 통해 this.itemImages 배열에 files에 있는 File 객체를 그대로 복사한다.
      Array.prototype.push.apply(this.addImages, this.$refs.addImage.files);
      console.log(this.addImages);
      this.$refs.addImage.value = "";
    },
    getItemImgsIdx(idxs) {
      let cnt = 0;
      console.log(idxs);
      for (let id of idxs.itemImgs) {
        console.log(id);
        console.log(id.main);
        if (id.main) {
          console.log(cnt);
          return cnt;
        }
        cnt++;
      }
      console.log(cnt);
      return 0;
    },
  },
});
</script>

<style>
.resize {
  width: 100%;
}
</style>
