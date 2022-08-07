<template>
  <div class="row">
    <div class="col"></div>
    <div class="col">
      <button class="video" v-if="isVideoOn">비디오 끄기</button>
      <button class="video" v-else>비디오 켜기</button>
    </div>
    <div class="col">
      <button class="mic" v-if="isMicOn">마이크 끄기</button>
      <button class="mic" v-else>마이크 켜기</button>
    </div>
    <div class="col">
      <button class="monitor" v-if="isMonitorOn">화면 공유 끄기</button>
      <button class="monitor" v-else>화면 공유</button>
    </div>
    <div class="col">
      <input
        class="btn btn-large btn-danger"
        type="button"
        id="buttonLeaveSession"
        @click="$emit('leaveSession')"
        value="Leave session"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "InSessionPanel",
  data() {
    return {};
  },
  props: {
    isVideoOn: Boolean,
    isMicOn: Boolean,
    isMonitorOn: Boolean,
  },
};
</script>

<style></style>
