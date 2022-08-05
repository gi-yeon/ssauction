<template>
  <div>
    <br />
    <br />
    <h1>{{ userNickname }}님의 프로필</h1>
    <br />
    <br />
    <div v-show="isLogin">
      <input v-model="nickname" />
      <br />
      <button @click="putUserName">닉네임 전송</button>
      <br />
      <input v-model="desc" />
      <br />
      <input v-model="comment" />
      <br />
      <button @click="putUserInfo">프로필 전송</button>
      <br />
      <input v-model="imgUri" />
      <br />
      <button @click="putUserImg">uri 전송</button>
      <br />
      <br />
      <h3>유저번호 : {{ userNo }}</h3>
      <br />
      <h3>유저등급 : {{ userGrade }}</h3>
      <br />
      <h3>유저로그인 : {{ isLogin }}</h3>
      <br />
      <h3>유저설명 : {{ userDesc }}</h3>
      <br />
      <h3>유저한줄평 : {{ userComment }}</h3>
      <br />
      <h3>유저사진uri : {{ userImgUri }}</h3>
      <br />
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";

export default {
  name: "SsauctionProfileUpdate",

  data() {
    return {
      userNickname: "",
      userNo: 0,
      userDesc: "",
      userComment: "",
      userImgUri: "",
      nickname: "",
      desc: "",
      comment: "",
      imgUri: "",
    };
  },

  mounted() {
    this.userNickname = this.$store.state.user.nickname;
    this.userNo = this.$store.state.user.userNo;
    this.userGrade = this.$store.state.user.grade;
    this.isLogin = this.$store.state.user.isLogin ? true : false;
    this.getUserInfo();
  },

  methods: {
    putUserInfo: async function () {
      const obj = {
        userDesc: this.desc,
        userComment: this.comment,
      };
      await axios
        .put("/users/profile/info/" + this.userNo, JSON.stringify(obj))
        .then(({ data }) => {
          console.log(data);
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    putUserImg: async function () {
      const obj = {
        imgName: this.imgName,
        imgUri: this.imgUri,
      };

      await axios
        .put("/users/profile/img/" + this.userNo, JSON.stringify(obj))
        .then(({ data }) => {
          console.log(data);
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    putUserName: async function () {
      const obj = {
        userNickname: this.nickname,
      };

      await axios
        .put("/users/profile/name/" + this.userNo, JSON.stringify(obj))
        .then(({ data }) => {
          this.$store.dispatch("user/getNickname", data.userNickname);
          console.log(data);
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    getUserInfo: async function () {
      await axios.get("/users/profile/" + this.userNo).then(({ data }) => {
        this.userDesc = data.userDesc;
        this.userComment = data.userComment;
        this.userImgUri = data.userImgUri;
      });
    },
  },
};
</script>

<style></style>
