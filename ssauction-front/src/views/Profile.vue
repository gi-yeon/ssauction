<template>
  <div>
    <br />
    <h1>{{ user.loginUser.userNickname }}님의 프로필</h1>
    <div v-show="user.isLogin">
      <br />
      <b-modal id="modal-3" scrollable title="Update item" size="lg">
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
              <img v-bind:src="'data:image/png;base64,' + element.img" />
              <button @click="deleteImgs(idx)">delete</button>
            </div>
          </draggable>
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
        <div class="card-body">
          <h5 class="card-title">UserProfile</h5>
          <div class="row">
            <div class="col-sm-4">
              <div class="card">
                <div class="card-body">
                  <div class="img-container">
                    <div>유저사진</div>
                    <img
                      v-if="hasImg"
                      v-bind:src="'data:image/png;base64,' + userMainImg"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-8">
              <div class="card">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-6">
                      유저닉네임 : {{ user.loginUser.userNickname }}
                    </div>
                    <br />
                    <div class="col-sm-6">
                      유저등급 : {{ user.loginUser.userGrade }}
                    </div>
                    <div class="col-sm-6">유저로그인 : {{ user.isLogin }}</div>
                    <br />
                    <div class="col-sm-6">유저설명 : {{ userDesc }}</div>
                    <div class="col-sm-6">유저한줄평 : {{ userComment }}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <br />
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">Sell Items</h5>
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
                  <div class="card-body">
                    <div class="sell-container">
                      <img
                        v-bind:src="
                          'data:image/png;base64,' + index.itemImgs[0].img
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
        <div class="card-body">
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
                  <div class="card-body">
                    <div class="sell-container">
                      <img
                        v-bind:src="
                          'data:image/png;base64,' + index.itemImgs[0].img
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
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapState } from "vuex";
import { defineComponent } from "vue";
import Carousel from "@/components/CarouselComp.vue";
import { VueDraggableNext } from "vue-draggable-next";

export default defineComponent({
  name: "SsauctionProfile",
  components: {
    Carousel,
    draggable: VueDraggableNext,
  },
  computed: {
    ...mapState(["user"]),
  },
  data() {
    return {
      userNo: 0,
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
    };
  },

  mounted() {
    this.userNo = this.$store.getters["user/userNo"];
    this.getUserInfo();
    this.getUserImg();
    this.getItemInfo();
  },

  methods: {
    getUserInfo: async function () {
      await axios
        .get("/users/profile/" + this.userNo)
        .then(({ data }) => {
          console.log(data);
          this.userDesc = data.userDesc;
          this.userComment = data.userComment;
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
      console.log(index.item);
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
      const deletejson = JSON.stringify({ indexs: deleteArr });
      console.log(deletejson);
      const deleteblob = new Blob([deletejson], { type: "application/json" });

      // html의 form 태그를 이용해 submit하면 formData 객체와 multipart/form-data 형식으로 전달된다.
      // form 태그를 이용하고 있지 않지만 이용한 것처럼 요청하기 위해 새 formData 객체를 만든다.
      let formData = new FormData();
      formData.append("deleteDto", deleteblob);

      await axios
        .put("/houses/update/" + this.info.houseNo, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then(({ data }) => {
          console.log(data);
          alert("사진 수정 완료");
        });
    },
  },
});
</script>

<style>
.resize {
  height: 300px;
  width: 300px;
}
</style>
