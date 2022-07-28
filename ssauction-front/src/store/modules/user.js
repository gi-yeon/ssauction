import { USER } from "../mutation-types";

const state = {
  email: "ssafy@ssafy.com",
  phoneNo: "010-0000-0000",
  nickname: "닉네임",
  userNo: 0,
  grade: 0,
};

const getters = {
  userEmail: (state) => {
    return `${state.email}`;
  },
  userPhoneNo: (state) => {
    return `${state.phoneNo}`;
  },
  userNickname: (state) => {
    return `${state.nickname}`;
  },
  userNo: (state) => {
    return `${state.userNo}`;
  },
  userGrade: (state) => {
    return `${state.grade}`;
  },
  // userInfo: (state) => {
  //   return `'닉네임' : ${state.nickname}, '유저번호' : ${state.userNo}, '등급' : ${state.grade}`;
  // },
};

const actions = {
  getEmail({ commit }, value) {
    commit(USER.SET_EMAIL, value);
  },
  getPhoneNo({ commit }, value) {
    commit(USER.SET_PHONENO, value);
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
  [USER.SET_EMAIL](state, value) {
    state.email = value;
  },
  [USER.SET_PHONENO](state, value) {
    state.phoneNo = value;
  },
  [USER.SET_NICKNAME](state, value) {
    state.nickname = value;
  },
  [USER.SET_GRADE](state, value) {
    state.grade = value;
  },
  [USER.SET_USERNO](state, value) {
    state.userNo = value;
  },
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
