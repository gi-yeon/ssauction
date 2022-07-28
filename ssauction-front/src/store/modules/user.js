import { USER } from "../mutation-types";
const state = {
  phoneNo: "",
  userId: "",
};
const getters = {
  userId: (state) => {
    return `'전화번호' : ${state.phoneNo}, '유저아이디' : ${state.userId}`;
  },
};
const actions = {
  getPhoneNo({ commit }, value) {
    commit(USER.SET_PHONENO, value);
  },
  getUserId({ commit }, value) {
    commit(USER.SET_USERID, value);
  },
};
const mutations = {
  [USER.SET_PHONENO](state, value) {
    state.phoneNo = value;
  },
  [USER.SET_USERID](state, value) {
    state.userId = value;
  },
};
export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations,
};