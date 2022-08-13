<template>
  <div class="row">
    <div class="col">
      <button class="video" @click="$emit('toggleVideo')">
        <div v-if="isVideoOn">비디오 끄기</div>
        <div v-else>비디오 켜기</div>
      </button>
    </div>
    <div class="col">
      <el-button
        v-if="isAudioOn"
        class="audio"
        @click="$emit('toggleAudio')"
        type="success"
      >
        마이크 켜짐
      </el-button>
      <el-button
        v-else
        class="audio"
        @click="$emit('toggleAudio')"
        type="danger"
      >
        마이크 꺼짐
      </el-button>
    </div>

    <div class="col" v-if="isHost">
      <button class="freezeHouse" v-if="!isFreeze" @click="$emit('setFreeze')">
        방 얼리기
      </button>
      <button class="freezeHouse" v-else @click="$emit('undoFreeze')">
        얼리기 해제
      </button>
    </div>
    <div v-if="!isHost">
      <div class="col" v-if="isFreeze">음성, 채팅 제한</div>
      <div class="col" v-if="!isFreeze">음성, 채팅 가능</div>
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
    isFreeze: Boolean,
  },
};
</script>

<style></style>
