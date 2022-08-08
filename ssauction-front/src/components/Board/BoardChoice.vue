<template>
  <div class="board">
    <div class="btn">
      <div class="choice">
        <span>공지사항</span>  <span>자유 게시판</span>
      </div>

    </div>
    <div class="line"></div>
  </div>
</template>

<script>
export default {
  name: "BoardChoice"
}
</script>

<style>
.btn {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>