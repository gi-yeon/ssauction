import { createStore } from "vuex";
import createPersistedState from 'vuex-persistedstate';
import user from "./modules/user";
import board from "@/store/modules/board.js";

export default createStore({
  modules: { user, board },
  plugins: [
    createPersistedState({
      path: ['@/store/modules'],
    })
  ]
});
