<template>
  <div class="card mt-4">
    <div class="card-header pb-0 p-3">
      <div class="row">
        <div class="col-6 text-end">
          <material-button @click="clickBtn" color="dark" variant="gradient">
            Add New Card
          </material-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MaterialButton from "@/components/MaterialButton.vue";
import axios from "axios";
import img1 from "@/assets/img/logos/mastercard.png";
import img2 from "@/assets/img/logos/visa.png";

export default {
  components: {
    MaterialButton,
  },
  setup() {
    const clickBtn = () => {
      //alert("test");
      console.log("post");
      axios.post("test", null, { params :{Test: "test"} }).then(({ data }) => {
        console.log(data);
      });
    };
    return {
      clickBtn,
      img1,
      img2,
    };
  },
};
</script>
