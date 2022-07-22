<template>
  <div class="container-fluid">
    <div
      class="page-header min-height-300 border-radius-xl mt-4"
      style="
        background-image: url('https://images.unsplash.com/photo-1531512073830-ba890ca4eba2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1920&q=80');
      "
    >
      <span class="mask bg-gradient-success opacity-6">
        <material-button @click="clickBtn" color="dark" variant="gradient">
          Update Profile
        </material-button>
      </span>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import MaterialButton from "@/components/MaterialButton.vue";
import setNavPills from "@/assets/js/nav-pills.js";
import setTooltip from "@/assets/js/tooltip.js";

import axios from "axios";

export default {
  name: "profile-overview",
  data() {
    return {
      showMenu: false,
      isOverview: true,
    };
  },
  components() {
    MaterialButton;
  },
  mounted() {
    this.$store.state.isAbsolute = true;
    setNavPills();
    setTooltip();
    console.log("profile post");
    this.$router.push({ name: "ProfileUpdate" });

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
  methods: {
    clickBtn() {
      if (this.isOverview) {
        this.isOverview = false;
        this.$router.push({ name: "ProfileUpdate" });
      } else {
        this.isOverview = true;
        this.$router.push({ name: "ProfileView" });
      }
    },
  },
};
</script>
