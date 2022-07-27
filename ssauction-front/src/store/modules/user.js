import { USER } from "../mutation-types";
const state = {
  name: "",
  userNo: 0,
};
const getters = {
  userInfo: (state) => {
    return `'이름' : ${state.name}, '유저번호' : ${state.userNo}`;
  },
  userId: (state) => {
    return `'전화번호' : ${state.PhoneNo}, '유저아이디' : ${state.userId}`;
  },
};
const actions = {
  changeName({ commit }, value) {
    commit(USER.SET_NAME, value);
  },
};
const mutations = {
  [USER.SET_NAME](state, value) {
    state.name = value;
  },
};
export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};