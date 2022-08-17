<template>
  <div class="container">
    <div class="clipping-container">
      <div class="pages" :style="{ left: currentLeft }">
        <div v-for="index in images" :key="index">
          <div class="card page">
            <div class="card-body">
              <div>
                <img
                  v-bind:src="'data:image/png;base64,' + index.img"
                  @click="clickimg"
                />
                <br />
              </div>
            </div>
          </div>
          hello
        </div>
      </div>
    </div>
    <div class="dots">
      <span
        v-for="index in totalPage"
        :class="{ dot: true, 'dot-current': isCurrentPage(index) }"
        :key="index"
      >
      </span>
    </div>
    <div>
      <button @click="backwardPage">←</button>
      <button @click="forwardPage">→</button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
  name: "HelloWorld",
  props: { images: Array },
  data() {
    return {
      currentPage: 1,
      totalPage: 1,
      pageWidth: 200,
    };
  },
  methods: {
    forwardPage() {
      console.log(this.totalPage);
      if (this.currentPage === this.totalPage) {
        return;
      }
      this.currentPage += 1;
    },

    backwardPage() {
      if (this.currentPage === 1) {
        return;
      }
      this.currentPage -= 1;
    },

    pageToPosition(): number {
      return -this.pageWidth * (this.currentPage - 1);
    },

    isCurrentPage(page: number) {
      return this.currentPage === page;
    },
    clickimg() {
      console.log(this.images);
    },
  },
  watch: {
    images() {
      const vm = this;
      vm.totalPage =
        vm.images?.length == null ? vm.totalPage : vm.images?.length;
    },
  },
  computed: {
    currentLeft(): string {
      return String(this.pageToPosition()) + "px";
    },
  },
});
</script>

<style scoped>
.clipping-container {
  clip-path: inset(0);
  position: relative;
  height: 200px;
  width: 200px;
  display: inline-block;
}
.pages {
  display: flex;
  /* ここにtransitionをつけることでpagesのleftを変更した場合にアニメーションさせることができる */
  transition: left 0.5s ease;
  position: absolute;
  left: 0;
}
.page {
  width: 200px;
  height: 200px;
  background-color: aqua;
  border: 1px solid white;
  box-sizing: border-box;
}
.dots {
  width: 30px;
  display: flex;
  justify-content: space-between;
  margin: auto;
  margin-bottom: 16px;
}
.dot {
  height: 8px;
  width: 8px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
}
.dot-current {
  background-color: skyblue;
}
</style>
