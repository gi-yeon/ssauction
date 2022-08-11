<template>
  <div>
    <br />
    <h1>{{ user.loginUser.userNickname }}님의 프로필</h1>
    <div v-show="user.isLogin">
      <router-link to="/profile/update">Profile Update</router-link>
      <br />
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">UserProfile</h5>
          <div class="row">
            <div class="col-sm-4">
              <div class="card">
                <div class="card-body">
                  <div>
                    <div>유저사진uri : {{ userImgUri }}</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-8">
              <div class="card">
                <div class="card-body">
                  <div class="row">
                    <div class="col-sm-6">
                      유저번호 : {{ user.loginUser.userNo }}
                    </div>
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
            <div class="col-sm-4" v-for="index in sellItem" :key="index">
              <div>
                <div class="card">
                  <div class="card-body">
                    <div>
                      <img
                        v-bind:src="
                          'data:image/png;base64,' + index.itemMainImg
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

export default {
  name: "SsauctionProfile",
  computed: {
    ...mapState(["user"]),
  },

  data() {
    return {
      userNo: 0,
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
