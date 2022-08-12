<template>
  <div>
    <input type="text" name="title" id="title" v-model="article.title" />
    <div></div>
    <textarea
      name="content"
      id="content"
      cols="30"
      rows="10"
      v-model="article.content"
    >
    </textarea>

    <div>
      <button @click="registerArticle">글작성</button>
      <router-link to="/board">
        <button>목록</button>
      </router-link>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import axios from "@/utils/axios";
import { useRouter } from "vue-router";
// npm install universal-cookie
import { useStore } from "vuex";
import { mapState } from "vuex";

export default {
  name: "BoardRegister",
  computed: {
    ...mapState(["user"]),
  },
  setup() {
    const router = useRouter();
    const store = useStore();

    const article = reactive({
      title: "",
      content: "",
    });

    const registerArticle = () => {
      //   console.log(store.state.user.accessToken);
      //   console.log(cookies.get("accessToken"));
      let params = {
        boardTitle: article.title,
        boardContent: article.content,
        boardType: store.state.board.boardType,
        userNo: store.getters["user/userNo"],
        userNickname: store.getters["user/userNickname"],
      };
      console.log(params);
      axios
        .post("board", JSON.stringify(params))
        .then(() => {})
        .catch(() => {
          alert("글 등록에 실패하였습니다.");
        });

      router.push({ path: "/board" });
    };

    return {
      article,
      registerArticle,
    };
  },
};
</script>
