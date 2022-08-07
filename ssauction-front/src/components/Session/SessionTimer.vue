<template>
  <div>
    <h1>{{ timerStr }}</h1>
    <button @click="timerStart">시작</button>
    <button @click="timerReset">리셋</button>
    <button @click="timerPause">일시정지</button>
    <button @click="timerStop">정지</button>
  </div>
</template>

<script>
export default {
  name: "SessionTimer",
  data() {
    return {
      timer: null,
      startTime: 5,
      pauseTime: null,
      timeCounter: null,
      timerStr: null,
      isPaused: false,
    };
  },
  mounted() {
    this.timerStop(this.timer);
    this.timerStr = this.formatTime(this.startTime);
    this.timer = null;
  },
  methods: {
    timerStart() {
      if (this.timer !== null) {
        console.log("이미 카운트다운이 실행중입니다.");
        return;
      }
      console.log("start of timerStart()");

      if (this.isPaused) {
        this.timeCounter = this.pauseTime;
      } else {
        this.timeCounter = this.startTime;
      }

      this.timer = setInterval(() => {
        this.timeCounter--;
        console.log(`timeCounter: ${this.timeCounter}`);
        this.timerStr = this.formatTime(this.timeCounter);

        if (this.timeCounter <= 0) {
          console.log("카운트다운이 종료되었습니다.");
          this.timerPause(this.timer);
        }
      }, 1000);
    },
    timerPause() {
      clearInterval(this.timer);
      this.pauseTime = this.timeCounter;
      this.isPaused = true;
      this.timer = null;
    },
    timerStop() {
      if (this.timer !== null) {
        clearInterval(this.timer);
      }
      this.timeCounter = 0;
      this.timerStr = this.formatTime(this.timeCounter);
      this.isPaused = false;
      this.timer = null;
    },
    timerReset() {
      this.timeCounter = this.startTime;
      this.timerStr = this.formatTime(this.timeCounter);

      console.log(`timeCounter: ${this.timeCounter}`);
    },
    formatTime(timeInSeconds) {
      let time = timeInSeconds / 60;
      let minutes = parseInt(time);
      let seconds = parseInt(timeInSeconds % 60);
      return (
        minutes.toString().padStart(2, "0") +
        ":" +
        seconds.toString().padStart(2, "0")
      );
    },
  },
};
</script>

<style></style>
