<template>
  <br />
  <h1>아이디 찾기</h1>
  <br />

  <label for="user_phoneNo">전화번호: </label>
  <input id="user_phoneNo" type="string" v-model="phoneNo" />
  <h1>{{ phoneNo }}</h1>
  <button @click="findId">Find ID</button>
  <br />
  <br />
  <h1>{{ userEmail }}</h1>
</template>

<script>
  import http from "@/utils/http";

  export default {
  name: "SsauctionFindId",

  data() {
    return {
      phoneNo: "",
      userEmail: "",
      returnValue: {},
    };
  },

  mounted() {},

  methods: {
    findId: function () {
      console.log("FindId start");
      console.log(this.phoneNo);
      console.log("start getting");
      http.get("/users/findId/" + this.phoneNo).then(({ data }) => {
        console.log(data.userPhoneNo);
        console.log(data.userEmail);
        this.returnValue = data;
        console.log(this.returnValue);
        if (this.returnValue !== "") {
          alert("아이디(이메일 주소)는 다음과 같습니다.\n" + data.userEmail);
        } else {
          alert("해당 유저 정보가 없습니다.");
        }
      });
    },
  },
};
</script>