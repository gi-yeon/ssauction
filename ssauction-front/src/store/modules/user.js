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
  accessToken: null,
  //refresh token
  refreshToken: null,
};

const getters = {
  userNickname: (state) => {
    return `${state.loginUser.userNickname}`;
  },
  userNo: (state) => {
    return `${state.loginUser.userNo}`;
  },
  userGrade: (state) => {
    return `${state.loginUser.userGrade}`;
  },
  accessToken: (state) => {
    return `${state.accessToken}`;
  },
  refreshToken: (state) => {
    return `${state.refreshToken}`;
  },
};

const actions = {
  //로그인
  userLogin({ commit }, loginInfo) {
    axios.post("/users/login", JSON.stringify(loginInfo)).then((res) => {
      //success 메시지가 떴다면
      if (res.data.message === "success") {
        console.log(res);
        commit('USER_LOGIN', res);
        commit('SAVE_LOGIN_INFO');
        alert("로그인 되었습니다!");
        router.push('/'); //홈으로 이동
      }
      //success 메시지가 뜨지 않았다면 (fail)
      else {
        alert("이메일 또는 비밀번호가 일치하지 않습니다.");
        router.push({ name: 'userLogin' });
      }
    })
  },

  //로그아웃
  userLogout({ commit }) {
    axios.post("/users/logout").then((res) => {
      commit('USER_LOGOUT')
      commit('RESET_LOGIN_INFO')
      console.log(res.data)

      alert("로그아웃되었습니다. 다음에 또 만나요 ^____^")
      router.push('/'); //홈으로 이동

    })

  },

  //로그인 정보 가져오기
  getLoginInfo({ commit }) {
    commit('GET_LOGIN_INFO')
  },

  //토큰 재발급
  refreshToken() {
    axios
      .post("/users/refresh", JSON.stringify(state.loginUser.userNo))
      .then((res) => {
        console.log(res);
        if (res.data.message === "success") {
          alert("refresh성공");
        }
      });
  },


  getNickname({ commit }, value) {
    commit(USER.SET_NICKNAME, value);
  },
  getGrade({ commit }, value) {
    commit(USER.SET_GRADE, value);
  },
  getUserNo({ commit }, value) {
    commit(USER.SET_USERNO, value);
  },
  getIsLogin({ commit }, value) {
    commit(USER.SET_ISLOGIN, value);
  }
};

const mutations = {

  [USER.SET_NICKNAME](state, value) {
    state.nickname = value;
  },
  [USER.SET_GRADE](state, value) {
    state.grade = value;
  },
  [USER.SET_USERNO](state, value) {
    state.userNo = value;
  },

  //로그인
  USER_LOGIN(state, payload) {
    state.isLogin = true;
    state.loginUser.userNo = payload.data.userNo;
    state.loginUser.userNickname = payload.data.userNickname;
    state.loginUser.userGrade = payload.data.userGrade;
  },
  //로그아웃
  USER_LOGOUT(state) {
    state.isLogin = false;
    state.accessToken = null;
    state.refreshToken = null;
    state.loginUser = {
      userNo: "",
      userNickname: "",
      userGrade: ""
    }
  },
  //쿠키에 로그인 정보 저장
  SAVE_LOGIN_INFO(state) {
    VueCookies.set("login.userNo", state.loginUser.userNo, '30m');
    VueCookies.set("login.userNickname", state.loginUser.userNickname, '30m');
    VueCookies.set("login.userGrade", state.loginUser.userGrade, '30m');
    VueCookies.set("isLogin", state.isLogin, '30m');
  },
  //쿠키에 있는 로그인 정보 읽어오기
  GET_LOGIN_INFO(state) {
    if (VueCookies.get("login.userNo") != null) {
      state.loginUser.userNo = VueCookies.get("login.userNo")
    }
    if (VueCookies.get("login.userNickname") != null) {
      state.loginUser.userNickname = VueCookies.get("login.userNickname")
    }
    if (VueCookies.get("login.userGrade") != null) {
      state.loginUser.userGrade = VueCookies.get("login.userGrade")
    }
    if (VueCookies.get("isLogin") != null) {
      state.isLogin = VueCookies.get("isLogin")
    }
  },

  //쿠키에 있는 로그인 정보 삭제
  RESET_LOGIN_INFO() {
    VueCookies.remove("login.userNo");
    VueCookies.remove("login.userNickname");
    VueCookies.remove("login.userGrade");
    VueCookies.remove("isLogin");
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
