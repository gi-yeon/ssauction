<template>
  <div class="board">
    <div class="btn">
      <div class="choice">
        <span @click="setNotice">공지사항</span> | 
        <span @click="setFree">자유 게시판</span>
      </div>

    </div>
    <div class="line"></div>
  </div>
</template>

<script>

export default {
  name: "BoardChoice",
  methods: {
    setNotice() {
      this.$store.dispatch("board/setBoardType", 1);
      // console.log(this.$store.state.board.boardType);
      this.$router.go();
    },

    setFree() {
      this.$store.dispatch("board/setBoardType", 0);
      // console.log(this.$store.state.board.boardType);
      this.$router.go();
    }
  }
}
</script>

<style>
.btn {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>