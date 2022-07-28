import { USER } from "../mutation-types";

const state = {
  nickname: "",
  userNo: 0,
  grade: -1,
};

const getters = {
  userInfo: (state) => {
    return `'닉네임' : ${state.nickname}, '유저번호' : ${state.userNo}, '등급' : ${state.grade}`;
  },
};

const actions = {
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
};

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};
