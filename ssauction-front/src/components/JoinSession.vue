<template>
  <div id="join">
    <!-- <div id="img-div"> -->
    <!-- <img src="resources/images/openvidu_grey_bg_transp_cropped.png" /> -->
    <!-- </div> -->
    <div id="join-dialog" class="jumbotron vertical-center">
      <h1>Join a video session</h1>
      <div class="form-group">
        <p>
          <label>Participant</label>
          <input
            v-model="myUserName"
            class="form-control"
            type="text"
            required
          />
        </p>
        <p>
          <label>Session</label>
          {{ $route.params.houseTitle }}
        </p>
        <p class="text-center">
          <button class="btn btn-lg btn-success" @click="$emit('joinSession')">
            Join!
          </button>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "JoinSession",
  props: {
    myUserName: String,
  },
};
</script>

<style></style>
