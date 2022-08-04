import { USER } from "../mutation-types";

const state = {
  nickname: "닉네임",
  userNo: 0,
  grade: 0,
  isLogin:false
};

const getters = {
  userNickname: (state) => {
    return `${state.nickname}`;
  },
  userNo: (state) => {
    return `${state.userNo}`;
  },
  userGrade: (state) => {
    return `${state.grade}`;
  },
  userLogin:(state)=>{
    return `${state.isLogin}`;
  }
  // userInfo: (state) => {
  //   return `'닉네임' : ${state.nickname}, '유저번호' : ${state.userNo}, '등급' : ${state.grade}`;
  // },
};

const actions = {
//   nickname: "",
//   userNo: 0,
//   grade: -1,
// };

// const getters = {
//   userInfo: (state) => {
//     return `'닉네임' : ${state.nickname}, '유저번호' : ${state.userNo}, '등급' : ${state.grade}`;
//   },
// };

// const actions = {

  getNickname({ commit }, value) {
    commit(USER.SET_NICKNAME, value);
  },
  getGrade({ commit }, value) {
    commit(USER.SET_GRADE, value);
  },
  getUserNo({ commit }, value) {
    commit(USER.SET_USERNO, value);
  },
  getIsLogin({commit},value){
    commit(USER.SET_ISLOGIN,value);
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
  [USER.SET_ISLOGIN](state,value){
    state.isLogin=value;
  }
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
