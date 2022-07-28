<template>
  <br />
  <h1>비밀번호 변경</h1>
  <br />

  <label for="user_phoneNo">전화번호: </label>
  <input id="user_phoneNo" type="string" v-model="phoneNo" />
  <h1>{{ phoneNo }}</h1>
  <label for="user_email">아이디(이메일): </label>
  <input id="user_email" type="string" v-model="email" />
  <h1>{{ email }}</h1>

  <button @click="ResetPwd">비밀번호 변경하기</button>
</template>

<script>
  import http from "@/utils/http";
  export default {
  name: "SsauctionResetPwd",

  data() {
    return {
      phoneNo: "",
      email: "",
      returnValue: {},
    };
  },

  mounted() {},

  methods: {
    ResetPwd: function () {
      console.log("ResetPwd start");
      console.log(this.phoneNo);
      console.log(this.email);
      
      console.log("start putting");
      // 전화번호 & 아이디 검사하여 일치하는 유저정보를 ResetPwd2로 router 이용하여 보내기
      http.get("/users/findId/" + this.phoneNo).then(({ data }) => {
        console.log(data.userPhoneNo);
        console.log(data.userEmail);
        this.returnValue = data;
        console.log(this.returnValue);
        if (this.returnValue !== "") {
          if (this.email === data.userEmail) {
            // redirect to ResetPwd2
            this.$router.push({name: 'ResetPwd2', params: {phoneNo: this.phoneNo, email: this.email}})
          } else {
            alert("아이디(이메일 주소) 정보가 일치하지 않습니다.");
          }
        } else {
          alert("전화번호 정보가 없습니다.");
        }
      });
    },
  },
};
</script>