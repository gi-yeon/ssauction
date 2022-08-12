<template>
  <el-table :data="BoardListItem" stripe style="width: 100%">
    <el-table-column prop="t"></el-table-column>
    <el-table-column prop="date" label="Date" width="180" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="address" label="Address" />
  </el-table>

  <div class="table">
    <h1>BoardList</h1>
    <div class="register">
      <div>
        <select v-model="state.searchType">
          <option disabled selected value="">-선택-</option>
          <option value="title">제목</option>
          <option value="userNickname">작성자</option>
        </select>
        <input type="text" name="search" id="search" v-model="state.search" />
        <button @click="getArticles(0)">검색</button>
      </div>
      <button v-show="state.isUser" @click="mvBoardRegister">글쓰기</button>
    </div>
    <table class="boardtable">
      <colgroup>
        <col style="width: 10%" />
        <col style="width: 50%" />
        <col style="width: 20%" />
        <col style="width: 20%" />
      </colgroup>
      <thead>
        <tr>
          <th>글번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성일</th>
        </tr>
      </thead>
      <tbody>
        <board-list-item
          v-for="(article, index) in state.articles"
          :key="index"
          v-bind="article"
        ></board-list-item>
      </tbody>
    </table>
    <div class="btnGroup">
      <button @click="getArticles(state.startPage - 10)">〈〈</button>
      <span v-for="(p, index) in state.pages" :key="index">
        <button @click="getArticles(p - 1)">
          {{ p }}
        </button>
      </span>
      <button @click="getArticles(state.totalPages + 10)">〉〉</button>
    </div>
  </div>
</template>

<script>
import BoardListItem from "@/components/Board/item/BoardListItem";
import axios from "@/utils/axios";
import { useRouter } from "vue-router";
import { reactive } from "vue";
// npm install universal-cookie
import { useStore } from "vuex";

export default {
  name: "BoardList",
  components: {
    BoardListItem,
  },
  setup() {
    const router = useRouter();
    const store = useStore();

    const state = reactive({
      startPage: Number,
      endPage: Number,
      articles: [],
      currentPage: Number,
      totalPages: Number,
      numberOfElements: Number,
      pages: [],
      first: Boolean,
      last: Boolean,
      searchType: "",
      search: "",
      isUser: Boolean,
    });

    const getArticles = (p) => {
      getPage(p, 10, state.searchType, state.search);
    };

    const getPage = (p, s, searchT, search) => {
      if (p < 0) {
        alert("첫 페이지 리스트 입니다");
        return;
      }
      if (p >= state.totalPages) {
        alert("마지막 페이지 리스트 입니다");
        return;
      }
      console.log(store.state.board.boardType);
      axios
        .get("board", {
          params: {
            page: p,
            size: s,
            boardType: store.state.board.boardType,
            searchType: searchT,
            search: search,
          },
        })
        .then(({ data }) => {
          state.currentPage = data.list.number;
          state.totalPages = data.list.totalPages;
          state.numberOfElements = data.list.numberOfElements;
          state.startPage = Math.floor((1 + state.currentPage) / 10) * 10 + 1;
          state.endPage =
            Math.floor((1 + state.currentPage) / 10) * 10 + 10 >
            state.totalPages
              ? state.totalPages
              : Math.floor((1 + state.currentPage) / 10) * 10 + 10;

          state.pages.length = 0;
          for (let i = state.startPage; i <= state.endPage; i++) {
            state.pages.push(i);
          }
          state.articles = data.list.content;
        });
    };

    const mvBoardRegister = () => {
      router.push({ path: "/board/regist" });
    };

    getPage(0, 10);
    state.searchType = "";

    if (store.getters["user/userNo"] != null) state.isUser = true;
    else state.isUser = false;

    return {
      state,
      getArticles,
      mvBoardRegister,
    };
  },
};
</script>