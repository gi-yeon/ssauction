<template>
  <br />
  <h1>회원가입</h1>
  <br />

  <div>
    <input
      id="user_email"
      type="email"
      v-model="userEmail"
      placeholder="이메일"
    />
    <br />
    <br />
    <input
      id="user_pwd"
      type="password"
      v-model="userPwd"
      placeholder="비밀번호"
    />
    <br />
    <br />
    <input
      id="user_pwd2"
      type="password"
      v-model="pwdCheck"
      placeholder="비밀번호 확인"
    />
    <br />
    <br />
    <input
      id="user_nickname"
      type="String"
      v-model="userNickname"
      placeholder="닉네임"
    />
    <br />
    <br />
    <input
      id="user_phone_no"
      type="String"
      v-model="userPhoneNo"
      placeholder="전화번호 010-0000-0000"
    />
    <br />
    <br />
    <button @click="submit" width="500px" class="btn_yellow">회원가입</button>
    <br />
    <br />
    <a href=""
      ><img
        src="@\assets\imgs\google.png"
        alt="구글 로그인"
        class="logo_resize" /></a
    >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href=""
      ><img
        src="@\assets\imgs\naver.png"
        alt="네이버 로그인"
        class="logo_resize"
    /></a>
  </div>
</template>


<script>
import axios from "@/utils/axios";

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
        axios.post("/users/join", JSON.stringify(obj)).then(({ data }) => {
          console.log(data);
          if (data === "OK") {
            alert("SSAUCTION에 오신 것을 환영합니다.");
            // router.push({ name: 'Login' })
          } else {
            alert("입력 정보를 확인해 주세요.");
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
