<template>
  <div class="board">
    <div class="btn">
      <div class="choice">
        <!-- <router-link :to="{ name: 'notice' }" 
          >공지사항</router-link
        >
        <router-link :to="{ name: 'boardList' }"
          >자유게시판</router-link
        > -->
        공지사항  자유 게시판
      </div>
      <div class="register">
        <button class="rgboardbtn">글쓰기</button>
      </div>
    </div>
    <div class="line"></div>
  </div>
</template>

<script>
</script>

<style>
.btn {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>