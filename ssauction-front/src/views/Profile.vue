<template>
  <br />
  <br />
  <h1>{{ userNickname }}님의 프로필</h1>
  <br />
  <br />
  <div v-show="isLogin">
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
</template>

<script>
import axios from "@/utils/axios.js";

export default {
  name: "SsauctionProfile",

  data() {
    return {
      userNickname:"",
      userNo:0,
      userGrade:0,
      userDesc:"",
      userComment:"",
      userImgUri:"",
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
    getUserInfo: async function(){
       await axios.get("/users/"+this.userNo).then(({data})=>{
        this.userDesc=data.userDesc;
        this.userComment=data.userComment;
        this.userImgUri=data.userImgUri;
       });
    }
  },
};
</script>

<style></style>
