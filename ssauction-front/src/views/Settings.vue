<template>
  <div>
    <br />
    <br />
    <h1>{{ userNickname }}님의 프로필</h1>
    <br />
    <br />
    <!-- 로그인 상태에서만 볼 수 있다 -->
    <div v-show="isLogin">
      <!-- 이메일 -->
      <label for="userEmail"
        ><button class="labelBtn" disabled>이메일</button></label
      >
      <input
        class="profileInput"
        type="text"
        name="userEmail"
        id="userEmail"
        v-model="userEmail"
        disabled
      />
      <br />
      <br />
      <!-- 닉네임 -->
      <label for="userNickname"
        ><button class="labelBtn" disabled>닉네임</button></label
      >
      <input
        class="profileInput"
        type="text"
        name="userNickname"
        id="userNickname"
        v-model="userNickname"
      /><button class="joinCheckBtn" @click="nicknameCheck">
        닉네임 중복 체크
      </button>
      <br />
      <br />

      <!-- 휴대폰 번호 -->
      <label for="userPhoneNo"
        ><button class="labelBtn" disabled>전화번호</button></label
      >
      <input
        class="profileInput"
        type="text"
        name="userPhoneNo"
        id="userPhoneNo"
        v-model="userPhoneNo"
      />
      <br />
      <br />
      <!-- 회원 Desc -->
      <label for="userDesc"
        ><button class="labelBtn" disabled>자기소개</button></label
      >
      <input
        class="profileInput"
        type="text"
        name="userDesc"
        id="userDesc"
        v-model="userDesc"
      />
      <br />
      <br />
      <!-- 회원 comment -->
      <label for="userComment"
        ><button class="labelBtn" disabled>상태 메시지</button></label
      >
      <input
        class="profileInput"
        type="text"
        name="userComment"
        id="userComment"
        v-model="userComment"
        disabled
      />
      <br />
      <br />
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import { mapState } from "vuex";

export default {
  name: "SsauctionSettings",
  compputed: {
    ...mapState(["user"]),
  },
  data() {
    return {
      userEmail: "",
      userNickname: "",
      userGrade: 0,
      userPhoneNo: "",
      userNo: 0,
      userDesc: "",
      userComment: "",
      isLogin: false,
    };
  },

  mounted() {
    this.userNickname = this.$store.getters["user/userNickname"];
    this.userGrade = this.$store.getters["user/userGrade"];
    this.userNo = this.$store.getters["user/userNo"];
    this.isLogin = this.$store.getters["user/isLogin"];
    this.getUserInfo();
  },

  methods: {
    putUserInfo: async function () {
      const obj = {
        userDesc: this.desc,
        userComment: this.comment,
      };
      await axios
        .put("/users/profile/info/" + this.userNo, JSON.stringify(obj))
        .then(({ data }) => {
          console.log(data);
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    putUserImg: async function () {
      const obj = {
        imgName: this.imgName,
        imgUri: this.imgUri,
      };

      await axios
        .put("/users/profile/img/" + this.userNo, JSON.stringify(obj))
        .then(({ data }) => {
          console.log(data);
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    putUserName: async function () {
      const obj = {
        userNickname: this.nickname,
      };

      await axios
        .put("/users/profile/name/" + this.userNo, JSON.stringify(obj))
        .then(({ data }) => {
          this.$store.dispatch("user/getNickname", data.userNickname);
          console.log(data);
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },

    //처음에 기존 프로필 정보 가져옴
    getUserInfo: async function () {
      await axios.get("/users/profile/" + this.userNo).then(({ data }) => {
        console.log(data);

        this.userEmail = data.userEmail;
        this.userPhoneNo = data.userPhoneNo;
        this.userDesc = data.userDesc;
        this.userComment = data.userComment;
        this.userImgUri = data.userImgUri;
      });
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
<style></style>;
