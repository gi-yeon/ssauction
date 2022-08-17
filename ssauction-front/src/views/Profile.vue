<template>
  <div>
    <h2>{{ user.loginUser.userNickname }}님의 프로필</h2>
    <br />
    <div v-show="user.isLogin">
      <!-- <button class="btn btn-info" @click="goToUpdateInfo">수정하기</button>
      <br />
      <br /> -->
      <div class="card">
        <div class="card-body p-2">
          <h4 class="card-title" style="display: inline-block">기본 정보</h4>
          <button class="btn btn-info p-1" @click="goToUpdateInfo">수정하기</button>
          <br />
          <div class="row">
            <div class="col-sm-4">
              <div class="card">
                <div class="card-body">
                  <div>
                    <img style="max-width: 100%"
                      v-bind:src="
                        'data:image/png;base64,' + userImgUri
                      "
                    />
                    <div>프로필 사진</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-8">
              <div class="card">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-6" style="padding: 0rem; padding-left: 0.5rem;">
                      <button type="button" class="btn btn-secondary btn-sm col-sm-4" disabled>아이디 (이메일)</button>
                      <button type="button" class="btn btn-light btn-sm col-sm-8" disabled>{{ userEmail }}</button>
                    </div>
                    <br />
                    <div class="col-sm-6" style="padding: 0rem; padding-right: 0.5rem;">
                      <button type="button" class="btn btn-secondary btn-sm col-sm-4" disabled>닉네임</button>
                      <button type="button" class="btn btn-light btn-sm col-sm-8" disabled>{{ user.loginUser.userNickname }}</button>
                    </div>
                    <br />
                    <div class="col-sm-6" style="padding: 0rem; padding-left: 0.5rem;">
                      <button type="button" class="btn btn-secondary btn-sm col-sm-4" disabled>휴대폰 번호</button>
                      <button type="button" class="btn btn-light btn-sm col-sm-8" disabled>{{ userPhoneNo }}</button>
                    </div>
                    <br />
                    <div class="col-sm-6" style="padding: 0rem; padding-right: 0.5rem;">
                      <button type="button" class="btn btn-secondary btn-sm col-sm-4" disabled>유저 등급</button>
                      <button type="button" class="btn btn-light btn-sm col-sm-8" disabled>{{ user.loginUser.userGrade }}</button>
                    </div>
                    <br />
                    <div class="col-sm-12" style="padding: 0rem; padding-left: 0.5rem; padding-right: 0.5rem;">
                      <button type="button" class="btn btn-secondary btn-sm col-sm-2" disabled>상태 메시지</button>
                      <button type="button" class="btn btn-light btn-sm col-sm-10" disabled>{{ userComment }}</button>
                    </div>
                    <br />
                    <div class="col-sm-12" style="padding: 0rem; padding-left: 0.5rem; padding-right: 0.5rem;">
                      <button type="button" class="btn btn-secondary btn-sm col-sm-2" disabled>자기 소개</button>
                      <button type="button" class="btn btn-light btn-sm col-sm-10" disabled>{{ userDesc }}</button>
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
        <div class="card-body p-2">
          <h4 class="card-title">판매 내역</h4>
          <div class="row">
            <div class="col-sm-3 px-2" v-for="index in sellItem" :key="index">
              <div>
                <div class="card">
                  <div class="card-body" style="padding: 0.5rem; padding-bottom: 0rem;">
                    <router-link to="#">
                      <div>
                        <img style="max-width: 100%"
                          v-bind:src="
                            'data:image/png;base64,' + index.itemMainImg
                          "
                        />

                        <br />
                        {{ index.item.itemName }} <!-- ({{ index.item.modelNo }}) -->
                        <br />
                        {{ index.item.startPrice }}원 / {{ index.item.regTime.substr(0, 10) }}
                      </div>
                    </router-link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-body p-2">
          <h4 class="card-title">구매 내역</h4>
          <div class="row">
            <div class="col-sm-3 px-2" v-for="index in buyItem" :key="index">
              <div>
                <div class="card">
                  <div class="card-body" style="padding: 0.5rem; padding-bottom: 0rem;">
                    <router-link to="#">
                      <div>
                        <img style="max-width: 100%"
                          v-bind:src="
                            'data:image/png;base64,' + index.itemMainImg
                          "
                        />

                        <br />
                        {{ index.item.itemName }}
                        <br />
                        {{ index.item.startPrice }}원 / {{ index.item.regTime.substr(0, 10) }}
                      </div>
                    </router-link>
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

export default {
  name: "SsauctionProfile",
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
      userImgUri: "",
      sellItem: [],
      buyItem: [],
    };
  },

  mounted() {
    this.userNo = this.$store.getters["user/userNo"];
    this.getUserInfo();
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
          this.userImgUri = data.userImgUri;
          this.userEmail = data.userEmail;
          this.userPhoneNo = data.userPhoneNo;
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
            console.log(this.sellItem[0].itemMainImg);
            console.log(this.sellItem);
            console.log(this.buyItem);
          }
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
  },
};
</script>

<style></style>
