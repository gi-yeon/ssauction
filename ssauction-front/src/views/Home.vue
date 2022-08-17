<template>
  <div class="container" style="height: 100%">
    <div id="hotdeal-container">
      <div>
        <h1>Hot Deals</h1>
      </div>
    </div>

    <div class="row" id="comming-soon-container">
      <div class="my-3" style="height: 20%">
        <h1>Comming Soon...</h1>
        <input
          type="string"
          v-model="houseSearchWord"
          placeholder="경매 제목으로 검색하세요"
          @keyup.enter="getHouses(0, houseSearchWord, 5, 0)"
        />
      </div>

      <div style="height: 60%">
        <main-houses
          v-for="(hot, index) in hotDeals"
          class="housecard"
          :key="index"
          :hot="hot"
          @click="toggleDetail(hot)"
        />
        <div class="example-pagination-block">
          <el-pagination
            layout="prev, pager, next"
            :current-page="houseCurrentPage"
            :total="totalPages"
            @current-change="getHouses(houseCurrentPage, houseSearchWord, 5, 0)"
            @prev-click="getHouses(houseCurrentPage - 1, houseSearchWord, 5, 0)"
            @next-click="getHouses(houseCurrentPage + 1, houseSearchWord, 5, 0)"
          />
        </div>
      </div>
    </div>
    <Teleport to="body">
      <div
        v-if="showModal"
        class="detail-modal"
        style="text-align: center; background-color: white"
      >
        <!-- <div class="row">
          <h1>{{ houseToDetail.houseTitle }}</h1>
          <button>찜하기</button>
        </div> -->
        <div class="row">
          <div class="col detail-image"></div>
          <div class="col detail-info">
            <div>경매 정보 : {{ houseToDetail.houseDesc }}</div>
            <div>
              경매 시작 시간 :
              {{ getAuctionStartTime(houseToDetail.houseAucTime) }}
            </div>
            <div>물품명 : {{ houseToDetail.item.itemName }}</div>
            <div>물품 설명 : {{ houseToDetail.item.itemDesc }}</div>
            <div>모델명 : {{ houseToDetail.item.itemModelNo }}</div>
            <div>품질 : {{ houseToDetail.item.itemQuality }}</div>
            <div>시작가 : {{ houseToDetail.item.itemStartPrice }}</div>
          </div>
        </div>
        <div class="row"><button @click="joinSession">입장</button></div>
        <div class="row"><button @click="toggleDetail">닫기</button></div>
      </div>
    </Teleport>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import MainHouses from "@/components/MainHouses.vue";
import axios from "@/utils/axios.js";
import { ArrowDown } from "@element-plus/icons-vue";

function useUser() {
  const store = useStore();
  const userInfo = computed(() => store.getters["user/userInfo"]);
  return {
    userInfo,
  };
}
export default {
  name: "SsauctionHome",
  components: {
    MainHouses,
    ArrowDown,
  },
  MainHousesdata() {
    return {
      ...useUser(),
    };
  },
  data() {
    return {
      hotDeals: null,
      houseSearchWord: null,
      houseCurrentPage: 1,
      userNo: null,
      showModal: false,
      houseToDetail: null,
      totalElements: null,
      totalPages: null,
      numberOfElements: null,
      isPagingFirst: false,
      isPagingLast: false,
      isPagingEmpty: false,
    };
  },
  mounted() {
    console.log("hotdeals");
    this.getHouses(0, "", 5, 0);
    this.userNo = this.$store.getters["user/userNo"];
  },
  methods: {
    getHouses(page, word, size, houseStatus) {
      if (word == null) {
        word = "";
      }

      axios
        .get(
          `/houses/searchAll?page=${page}&search=${word}&size=${size}&houseStatus=${houseStatus}`
        )
        .then((response) => {
          console.log(response.data);
          this.hotDeals = response.data.list.content;
          this.totalElements = response.data.list.totalElements;
          this.totalPages = response.data.list.totalPages;
          this.numberOfElements = response.data.list.numberOfElements;
          this.isPagingFirst = response.data.list.first;
          this.isPagingLast = response.data.list.last;
          this.isPagingEmpty = response.data.list.empty;
          console.log(this.hotDeals);
        });
    },
    setHouseSearchOption(option) {
      this.houseSearchOption = option;
    },
    toggleDetail(house) {
      console.log("toggleDetail");
      console.log(house);
      this.houseToDetail = house;
      this.showModal = !this.showModal;
    },
    getAuctionStartTime(timestamp) {
      let datetest = new Date(timestamp);
      console.log(datetest);
      let [date, time] = timestamp.split("T");
      time = time.split(".")[0];
      return `${date} ${time}`;
    },
    joinSession() {
      let isHost = this.userNo == "1" ? "true" : "false";
      let sessionId = this.houseToDetail.houseNo;
      let sessionTitle = this.houseToDetail.houseTitle;
      console.log(this.houseToDetail.houseSearchOptio);
      this.$router.push({
        name: "Sessions",
        params: {
          sessionId: sessionId,
          sessionTitle: sessionTitle,
          isHost: isHost,
        },
      });
    },
  },
};
</script>
<style>
#hotdeal-container {
  overflow-x: auto;
  white-space: nowrap;
  height: 25%;
  background-color: pink;
}
#comming-soon-container {
  background-color: skyblue;
  height: 70%;
}
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
.detail-modal {
  position: fixed;
  z-index: 999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  width: auto;
}
</style>
