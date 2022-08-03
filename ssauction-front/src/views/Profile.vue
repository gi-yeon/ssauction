<template>
  <br />
  <h1>{{ userNickname }}님의 프로필</h1>
  <div v-show="isLogin">
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
                  <div class="col-sm-6">유저번호 : {{ userNo }}</div>
                  <div class="col-sm-6">유저닉네임 : {{ userNickname }}</div>
                  <br />
                  <div class="col-sm-6">유저등급 : {{ userGrade }}</div>
                  <div class="col-sm-6">유저로그인 : {{ isLogin }}</div>
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
                    {{ index.itemMainImg.itemImgUri }}
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
</template>

<script>
import axios from "@/utils/axios.js";

export default {
  name: "SsauctionProfile",

  data() {
    return {
      userNickname: "",
      userNo: 0,
      userGrade: 0,
      userDesc: "",
      userComment: "",
      userImgUri: "",
      sellItem: [],
      buyItem: [],
    };
  },

  mounted() {
    this.userNickname = this.$store.state.user.nickname;
    this.userNo = this.$store.state.user.userNo;
    this.userGrade = this.$store.state.user.grade;
    this.isLogin = this.$store.state.user.isLogin ? true : false;
    this.getUserInfo();
    this.getItemInfo();
  },

  methods: {
    getUserInfo: async function () {
      console.log(this.userNickname);
      console.log(this.userNo);
      console.log(this.isLogin);
      await axios
        .get("/users/profile/" + this.userNo)
        .then(({ data }) => {
          this.userDesc = data.userDesc;
          this.userComment = data.userComment;
          this.userImgUri = data.userImgUri;
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    getItemInfo: async function () {
      console.log(this.userNickname);
      console.log(this.userNo);
      console.log(this.isLogin);
      await axios
        .get("/houses/" + this.userNo)
        .then(({ data }) => {
          console.log(data);
          this.sellItem = data.sellItemsList;
          console.log(this.sellItem[0].itemMainImg);
          console.log(this.sellItem);
          console.log(this.buyItem);
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
  },
};
</script>

<style></style>
