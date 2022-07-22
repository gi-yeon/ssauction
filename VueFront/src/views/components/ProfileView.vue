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
          <h5 class="mb-1">{{ userNickname }}</h5>
          <p class="mb-0 font-weight-normal text-sm">{{ userComment }}</p>
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
              <p class="mb-0 font-weight-normal text-sm">{{ userEmail }}</p>
              <h6
                class="mt-4 text-xs text-uppercase text-body font-weight-bolder"
              >
                User Description
              </h6>
              <p class="mb-0 font-weight-normal text-sm">{{ userDesc }}</p>
            </div>
          </div>
          <hr class="vertical dark" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
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
  mounted() {
    this.$store.state.isAbsolute = true;
    setNavPills();
    setTooltip();
    console.log("profile post");
    axios.post("test", null, { params: { id: "1" } }).then(({ data }) => {
      console.log(data);
      this.userNickname = data.userNickname;
      this.userEmail = data.userId;
      this.userComment = data.userComment;
      this.userDesc = data.userDesc;
    });
  },
  beforeUnmount() {
    this.$store.state.isAbsolute = false;
  },
};
</script>
