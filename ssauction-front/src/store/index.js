import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import user from "./modules/user";
import board from "@/store/modules/board.js";

export default createStore({
  modules: { user, board },
  plugins: [
    // 새로고침해도 store 정보 초기화되지 않게 하기
    createPersistedState({
      path: ["@/store/modules"],
    }),
  ],
});
