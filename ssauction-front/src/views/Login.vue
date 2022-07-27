<template>

<br>
<h1>로그인</h1>
<br>

  <form>
    <label for="user_email">이메일: </label>
    <input id="user_email" type="email">
    <br>
    <br>
    <label for="user_pwd">비밀번호: </label>
    <input id="user_pwd" type="password" label="비밀번호">


    <br>
    <br>
    <button>아이디 찾기</button> &nbsp;
    <button>비밀번호 찾기</button>

    <br>
    <br>
    <button>구글로 회원가입</button> &nbsp;
    <button>네이버로 회원가입</button> &nbsp;
    <button>Login</button> 
  </form>

</template>

<script>
export default {
  name: "SsauctionLogin",

  data() {
    return {};
  },

  mounted() {},

  methods: {},
};
</script>

<style></style>
