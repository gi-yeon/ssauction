<template>
  <div class="card card-body mx-3 mx-md-4 mt-n6">
    <div class="row gx-4">
      <div class="col-auto">
        <div class="avatar avatar-xl position-relative">
          <img
            src="@/assets/img/bruce-mars.jpg"
            alt="profile_image"
            class="shadow-sm w-100 border-radius-lg"
          />
        </div>
      </div>
      <div class="col-auto my-auto">
        <div class="h-100">
          <input v-model="userNickname" placeholder="닉네임 수정" />
          <input v-model="userComment" placeholder="한 줄 소개 수정" />
        </div>
      </div>
    </div>
    <div class="row">
      <div class="mt-3 row">
        <div class="col-12 col-md-6 col-xl-4 position-relative">
          <div class="card card-plain h-100">
            <div class="p-3 pb-0 card-header">
              <h6 class="mb-0">Intro</h6>
            </div>
            <div class="p-3 card-body">
              <h6 class="text-xs text-uppercase text-body font-weight-bolder">
                E-mail
              </h6>
              <input v-model="userEmail" placeholder="이메일 수정" />
              <h6
                class="mt-4 text-xs text-uppercase text-body font-weight-bolder"
              >
                User Description
              </h6>
              <input v-model="userDesc" placeholder="자기소개 수정" />
            </div>
          </div>
          <hr class="vertical dark" />
        </div>
      </div>
    </div>
    <material-button @click="clickBtn" color="dark" variant="gradient">
      Update
    </material-button>
  </div>
</template>

<script>
import MaterialButton from "@/components/MaterialButton.vue";
import setNavPills from "@/assets/js/nav-pills.js";
import setTooltip from "@/assets/js/tooltip.js";

import axios from "axios";

export default {
  name: "profile-view",
  data() {
    return {
      showMenu: false,
    };
  },
  components: {
    MaterialButton,
  },
  mounted() {
    this.$store.state.isAbsolute = true;
    setNavPills();
    setTooltip();
  },
  beforeUnmount() {
    this.$store.state.isAbsolute = false;
  },
  methods: {
    clickBtn() {
      console.log("profile post");
      axios
        .post("update", null, {
          params: {
            id: "1",
            userNickname: this.userNickname,
            userEmail: this.userEmail,
            userComment:this.userComment,
            userDesc:this.userDesc,
          },
        })
        .then(({ data }) => {
          console.log(data);
          this.$router.push({ name: "ProfileView" });
          // this.userNickname = data.userNickname;
          // this.userEmail = data.userId;
          // this.userComment = data.userComment;
          // this.userDesc = data.userDesc;
        });
    },
  },
};
</script>
