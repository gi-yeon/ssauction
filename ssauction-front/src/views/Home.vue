<template>
  <div class="container" style="height: 100%">
    <div id="hotdeal-container">
      <div class="wave1">
        <span>H</span>
        <span>O</span>
        <span>T</span>
        <span>&nbsp;</span>
        <span>D</span>
        <span>E</span>
        <span>A</span>
        <span>L</span>
        <span>S</span>
      </div>
    </div>

    <div class="row" id="comming-soon-container">
      <div class="my-3" style="height: 20%">
        <!-- <div class="wave2">
          <span>C</span>
          <span>O</span>
          <span>M</span>
          <span>I</span>
          <span>N</span>
          <span>G</span>
          <span>&nbsp;</span>
          <span>S</span>
          <span>O</span>
          <span>O</span>
          <span>N</span>
        </div> -->
        <input
          class="searchInput"
          type="string"
          v-model="houseSearchWord"
          placeholder="경매 제목으로 검색하세요"
          @keyup.enter="getHouses(0, houseSearchWord, 5, 0)"
        />
        <button class="searchBtn" @click="getHouses(0, houseSearchWord, 5, 0)">
          검색
        </button>
      </div>

      <div style="height: 60%">
        <div class="card">
          <div class="card-body">
            <div class="row">
              <main-houses
                v-for="(hot, index) in hotDeals"
                :key="index"
                :hot="hot"
                @click="toggleDetail(hot)"
              />
            </div>
          </div>
          <!-- <nav aria-label="Page navigation example">
            <ul class="pagination">
              <li class="page-item">
                <a class="page-link" href="#">Previous</a>
              </li>
              <li class="page-item"><a class="page-link" href="#">1</a></li>
              <li class="page-item"><a class="page-link" href="#">2</a></li>
              <li class="page-item"><a class="page-link" href="#">3</a></li>
              <li class="page-item"><a class="page-link" href="#">Next</a></li>
            </ul>
          </nav> -->
          <div class="example-pagination-block">
            <el-pagination
              layout="prev, pager, next"
              :current-page="houseCurrentPage"
              :total="totalPages"
              @current-change="
                getHouses(houseCurrentPage, houseSearchWord, 5, 0)
              "
              @prev-click="
                getHouses(houseCurrentPage - 1, houseSearchWord, 5, 0)
              "
              @next-click="
                getHouses(houseCurrentPage + 1, houseSearchWord, 5, 0)
              "
            />
          </div>
        </div>
      </div>
    </div>

    <Teleport to="body">
      <div
        v-if="showModal"
        class="detail-modal"
        style="text-align: center; background-color: white"
      >
        <div class="row">
          <h1>{{ houseToDetail.houseTitle }}</h1>
          <b-button
            style="color: red; background-color: white"
            variant="link"
            @click="addCarrotZzim"
            v-if="!isZzimCarrot && !isOwn"
            ><b-icon icon="heart"></b-icon
          ></b-button>
        </div>
        <div class="row">
          <div class="col detail-image">
            <Carousel :images="houseToDetail.itemImgList" />
          </div>
          <div class="col detail-info">
            <div>경매 정보 : {{ houseToDetail.house.houseDesc }}</div>
            <div>
              경매 시작 시간 :
              {{ getAuctionStartTime(houseToDetail.house.houseAucTime) }}
            </div>
            <div>물품명 : {{ houseToDetail.item.itemName }}</div>
            <div>물품 설명 : {{ houseToDetail.item.itemDesc }}</div>
            <div>모델명 : {{ houseToDetail.item.itemModelNo }}</div>
            <div>품질 : {{ houseToDetail.item.itemQuality }}</div>
            <div>시작가 : {{ houseToDetail.item.itemStartPrice }}</div>
          </div>
        </div>
        <div class="row"><button @click="joinSession">입장</button></div>
        <div class="row">
          <button @click="toggleDetail(houseToDetail)">닫기</button>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import MainHouses from "@/components/MainHouses.vue";
import axios from "@/utils/axios.js";
import Carousel from "@/components/CarouselComp.vue";

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
    Carousel,
    MainHouses,
  },

  MainHousesdata() {
    return {
      ...useUser(),
    };
  },
  data() {
    return {
      store: null,
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
    this.store = useStore();
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
          this.hotDeals = response.data;
          this.hotDeals.splice(4);
          // 페이지네이션을 위한 코드

          // this.totalElements = response.data.list.totalElements;
          // this.totalPages = response.data.list.totalPages;
          // this.numberOfElements = response.data.list.numberOfElements;
          // this.isPagingFirst = response.data.list.first;
          // this.isPagingLast = response.data.list.last;
          // this.isPagingEmpty = response.data.list.empty;
          console.log(this.hotDeals);
        });
    },
    setHouseSearchOption(option) {
      this.houseSearchOption = option;
    },
    toggleDetail(house) {
      console.log("toggleDetail");
      console.log(house);
      this.showModal = !this.showModal;

      this.houseToDetail = house;
      this.$store.dispatch(
        "session/setSessionId",
        this.houseToDetail.house.houseNo
      );
      const isHost =
        this.userNo == this.houseToDetail.item.sellerNo ? "true" : "false";
      this.$store.dispatch("session/setIsHost", isHost);
      this.$store.dispatch(
        "session/setSessionTitle",
        this.houseToDetail.house.houseTitle
      );
      this.$store.dispatch(
        "session/setStartPrice",
        this.houseToDetail.item.itemStartPrice
      );
      console.log("sessionStoreTest");
      console.log(
        `sessionId : ${this.store.state.session.sessionId}, isHost : ${this.store.state.session.isHost}, sessionTitle : ${this.store.state.session.sessionTitle}, startPrice : ${this.store.state.session.startPrice}`
      );
    },
    getAuctionStartTime(timestamp) {
      let datetest = new Date(timestamp);
      console.log(datetest);
      let [date, time] = timestamp.split("T");
      time = time.split(".")[0];
      return `${date} ${time}`;
    },
    joinSession() {
      this.$store.dispatch("session/setIsInSession", true);
      console.log(this.store.state.session.isInSession);
      this.$router.push({
        name: "Sessions",
      });
    },
  },
};
</script>
<style>
@keyframes anime_textup {
  0% {
    top: 0;
  }
  20% {
    top: -0.2rem;
  }
  40% {
    top: 0;
  }
  60% {
    top: 0;
  }
  80% {
    top: 0;
  }
  100% {
    top: 0;
  }
}

.wave1 span {
  font-size: 35px;
  color: rgb(255, 99, 99);
  position: relative;
  animation: anime_textup 1.5s infinite;
}

.wave1 span:nth-of-type(1) {
  animation-delay: 0.1s;
}

.wave1 span:nth-of-type(2) {
  animation-delay: 0.2s;
}

.wave1 span:nth-of-type(3) {
  animation-delay: 0.3s;
}

.wave1 span:nth-of-type(4) {
  animation-delay: 0.4s;
}

.wave1 span:nth-of-type(5) {
  animation-delay: 0.5s;
}
.wave1 span:nth-of-type(6) {
  animation-delay: 0.6s;
}
.wave1 span:nth-of-type(7) {
  animation-delay: 0.7s;
}
.wave1 span:nth-of-type(8) {
  animation-delay: 0.8s;
}
.wave1 span:nth-of-type(9) {
  animation-delay: 0.9s;
}
.wave2 span {
  font-size: 35px;
  color: rgb(40, 40, 200);
  position: relative;
  animation: anime_textup 1.5s infinite;
}

.wave2 span:nth-of-type(1) {
  animation-delay: 0.1s;
}

.wave2 span:nth-of-type(2) {
  animation-delay: 0.2s;
}

.wave2 span:nth-of-type(3) {
  animation-delay: 0.3s;
}

.wave2 span:nth-of-type(4) {
  animation-delay: 0.4s;
}

.wave2 span:nth-of-type(5) {
  animation-delay: 0.5s;
}
.wave2 span:nth-of-type(6) {
  animation-delay: 0.6s;
}
.wave2 span:nth-of-type(7) {
  animation-delay: 0.7s;
}
.wave2 span:nth-of-type(8) {
  animation-delay: 0.8s;
}
.wave2 span:nth-of-type(9) {
  animation-delay: 0.9s;
}
.wave2 span:nth-of-type(10) {
  animation-delay: 1s;
}
.wave1 span:nth-of-type(11) {
  animation-delay: 1.1s;
}

#hotdeal-container {
  margin: 10px;
  overflow-x: auto;
  white-space: nowrap;
  /* height: 25%; */
  border: 1px solid pink;
  border-width: medium;
  border-radius: 5px;
}
#comming-soon-container {
  margin: 10px;
  border: 1px solid skyblue;
  border-width: medium;
  border-radius: 5px;
  /* height: 70%; */
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
.searchInput {
  margin-left: 5px;
  margin-right: 5px;
  height: 40px;
  width: 400px;
  border: solid;
  border-width: thin;
  border-color: rgb(153, 197, 255);
  border-radius: 5px;
  background-color: white;
}
.searchBtn {
  margin: 10px;
  border-color: skyblue;
  background-color: rgb(153, 197, 255);
  border-radius: 5px;
}
</style>
