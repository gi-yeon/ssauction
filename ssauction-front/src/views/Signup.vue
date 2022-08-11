<template>
  <div style="margin-top: 40px">
    <h1>회원가입</h1>
    <br />
  </div>

  <!--이메일-->
  <div>
    <input
      class="joinInput"
      id="user_email"
      type="email"
      v-model="userEmail"
      placeholder="이메일"
      :state="emailValidation"
    />
    <button class="joinCheckBtn" @click="emailCheck">이메일 중복 체크</button>
    <br />
    <br />

    <!--비밀번호-->
    <input
      id="user_pwd"
      type="password"
      v-model="userPwd"
      placeholder="8~20자의 비밀번호를 입력해주세요."
      :state="pwdValidation"
    />
    <br />
    <br />
    <input
      id="user_pwd2"
      type="password"
      v-model="pwdCheck"
      placeholder="비밀번호 확인"
      :state="pwdCheckValidation"
    />
    <br />
    <p v-if="pwdCheck.length == 0" style="color: grey">
      비밀번호를 입력해주세요.
    </p>
    <p v-else-if="userPwd === pwdCheck">비밀번호가 일치합니다.</p>
    <p v-else style="color: red">비밀번호가 일치하지 않습니다.</p>

    <!--닉네임--->
    <input
      class="joinInput"
      id="user_nickname"
      type="String"
      v-model="userNickname"
      placeholder="닉네임"
      :state="nicknameValidation"
    /><button class="joinCheckBtn" @click="nicknameCheck">
      닉네임 중복 체크
    </button>
    <br />
    <br />

    <!--휴대폰 번호-->
    <input
      id="user_phone_no"
      type="String"
      v-model="userPhoneNo"
      placeholder="전화번호 ex) 01012345678"
      :state="phoneValidation"
    />
    <br />
    <br />
    <button @click="submit" width="500px" class="btn_yellow">회원가입</button>
    <br />
    <br />
    <!-- <a href=""
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
    /></a> -->
  </div>
</template>

<script lang="ts" setup>
</script>

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
      emailChecked: false,
      nicknameChecked: false,
    };
  },

  computed: {
    emailValidation() {
      let re =
        /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
      return this.emailChecked && re.test(this.userEmail);
    },
    pwdValidation() {
      return this.userPwd.length >= 8 && this.userPwd.length <= 20;
    },
    pwdCheckValidation() {
      return (
        this.userPwd == this.pwdCheck &&
        this.pwdCheck.length >= 8 &&
        this.pwdCheck.length <= 20
      );
    },
    nicknameValidation() {
      return this.nicknameChecked;
    },
    phoneValidation() {
      return this.userPhoneNo != "";
    },
  },

  mounted() {},

  methods: {
    submit: function () {
      if (
        this.emailValidation &&
        this.pwdValidation &&
        this.pwdCheckValidation &&
        this.nicknameValidation &&
        this.phoneValidation
      ) {
        const obj = {
          userEmail: this.userEmail,
          userPwd: this.userPwd,
          userPhoneNo: this.userPhoneNo,
          userNickname: this.userNickname,
        };
        axios.post("/users/join", JSON.stringify(obj)).then(({ data }) => {
          if (data === "OK") {
            alert("SSAUCTION에 오신 것을 환영합니다.");
            this.$router.push({ name: "Login" });
          } else {
            alert("입력 정보를 확인해주세요.");
          }
        });
      } else if (!this.emailValidation) {
        alert("이메일을 확인해주세요.");
      } else if (!this.pwdValidation) {
        alert("8~20자의 비밀번호를 입력해주세요.");
      } else if (!this.pwdCheckValidation) {
        alert("비밀번호 확인란을 확인해주세요.");
      } else if (!this.nicknameValidation) {
        alert("닉네임을 확인해주세요.");
      } else if (!this.phoneValidation) {
        alert("전화번호를 확인해주세요.");
      } else {
        alert("입력 정보를 확인해주세요.");
      }
    },

    emailCheck() {
      if (this.userEmail != "") {
        axios.get("users/checkEmail/" + this.userEmail).then(({ data }) => {
          if (data === "success") {
            this.emailChecked = true;
            alert("사용 가능한 이메일입니다.");
          } else {
            alert("이미 사용중인 이메일입니다.");
          }
        });
      } else {
        alert("이메일을 입력해주세요");
      }
    },

    nicknameCheck() {
      if (this.userNickname != "") {
        axios
          .get("users/checkNickname/" + this.userNickname)
          .then(({ data }) => {
            if (data === "success") {
              this.nicknameChecked = true;
              alert("사용 가능한 닉네임입니다.");
            } else {
              alert("이미 사용중인 닉네임입니다.");
            }
          });
      } else {
        alert("닉네임을 입력해주세요");
      }
    },
  },
};
</script>

<style>
.joinInput {
  width: 390px;
}

.joinCheckBtn {
  margin-left: 10px;
  margin-right: 0px;
  font-size: 0.8rem;
  font-weight: 700;

  text-align: center;

  display: inline-block;
  width: auto;
  height: 40px;

  background-color: bisque;
  border-color: bisque;
  border-radius: 4px;
  border: none;
  box-shadow: none;
  outline: none;
}
</style>
