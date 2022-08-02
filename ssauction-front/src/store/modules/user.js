import { USER } from "../mutation-types";
import axios from "axios";
import router from "@/router";

const state = {
  //로그인 한 유저의 정보
  loginUser: {
    userNo: "",
    userNickname: "",
    userGrade: ""
  },
  //로그인 여부
  isLogin: false,
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
  //로그인
  userLogin({ commit }, loginInfo) {
    axios.post("/users/login", JSON.stringify(loginInfo)).then((res) => {
      //success 메시지가 떴다면
      if (res.data.message === "success") {
        //헤더에 acessToken 넣어준다.
        axios.defaults.headers.common['Authorization'] = res.data.accessToken;
        console.log(res);
        commit('USER_LOGIN', res);
        alert("로그인 되었습니다!");
        router.push('/'); //홈으로 이동
      }
      //success 메시지가 뜨지 않았다면
      else {
        alert("아이디 또는 비밀번호가 일치하지 않습니다.");
        router.push({ name: 'userLogin' });
      }
    })
  },

  //로그아웃
  userLogout({ commit }) {
    axios.post("/users/logout").then((res) => {
      commit('USER_LOGOUT')
      console.log(res.data)
      //헤더 access token 없애준다.
      axios.defaults.headers.common['Authorization'] = null;
      alert("로그아웃되었습니다. 다음에 또 만나요 ^____^")
      router.push('/'); //홈으로 이동

    })

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
    state.token = payload.data.accessToken;
  },
  //로그아웃
  USER_LOGOUT(state) {
    state.isLogin = false;
    state.loginUser = {
      userNo: "",
      userNickname: "",
      userGrade: ""
    }
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
