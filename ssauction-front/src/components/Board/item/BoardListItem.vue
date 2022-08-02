<template>
  <tr class="up">
    <td>{{boardNo}}</td>
    <td>{{boardTitle}}</td>
    <td>{{userNo}}</td>
    <td>{{boardRegDate}}</td>
  </tr>
</template>

<script>
export default {
  name: "BoardListItem",
  props: {
    boardNo: Number,
    boardTitle: String,
    boardContent: String,
    boardRegDate: String,
    boardReply: Number,
    boardType: String,
    userNo: Number,
  }
}
</script>