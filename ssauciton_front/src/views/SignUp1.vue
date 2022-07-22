<template>
  <div class="bg-white">
    <div class="container top-0 position-sticky z-index-sticky">
      <div class="row">
        <div class="col-12">
          <navbar
            isBlur="blur my-3 py-2 mt-4 start-0 end-0 mx-4 shadow blur border-radius-lg"
            btnBackground="bg-gradient-success"
            v-bind:darkMode="true"
          />
        </div>
      </div>
    </div>
    <main class="mt-0 main-content">
      <section>
        <div class="page-header min-vh-100">
          <div class="container">
            <div class="row">
              <div
                class="col-6 d-lg-flex d-none h-100 my-auto pe-0 ps-0 position-absolute top-0 start-0 text-center justify-content-center flex-column"
              >
                <div
                  class="page-header align-items-start min-vh-100"
                  :style="{
                    backgroundImage:
                      'url(' +
                      require('@/assets/img/illustrations/bg.png') +
                      ')',
                  }"
                ></div>
              </div>
              <div
                class="col-xl-4 col-lg-5 col-md-7 d-flex flex-column ms-auto me-auto ms-lg-auto me-lg-5"
              >
                <div class="card card-plain">
                  <div class="pb-0 card-header bg-transparent mb-4">
                    <h4 class="font-weight-bolder">회원 가입</h4>
                    <p class="mb-0">
                    </p>
                  </div>
                  <div class="card-body">
                    <form role="form">
                      <div class="mb-3">
                        <material-input
                          id="user_nickname"
                          type="text"
                          label="닉네임"
                          name="user_name"
                          size="lg"
                        />
                      </div>
                      <div class="mb-3">
                        <material-input
                          id="user_email"
                          type="email"
                          label="이메일"
                          name="user_email"
                          size="lg"
                        />
                      </div>
                      <div class="mb-3">
                        <material-input
                          id="user_phone_no"
                          type="text"
                          label="전화번호 : 숫자만 입력해주세요!"
                          name="user_phone_no"
                          size="lg"
                        />
                      </div>
                      <div class="mb-3">
                        <material-input
                          id="user_pwd"
                          type="password"
                          label="비밀번호"
                          name="user_pwd1"
                          size="lg"
                        />
                      </div>
                      <div class="mb-3">
                        <material-input
                          id="user_pwd2"
                          type="password"
                          label="비밀번호 확인"
                          name="user_pwd2"
                          size="lg"
                        />
                      </div>
                      <p v-if="user_pwd1 == user_pwd2">비밀번호가 일치합니다.</p>
                      <p v-else>비밀번호가 일치하지 않습니다.</p>

                      <material-checkbox
                        id="flexCheckDefault"
                        class="font-weight-light"
                        checked
                      >
                      <a
                          href="../../../pages/privacy.html"
                          class="text-dark font-weight-bolder"
                          >이용약관 </a
                        >및 개인정보 수집 및 이용에 동의합니다.
                      </material-checkbox>
                      <div class="text-center">
                        <material-button
                          class="mt-4"
                          variant="gradient"
                          color="success"
                          fullWidth
                          size="lg"
                          >회원 가입</material-button
                        >
                      </div>
                    </form>
                  </div>
                  <div class="px-1 pt-0 text-center card-footer px-lg-2">
                    <p class="mx-auto mb-4 text-sm">
                      이미 게정이 있으신가요?
                      <router-link
                        :to="{ name: 'SignIn' }"
                        class="text-success text-gradient font-weight-bold"
                        >로그인</router-link
                      >
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script>
import Navbar from "@/examples/PageLayout/Navbar.vue";
import MaterialInput from "@/components/MaterialInput.vue";
import MaterialCheckbox from "@/components/MaterialCheckbox.vue";
import MaterialButton from "@/components/MaterialButton.vue";
const body = document.getElementsByTagName("body")[0];
import { mapMutations } from "vuex";

export default {
  name: "sign-up",
  components: {
    Navbar,
    MaterialInput,
    MaterialCheckbox,
    MaterialButton,
  },
  beforeMount() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
    body.classList.remove("bg-gray-100");
  },
  beforeUnmount() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
    body.classList.add("bg-gray-100");
  },
  methods: {
    ...mapMutations(["toggleEveryDisplay", "toggleHideConfig"]),
  },
  data(){
    return{
      user_pwd: ""
    }
  }
};
</script>
