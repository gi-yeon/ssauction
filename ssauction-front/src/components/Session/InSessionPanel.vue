<template>
  <div class="row">
    <div class="col"></div>
    <div class="col">
      <button class="video" @click="$emit('toggleVideo')">
        <div v-if="isVideoOn">비디오 끄기</div>
        <div v-else>비디오 켜기</div>
      </button>
    </div>
    <div class="col">
      <button class="audio" @click="$emit('toggleAudio')">
        <div v-if="isAudioOn">마이크 끄기</div>
        <div v-else>마이크 켜기</div>
      </button>
    </div>
    <div class="col">
      <button class="screen" @click="$emit('toggleScreen')">
        <div v-if="isMonitor">화면 공유 중지</div>
        <div v-else>화면 공유</div>
      </button>
    </div>
    <div class="col" v-if="isHost">
      <button class="startAuction" @click="$emit('startAuction')">
        <div v-if="!isFinished">경매 시작</div>
        <div v-else>경매 종료</div>
      </button>
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
    isAudioOn: Boolean,
    isMonitor: Boolean,
    isHost: Boolean,
    isFinished: Boolean,
  },
};
</script>

<style></style>
