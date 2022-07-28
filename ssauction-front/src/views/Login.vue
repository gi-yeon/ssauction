<template>
<br>
<br>
<h1>로그인</h1>
<br>

  <div>
    <!-- <label for="user_email">이메일: </label> -->
    <input id="user_email" type="email" placeholder="이메일">
    <br>
    <br>
    <!-- <label for="user_pwd">비밀번호: </label> -->
    <input id="user_pwd" type="password" label="비밀번호" placeholder="비밀번호">
    <br>
    <br>
    <button width=500px class="btn_yellow" >로그인</button>
    <br>
    <br>
    <button class="btn_pink">아이디 찾기</button> &nbsp;
    <button class="btn_pink">비밀번호 찾기</button>
    <br>
    <br>
    <br>
    <a href=""><img src="@\assets\imgs\google.png" alt="구글 로그인" class="logo_resize"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href=""><img src="@\assets\imgs\naver.png" alt="네이버 로그인" class="logo_resize"></a>
    <!-- <br>
    <button class="btn_pink">구글로 회원가입</button> &nbsp;
    <button class="btn_pink">네이버로 회원가입</button> &nbsp; -->

  </div>

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

<style>
/* h1 {
  margin-left:110px
} */

input {
  width: 500px;
  height: 50px;
  border:0;
  background-color: rgb(230, 230, 230);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  padding-left:30px
}

input:focus {
  outline: 3px rgb(94, 94, 94)
}

.btn_pink {
  width:245px;
  height: 50px;
  border: 0;
  background-color:rgb(255, 170, 165);
  border-radius: 20px;
  color:rgb(94, 94, 94);
  text-align: center;
}

.btn_yellow {
  width:500px;
  height: 50px;
  border: 0;
  background-color:rgb(255, 211, 182);
  border-radius: 20px;
  color:rgb(94, 94, 94);
  text-align: center;
}

.logo_resize {
  width:60px;
  height:60px;
}
</style>
