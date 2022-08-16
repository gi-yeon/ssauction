<template>
  <div>
    <br />
    <br />
    <h2>계정 정보 설정</h2>
    <br />
    <br />
    <!-- 로그인 상태에서만 볼 수 있다 -->
    <div v-show="isLogin">
      <!-- 이메일 -->
      <label for="userEmail" class="profileLabel"
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
      <label for="userNickname" class="profileLabel"
        ><button class="labelBtn" disabled>닉네임</button></label
      >
      <input
        class="nicknameInput"
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
      <label for="userPhoneNo" class="profileLabel"
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
      <label for="userDesc" class="profileLabel"
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
      <label for="userComment" class="profileLabel"
        ><button class="labelBtn" disabled>상태 메시지</button></label
      >
      <input
        class="profileInput"
        type="text"
        name="userComment"
        id="userComment"
        v-model="userComment"
      />
      <br />
      <br />

      <button @click="putUserInfo" width="500px" class="btn_update">
        수정
      </button>
      <button @click="toPwdUpdate" width="500px" class="btn_update">
        비밀번호 변경
      </button>
      <br />
      <router-link to="/deleteUser" class="toDeleteUser">회원탈퇴</router-link>
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
      originNickname: "",
      originPhoneNo: "",
      originDesc: "",
      originComment: "",
      originImg: "",
      userEmail: "",
      userNickname: "",
      userGrade: 0,
      userPhoneNo: "",
      userNo: 0,
      userDesc: "",
      userComment: "",
      isLogin: false,
      userImgUri: "",
      nicknameChecked: false,
      phoneChecked: false,
    };
  },

  mounted() {
    this.userNickname = this.$store.getters["user/userNickname"];
    this.originNickname = this.$store.getters["user/userNickname"];
    this.userGrade = this.$store.getters["user/userGrade"];
    this.userNo = this.$store.getters["user/userNo"];
    this.isLogin = this.$store.getters["user/isLogin"];
    this.getUserInfo();
  },

  methods: {
    toPwdUpdate() {
      this.$router.push("/pwdUpdate");
    },
    toDeleteUser() {
      this.$router.push("/deleteUser");
    },

    putUserInfo() {
      //닉네임 변경되었을 경우 (중복체크 미완료시)
      if (this.originNickname != this.userNickname && !this.nicknameChecked) {
        alert("닉네임 중복체크를 진행해주세요.");
      } else if (this.originPhoneNo != this.userPhoneNo && !this.phoneChecked) {
        alert("휴대폰 인증을 진행해주세요.");
      }
      //변동사항 있을 시
      else if (
        (this.originNickname != this.userNickname && this.nicknameChecked) ||
        (this.originPhoneNo != this.userPhoneNo && this.phoneChecked) ||
        this.originDesc != this.userDesc ||
        this.originComment != this.userComment
      ) {
        const obj = {
          userNickname: this.userNickname,
          userPhoneNo: this.userPhoneNo,
          userDesc: this.userDesc,
          userComment: this.userComment,
        };

        axios
          .put("/users/profile/name/" + this.userNo, JSON.stringify(obj))
          .then(() => {
            this.$store.dispatch("user/getUpdateInfo", this.userNickname);
            alert("회원 정보가 변경되었습니다.");
          })
          .catch(({ data }) => {
            console.log(data);
          });
      }
      //이미지 변경되었을 경우
      if (this.originImg != this.userImgUri) {
        const obj = {
          imgName: this.imgName,
          imgUri: this.imgUri,
        };

        axios
          .put("/users/profile/img/" + this.userNo, JSON.stringify(obj))
          .then(() => {
            alert("회원 정보가 변경되었습니다.");
            this.$router.push("/");
          })
          .catch(({ data }) => {
            console.log(data);
          });
      }
      //바뀐 것이 없다면
      else if (
        this.originNickname == this.userNickname &&
        this.originPhoneNo == this.userPhoneNo &&
        this.originDesc == this.userDesc &&
        this.originComment == this.userComment
      ) {
        alert("변동사항이 없습니다.");
      }
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
        this.originPhoneNo = data.userPhoneNo;
        this.userDesc = data.userDesc;
        this.originDesc = data.userDesc;
        this.userComment = data.userComment;
        this.originComment = data.userComment;
        this.userImgUri = data.userImgUri;
        this.originImg = data.userImgUri;
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

<style>
.profileInput {
  margin-left: 10px;
  margin-right: 10px;
  height: 50px;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  background-color: white;
}
.nicknameInput {
  margin-left: 10px;
  margin-right: 10px;
  height: 50;
  width: 100 !important;
  border: solid;
  border-width: medium;
  border-color: grey;
  border-radius: 5px;
  background-color: white;
}
.labelBtn {
  height: 45px;
  border-radius: 4px;
}
.btn_update {
  margin: 10px;
  width: 200px;
  height: 50px;
  border: 0;
  background-color: rgb(255, 211, 182);
  border-radius: 20px;
  color: rgb(94, 94, 94);
  text-align: center;
}
.profileLabel {
  width: 100px;
}
</style>;
