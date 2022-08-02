<template>
  <br />
  <h1>아이디 찾기</h1>
  <br />

  <label for="user_phoneNo">전화번호: </label>
  <input id="user_phoneNo" type="string" v-model="phoneNo" />
  <h1>{{ phoneNo }}</h1>
  <button @click="findId">Find ID</button>

  <div v-if="modalSmsCode == true">
    <br/>
    <br/>
    <h3>인증코드 입력</h3>
      <label for="user_smsCode">인증코드: </label>
      <input id="user_smsCode" type="string" v-model="smsCode" />
      <h1>{{ smsCode }}</h1>
      <button @click="verifyCode">Verify Code</button>
  </div>
</template>

<script>
  import axios from "@/utils/axios";

  export default {
  name: "SsauctionFindId",

  data() {
    return {
      phoneNo: "",
      userEmail: "",
      returnValue: {},
      modalSmsCode : false,
      randCode: null,
      smsCode: ""
    };
  },

  mounted() {},

  methods: {
    findId: function () {
      // console.log("FindId start");
      // console.log(this.phoneNo);
      // console.log("start getting");
      axios.get("/users/findId/" + this.phoneNo).then(({ data }) => {
        // console.log(data.userPhoneNo);
        // console.log(data.userEmail);
        this.userEmail = data.userEmail;
        this.returnValue = data;
        // console.log(this.returnValue);
        if (this.returnValue !== "") {
          // 인증번호 입력창 띄워주기 + axios.post 요청으로 sms 발송
          this.modalSmsCode = true;
          axios.post("/users/sendSMS/" + this.phoneNo).then((smsData) => {
            // console.log(smsData);
            this.randCode = smsData.data;
            // console.log(`randCode: ${this.randCode}`);
            });
          } else {
          alert("해당 유저 정보가 없습니다.");
        }
      });
    },
    verifyCode: function() {
      // console.log(parseInt(this.smsCode));
      // console.log(parseInt(this.randCode));
      // if (인증번호 === 사용자입력값) { 아이디 정보 반환 }
      if (parseInt(this.smsCode) === parseInt(this.randCode)) {
        alert("아이디(이메일 주소)는 다음과 같습니다.\n" + this.userEmail);
        this.$router.push('/login')
      } else {
        alert("인증번호가 올바르지 않습니다.");
      }
    },
  },
};
</script>

// 220729
// 휴대폰 번호가 유저 테이블에 있음을 확인하면,
// 포스트 axios로 ('/users/sendSMS') 연동해서
// 해당 휴대폰번호에 sms로 인증번호 보내고
// 인증번호를 백에서 받아와서 입력한 번호와 일치할 경우 아이디 정보 반환