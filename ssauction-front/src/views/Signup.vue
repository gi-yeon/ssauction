<template>
  <br />
  <h1>회원가입</h1>
  <br />

  <div>
    <label for="user_email">이메일: </label>
    <input id="user_email" type="email" v-model="userEmail" />
    <h1>{{ userEmail }}</h1>
    <br />
    <br />

    <label for="user_pwd">비밀번호: </label>
    <input id="user_pwd" type="password" v-model="userPwd" />
    <h1>{{ userPwd }}</h1>
    <br />
    <br />

    <label for="user_pwd2">비밀번호 확인: </label>
    <input id="user_pwd2" type="password" v-model="pwdCheck" />
    <h1>{{ pwdCheck }}</h1>
    <br />
    <br />

    <label for="user_nickname">닉네임: </label>
    <input id="user_nickname" type="String" v-model="userNickname" />
    <h1>{{ userNickname }}</h1>
    <br />
    <br />

    <label for="user_phone_no">휴대폰 번호: </label>
    <input id="user_phone_no" type="String" v-model="userPhoneNo" />
    <h1>{{ userPhoneNo }}</h1>
    <br />
    <br />

    <button>구글로 회원가입</button> &nbsp;
    <button>네이버로 회원가입</button> &nbsp;
    <button @click="submit">회원가입</button>
  </div>
</template>

<script>
import http from "@/utils/http";
export default {
  name: "SsauctionSignup",
  data() {
    return {
      userEmail: "",
      userPwd: "",
      pwdCheck: "",
      userPhoneNo: "",
      userNickname: "",
    };
  },

  mounted() {},

  methods: {
    submit: function () {
      console.log("submit start");
      console.log(this.userEmail);
      console.log(this.userPwd);
      console.log(this.userPhoneNo);
      console.log(this.userNickname);
      if (this.userPwd === this.pwdCheck) {
        const obj = {
          userEmail: this.userEmail,
          userPwd: this.userPwd,
          userPhoneNo: this.userPhoneNo,
          userNickname: this.userNickname,
        };
        console.log("start posting");
        http.post("/users", JSON.stringify(obj)).then(({ data }) => {
          console.log(data);
          if (data === "OK") {
            alert("회원가입 성공");
          } else {
            alert("회원가입 실패");
          }
        });
      } else {
        alert("비밀번호가 다릅니다.");
      }
    },
  },
};
</script>

<style></style>
