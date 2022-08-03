import { USER } from "../mutation-types";
import axios from "axios";
import router from "@/router";
import VueCookies from "vue-cookies";


const state = {
  //로그인 한 유저의 정보
  loginUser: {
    userNo: "",
    userNickname: "",
    userGrade: ""
  },
  //로그인 여부
  isLogin: false,
  //access token
  accessToken: "",
  //refresh token
  refreshtoken: "",
};

const getters = {
  userNickname: (state) => {
    return `${state.loginUser.nickname}`;
  },
  userNo: (state) => {
    return `${state.loginUser.userNo}`;
  },
  userGrade: (state) => {
    return `${state.loginUser.grade}`;
  },

  // userInfo: (state) => {
  //   return `'닉네임' : ${state.nickname}, '유저번호' : ${state.userNo}, '등급' : ${state.grade}`;
  // },
};

const actions = {
 
};

const mutations = {

};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
