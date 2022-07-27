<template>

<br>
<h1>회원가입</h1>
<br>

<div>
  <form>
    <label for="user_email">이메일: </label>
    <input id="user_email" type="email">
    <br>
    <br>
    <label for="user_pwd">비밀번호: </label>
    <input id="user_pwd" type="password">
    <br>
    <br>
    <label for="user_pwd2">비밀번호 확인: </label>
    <input id="user_pwd2" type="password">
    <br>
    <br>

    <button>구글로 회원가입</button> &nbsp;
    <button>네이버로 회원가입</button> &nbsp;
    <button>회원가입</button>
  </form>
</div>
</template>

<script>
export default {
  name: "SsauctionSignup",

  data() {
    return {};
  },

  mounted() {},

  methods: {},
};
</script>

<style></style>
