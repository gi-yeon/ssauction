<template>
  <div>
    <br />
    <div>
      <br />
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">UserProfile</h5>
          <div class="row">
            <div class="col-sm-4">
              <div class="card">
                <div class="card-body">
                  <div>
                    <div>유저사진</div>
                    <img
                      v-if="hasImg"
                      v-bind:src="'data:image/png;base64,' + userMainImg"
                    />
                    <el-upload
                      class="avatar-uploader"
                      action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                      :show-file-list="false"
                      :on-success="handleAvatarSuccess"
                      :before-upload="beforeAvatarUpload"
                    >
                      <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                      <el-icon v-else class="avatar-uploader-icon"
                        ><Plus
                      /></el-icon>
                    </el-upload>
                    <div class="row" style="height: 320px">
                      <div class="text-start preview">
                        <input
                          type="file"
                          ref="userImage"
                          @change="imageUpload"
                          style="margin-bottom: 0.5rem"
                        />
                        <user-image-preview
                          v-for="(file, index) in userImage"
                          :key="file.lastModified"
                          :previewfile="file"
                          :index="index"
                          @delete-img="deleteImg"
                          style="margin-bottom: 0.5rem; margin-left: 0.5rem"
                        />
                      </div>
                    </div>
                  </div>
                  <button @click="updateImg">이미지 수정</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/utils/axios.js";
import UserImagePreview from "@/components/UserImagePreview.vue";
export default {
  name: "SsauctionProfileUpdate",
  components: { UserImagePreview },
  data() {
    return {
      userNo: 0,
      userDesc: "",
      userComment: "",
      userMainImg: [],
      sellItem: [],
      buyItem: [],
      hasImg: false,
      userImage: [],
    };
  },

  mounted() {
    this.userNo = this.$store.getters["user/userNo"];
    console.log(this.userNo);
    this.getUserInfo();
  },

  methods: {
    getUserInfo: async function () {
      await axios
        .get("/users/profile/" + this.userNo)
        .then(({ data }) => {
          console.log(data);
          this.userDesc = data.infoDto.userDesc;
          this.userComment = data.infoDto.userComment;
          this.hasImg = data.userMainImg === null;
          if (this.hasImg) this.userMainImg = data.userMainImg;
        })
        .catch(({ data }) => {
          console.log(data);
        });
    },
    updateImg: async function () {
      console.log(this.userNo);
      let formData = new FormData();
      for (let img of this.userImage) {
        formData.append("file", img);
      }
      await axios
        .put("/users/profile/img/" + this.userNo, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then(({ data }) => {
          console.log(data);
          alert("사진 수정 완료");
        });
    },
    imageUpload() {
      // $refs를 통해 DOM에 있는 input의 files에 직접 접근하면 FileList 객체가 반환된다.
      // FileList 객체는 read only이므로 다루기 어렵다.
      // 아래의 코드를 통해 this.userImages 배열에 files에 있는 File 객체를 그대로 복사한다.
      this.userImage=[];
      Array.prototype.push.apply(this.userImage, this.$refs.userImage.files);
      console.log(this.userImage);
      this.$refs.userImage.value = "";
    },
    deleteImg(index) {
      this.userImage.splice(index, 1);
      console.log(this.$refs.userImage);
    },
  },
};
</script>

<style></style>
