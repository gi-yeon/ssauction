<template>
  <br />
  <h1>비밀번호 변경</h1>
  <br />

  <label for="user_phoneNo">전화번호: </label>
  <input id="user_phoneNo" type="string" v-model="phoneNo" />
  <h1>{{ phoneNo }}</h1>
  <label for="user_email">아이디(이메일): </label>
  <input id="user_email" type="string" v-model="email" />
  <h1>{{ userId }}</h1>
  <label for="user_password">새 비밀번호: </label>
  <input id="user_password" type="password" v-model="password" />
  <h1>{{ userId }}</h1>
  <label for="user_password2">비밀번호 확인: </label>
  <input id="user_password2" type="password" v-model="password2" />
  <h1>{{ userId }}</h1>
  <button @click="ResetPwd">Reset Password</button>
</template>

<script>
  import http from "@/utils/http";

  export default {
  name: "SsauctionResetPwd",

  data() {
    return {
      phoneNo: "",
      email: "",
      password: "",
      password2: "",
      returnValue: {},
    };
  },

  mounted() {},

  methods: {
    ResetPwd: function () {
      console.log("ResetPwd start");
      console.log(this.phoneNo);
      console.log(this.email);
      console.log(this.password);
      console.log(this.password2);
      
      if (this.password === this.password2) {
        const obj = {
          userPwd: this.password,
        };
        console.log("start putting");
        http.put("/users/resetPwd/" + this.phoneNo + "/" + this.email, JSON.stringify(obj),).then(({ data }) => {
        console.log(data.userPhoneNo);
        console.log(data.userEmail);
        this.returnValue = data;
        console.log(this.returnValue);
          if (this.returnValue !== "") {
            alert("비밀번호가 변경되었습니다.");
          } else {
            alert("아이디(이메일 주소)가 올바르지 않습니다.");
          }
        });
      } else {
        alert("새 비밀번호가 일치하지 않습니다.");
      }
    },
  },
};
</script>