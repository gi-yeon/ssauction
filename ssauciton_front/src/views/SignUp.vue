<template>
  <navbar btnBackground="bg-gradient-success" />
  <div
    class="page-header align-items-start min-vh-100"
    style="
      background-image: url('src\assets\img\illustrations\bg.png');
    "
  >
  <span class="mask bg-gradient-dark opacity-6"></span>
    <div class="container my-auto">
      <div class="row">
        <div class="col-lg-4 col-md-8 col-12 mx-auto">
          <div class="card z-index-0 fadeIn3 fadeInBottom">
            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
              <div
                class="bg-gradient-success shadow-success border-radius-lg py-3 pe-1"
              >
                <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">
                  회원가입
                </h4>
                <!-- <div class="row mt-3">
                  <div class="col-2 text-center ms-auto">
                    <a class="btn btn-link px-3" href="javascript:;">
                      <i class="fab fa-facebook text-white text-lg"></i>
                    </a>
                  </div>
                  <div class="col-2 text-center px-1">
                    <a class="btn btn-link px-3" href="javascript:;">
                      <i class="fab fa-github text-white text-lg"></i>
                    </a>
                  </div>
                  <div class="col-2 text-center me-auto">
                    <a class="btn btn-link px-3" href="javascript:;">
                      <i class="fab fa-google text-white text-lg"></i>
                    </a>
                  </div>
                </div> -->
              </div>
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
                          >회원가입</material-button
                        >
                      </div>
                    </form>
                  </div>
                  <div class="px-1 pt-0 text-center card-footer px-lg-2">
                    <p class="mx-auto mb-4 text-sm">
                      이미 계정이 있으신가요?
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
    <footer class="footer position-absolute bottom-2 py-2 w-100">
      <div class="container">
        <div class="row align-items-center justify-content-lg-between">
          <div class="col-12 col-md-6 my-auto">
            <!-- <div class="copyright text-center text-sm text-white text-lg-start">
              © {{ new Date().getFullYear() }}, made with
              <i class="fa fa-heart" aria-hidden="true"></i> by
              <a
                href="https://www.creative-tim.com"
                class="font-weight-bold text-white"
                target="_blank"
                >Creative Tim</a
              >
              for a better web.
            </div> -->
          </div>
          <div class="col-12 col-md-6">
            <ul
              class="nav nav-footer justify-content-center justify-content-lg-end"
            >
              <!-- <li class="nav-item">
                <a
                  href="https://www.creative-tim.com"
                  class="nav-link text-white"
                  target="_blank"
                  >Creative Tim</a
                >
              </li> -->
              <!-- <li class="nav-item">
                <a
                  href="https://www.creative-tim.com/presentation"
                  class="nav-link text-white"
                  target="_blank"
                  >About Us</a
                >
              </li> -->
              <!-- <li class="nav-item">
                <a
                  href="https://www.creative-tim.com/blog"
                  class="nav-link text-white"
                  target="_blank"
                  >Blog</a
                >
              </li> -->
              <!-- <li class="nav-item">
                <a
                  href="https://www.creative-tim.com/license"
                  class="nav-link pe-0 text-white"
                  target="_blank"
                  >License</a
                >
              </li> -->
            </ul>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
import Navbar from "@/examples/PageLayout/Navbar.vue";
import MaterialInput from "@/components/MaterialInput.vue";
import MaterialCheckbox from "@/components/MaterialCheckbox.vue";
import MaterialButton from "@/components/MaterialButton.vue";
import { mapMutations } from "vuex";

export default {
  name: "sign-in",
  components: {
    Navbar,
    MaterialInput,
    MaterialCheckbox,
    MaterialButton,
  },
  beforeMount() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
  },
  beforeUnmount() {
    this.toggleEveryDisplay();
    this.toggleHideConfig();
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
