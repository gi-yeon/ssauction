<template>
  <div id="main-container" class="container">
    <join-session
      v-if="!session"
      @joinSession="joinSession"
      @switchModal="switchModal"
    />
    <div class="row" id="session" v-if="session">
      <div id="session-header">
        <div class="col">
          <h1 id="session-title">{{ mySessionId }}</h1>
        </div>
        <div class="col"></div>
      </div>
      <div class="row">
        <div class="col-9">
          <div class="row">
            <div id="video-container">
              <participant-video
                id="publisher"
                :stream-manager="publisher"
                @click="updateMainVideoStreamManager(publisher)"
                style="display: inline-block"
              />
              <participant-video
                v-for="sub in subscribers"
                :key="sub.stream.connection.connectionId"
                :stream-manager="sub"
                @click="updateMainVideoStreamManager(sub)"
              />
            </div>
          </div>
          <div class="row main-video">
            <div class="col-md-9" id="main-video">
              <main-video :stream-manager="mainStreamManager" />
            </div>
          </div>
          <div class="row">
            <in-session-panel
              :isVideoOn="this.isVideoOn"
              :isMicOn="this.isMicOn"
              @leaveSession="this.leaveSession"
            />
          </div>
        </div>
        <div class="col-md-3" id="timer-bid-chat">
          <div class="row" id="timer">
            <session-timer ref="timer"></session-timer>
            <div><button @click="openBid = !openBid">입찰하기</button></div>
          </div>
          <div class="row">최고 입찰자 : {{ currentBidder }}</div>
          <div class="row">최고 금액 : {{ currentPrice }}</div>
          <div class="row" id="chat-history">
            <chat-message
              v-for="(m, index) in messageHistory"
              :key="index"
              :sender="m.sender"
              :message="m.message"
            />
          </div>
          <div id="chat-control-panel"></div>
          <div class="input-group" id="chat-input">
            <textarea
              v-model="message"
              class="form-control"
              type="text"
              v-on:keyup.enter="submitMessage"
            />
            <button
              class="btn btn-outline-secondary"
              type="button"
              @click="submitMessage"
            >
              전송
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- modal 모달 -->
    <Teleport to="body">
      <div v-if="openBid" class="bid-modal">
        <div class="row modal-title"><h1>경고</h1></div>
        <div class="row modal-warn">
          <h3>입찰을 하게 되면 되돌릴 수 없습니다.</h3>
        </div>
        <div class="row">
          <div class="col">
            <button
              class="btn btn-primary"
              @click="priceToBid = Number(priceToBid) + 1000 + ''"
            >
              1,000
            </button>
          </div>
          <div class="col">
            <button
              class="btn btn-warning"
              @click="priceToBid = Number(priceToBid) + 5000 + ''"
            >
              5,000
            </button>
          </div>
          <div class="col">
            <button
              class="btn btn-success"
              @click="priceToBid = Number(priceToBid) + 10000 + ''"
            >
              10,000
            </button>
          </div>
          <div class="col">
            <button class="btn btn-danger" @click="resetPriceToBid">
              reset
            </button>
          </div>
          <div class="col-4">
            <div class="form-check form-check-inline">
              <input
                class="form-check-input"
                type="checkbox"
                id="manualInput"
                v-model="isManual"
              />
              <label class="form-check-label" for="manualInput"
                >직접 입력하기</label
              >
            </div>
          </div>
        </div>
        <div class="row">
          <input type="text" v-model="priceToBid" :disabled="!isManual" />
        </div>
        <div class="row">
          {{ priceToKor }}
        </div>
        <div class="row">
          <div class="col">
            <button @click="makeBid">입찰</button>
          </div>
          <div class="col"><button @click="closeModal">닫기</button></div>
        </div>
      </div>
    </Teleport>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import MainVideo from "@/components/Session/MainVideo.vue";
import ParticipantVideo from "@/components/Session/ParticipantVideo.vue";
import ChatMessage from "@/components/Session/ChatMessage.vue";
import InSessionPanel from "@/components/Session/InSessionPanel.vue";
import JoinSession from "@/components/Session/JoinSession.vue";
import SessionTimer from "@/components/Session/SessionTimer.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "SessionsView",
  components: {
    MainVideo,
    ParticipantVideo,
    ChatMessage,
    InSessionPanel,
    JoinSession,
    SessionTimer,
  },
  mounted() {
    this.mySessionId = this.$route.params.houseNo;
  },

  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      mySessionId: null,
      myUserName: null,
      mode: "basic",
      message: null,
      messageHistory: [],
      now: null,
      createNewSession: false,

      // 마이크, 카메라 설정
      isVideoOn: true,
      isMicOn: true,
      openBid: false,
      isManual: false,

      top3List: [],
      currentBidder: null,
      currentPrice: "0",
      priceToBid: "0",
    };
  },

  computed: {
    priceToKor() {
      let hanA = new Array(
        "",
        "일",
        "이",
        "삼",
        "사",
        "오",
        "육",
        "칠",
        "팔",
        "구",
        "십"
      );
      let danA = new Array(
        "",
        "십",
        "백",
        "천",
        "",
        "십",
        "백",
        "천",
        "",
        "십",
        "백",
        "천",
        "",
        "십",
        "백",
        "천"
      );
      let num = this.priceToBid + "";
      let result = "";
      for (let i = 0; i < num.length; i++) {
        let str = "";
        let han = hanA[num.charAt(num.length - (i + 1))];
        if (han != "") str += han + danA[i];
        if (i == 4) str += "만";
        if (i == 8) str += "억";
        if (i == 12) str += "조";
        result = str + result;
      }
      return result + "원";
    },
  },
  methods: {
    closeModal() {
      this.openBid = false;
      this.resetPriceToBid();
    },
    resetPriceToBid() {
      this.top3List.length == 0
        ? (this.priceToBid = "0")
        : (this.priceToBid = this.top3List[0].priceToBid);
    },
    switchModal() {
      this.openBid = !this.openBid;
    },
    joinSession(userName) {
      this.myUserName = userName;
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber); // 해당 스트림을 subscribe하고 video player를 삽입한다.
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // 채팅 메시지 신호 수신
      this.session.on(`signal:${this.mySessionId}/message`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.appendMessage(event);
      });

      // 경매 시작 신호 수신
      this.session.on(`signal:${this.mySessionId}/startAuction`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.resetTimer();
        this.initTimer();
      });

      // 입찰 신호 수신
      this.session.on(`signal:${this.mySessionId}/bid`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.updateBid(event);
      });

      // 경매 종료 신호 수신
      this.session.on(`signal:${this.mySessionId}/endAuction`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.endAuction(event);
      });

      // 경매 결과 신호 수신
      this.session.on(`signal:${this.mySessionId}/result`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.showResult(event);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken().then((token) => {
        console.log(`received token : ${token}`);
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher("publisher", {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher); // publisher 객체를 publish하고 video player를 삽입힌다.
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    shareMonitor() {
      let monitorPublisher = this.OV.initPublisher("main-video", {
        videoSource: "screen",
      });
      monitorPublisher.once("accessAllowed", () => {
        publisher.stream
          .getMediaStream()
          .getVideoTracks()[0]
          .addEventListener("ended", () => {
            console.log('User pressed the "Stop sharing" button');
          });
        sessionScreen.publish(publisher);
      });

      publisher.once("accessDenied", () => {
        console.warn("ScreenShare: Access Denied");
      });
    },

    leaveSession() {
      this.closeModal();
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      this.messageHistory = [];

      this.currentBidder = null;
      this.currentPrice = "0";
      this.priceToBid = "0";

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    // getToken(mySessionId) {
    getToken() {
      // return this.createSession(mySessionId).then((sessionId) =>
      //   this.createToken(sessionId)
      // );
      return new Promise((resolve, reject) => {
        axios
          .post(
            "/sessions/getToken",
            JSON.stringify({
              sessionName: this.mySessionId,
              loggedUser: this.myUserName,
              createNewSession: this.createNewSession,
            })
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },

    submitMessage() {
      this.session
        .signal({
          data: JSON.stringify({
            sender: this.myUserName,
            message: this.message,
          }),
          to: [],
          type: this.mySessionId + "/message",
        })
        .then(() => {
          console.log();
        });
      this.message = null;
    },

    appendMessage(event) {
      const message = JSON.parse(event.data);
      this.messageHistory.push(message);
    },

    // (판매자 전용) 경매 시작 메시지를 보낸다.
    startAuction(event) {},

    // 타이머 카운트다운을 시작한다.
    initTimer() {},

    // 타이머 시간을 30초로 초기화하고 시작한다.
    resetTimer() {},
    makeBid() {
      // 입찰 검증
      // if (
      //   this.top3List.length != 0 &&
      //   this.top3List[0].sender == this.myUserName
      // ) {
      //   console.log("현재 최고가로 입찰하셨습니다.");
      //   return;
      // }

      if (
        this.top3List.length != 0 &&
        Number(this.top3List[0].priceToBid) >= Number(this.priceToBid)
      ) {
        console.log("현재 최고가보다 높은 값을 입력해야 합니다.");
        return;
      }

      axios
        .post(
          "/sessions/bid",
          JSON.stringify({
            bidder: this.myUserName,
            priceToBid: this.priceToBid,
            sessionName: this.mySessionId,
          })
        )
        .then(() => {
          this.session
            .signal({
              data: JSON.stringify({
                sender: this.myUserName,
                priceToBid: this.priceToBid,
              }),
              to: [],
              type: this.mySessionId + "/bid",
            })
            .then((data) => {
              console.log("after bid");
              console.log(data);
            });
          this.message = null;
          this.openBid = false;
        })
        .catch(() => {
          console.log("입찰에 실패했습니다.");
        });
    },
    // 타이머를 30초로 초기화하고 금액을 전달받은 금액으로 업데이트한다.
    // 만약 전달받은 금액이 이전 금액보다 작거나 같으면 업데이트하지 않는다.
    updateBid(event) {
      this.$refs.timer.timerReset();
      this.$refs.timer.timerStart();
      const data = JSON.parse(event.data);
      this.currentPrice = data.priceToBid;
      this.priceToBid = data.priceToBid;
      this.currentBidder = data.sender;
      this.top3List.push(data);
      this.top3List.sort((a, b) => {
        return Number(b.priceToBid) - Number(a.priceToBid);
      });
      if (this.top3List.length > 3) {
        this.top3List.splice(3, 1);
      }
      console.log("updateBid");
      console.log(this.top3List);
    },

    // 타이머를 종료시킨다.
    // (판매자 전용) 경매 결과를 서버에 POST한다.
    // (판매자 전용) 모두에게 경매 결과가 담긴 신호를 전달한다.
    endAuction(event) {},

    turnOnVideo() {
      this.session.publish(this.publisher);
    },

    turnOffVideo() {
      this.session.unpublish(this.publisher);
    },

    turnOnMic() {},
    turnOffMic() {},
  },
};
</script>
<style scoped>
#buttonLeaveSession {
  float: right;
  margin-top: 20px;
}
#video-container {
  overflow-x: auto;
  white-space: nowrap;
  height: auto;
}
#chat-history {
  height: 80%;
  background-color: white;
  overflow-y: auto;
}
#chat-input {
  background-color: black;
}
.bid-modal {
  position: fixed;
  z-index: 999;
  top: 20%;
  left: 50%;
  margin-left: -150px;
  background-color: white;
  width: auto;
}
.form-check {
  margin-top: 0.5rem;
  margin-right: 1.5rem;
  font-size: 1.3rem;
  width: auto;
}
.form-check-input {
  margin-top: 0;
  margin-right: 0.3rem;
  height: 1.8rem;
}
</style>
