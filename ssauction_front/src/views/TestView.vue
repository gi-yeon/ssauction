<template>
  <div class="container-fluid mt-4">
    <div class="row align-items-center">
      <div class="col-lg-4 col-sm-8">
        <temp></temp>
      </div>
    </div>
  </div>
</template>

<script>
import Temp from "./components/TestComponents.vue";
export default {
  name: "TestView",
  components: {
    Temp
  },
};
</script>
