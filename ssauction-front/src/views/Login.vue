<template>
  <br />
  <h1>로그인</h1>
  <br />

  <label for="user_email">이메일: </label>
  <input id="user_email" type="email" v-model="loginEmail" />
  <h1>{{ loginEmail }}</h1>
  <br />
  <br />
  <label for="user_pwd">비밀번호: </label>
  <input id="user_pwd" type="password" label="비밀번호" v-model="loginPwd" />
  <h1>{{ loginPwd }}</h1>
  <br />
  <br />
  <h1>{{ loginPwd }}</h1>
  <h1>{{ userInfo }}</h1>

  <button>아이디 찾기</button> &nbsp;
  <button>비밀번호 찾기</button>

  <br />
  <br />
  <button>구글로 회원가입</button> &nbsp;
  <button>네이버로 회원가입</button> &nbsp;
  <button @click="login">Login</button>
</template>

<script>
import http from "@/utils/http";

export default {
  name: "SsauctionLogin",

  data() {
    return {
      loginEmail: "",
      loginPwd: "",
      returnValue: {},
    };
  },

  mounted() {},

  methods: {
    login: function () {
      console.log("login start");
      console.log(this.loginEmail);
      console.log(this.loginPwd);

      const obj = {
        loginEmail: this.loginEmail,
        loginPwd: this.loginPwd,
      };
      console.log("start posting");
      http.post("/users/login", JSON.stringify(obj)).then(({ data }) => {
        console.log(data.userNo);
        console.log(data.userNickname);
        console.log(data.userGrade);
        this.returnValue = data;
        if (this.returnValue !== "") {
          alert("로그인 성공");
          this.$store.dispatch("user/getUserNo", this.returnValue.userNo);
          this.$store.dispatch(
            "user/getNickname",
            this.returnValue.userNickname
          );
          this.$store.dispatch("user/getGrade", this.returnValue.userGrade);
        } else {
          alert("로그인 실패");
        }
      });
    },
  },
};
</script>

<style></style>
