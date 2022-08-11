<template>
  <div id="main-container" class="container">
    <join-session
      v-if="!sessionCamera"
      :isAudioOn="isAudioOn"
      :isVideoOn="isVideoOn"
      @joinSession="joinSession"
      @switchModal="switchModal"
      @toggleVideo="toggleVideo"
      @toggleAudio="toggleAudio"
    />
    <div class="row" id="session" v-if="sessionCamera">
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
            <div class="col-md-9">
              <main-video
                :stream-manager="mainStreamManager"
                :key="mainStreamManager"
              />
            </div>
          </div>
          <div class="row">
            <in-session-panel
              :isVideoOn="isVideoOn"
              :isAudioOn="isAudioOn"
              :isMonitor="isMonitor"
              :isHost="isHost"
              :isFinished="isFinished"
              @leaveSession="leaveSession"
              @toggleVideo="toggleVideo"
              @toggleAudio="toggleAudio"
              @toggleScreen="toggleScreen"
              @startAuction="startAuctionFromHost"
            />
          </div>
        </div>
        <div class="col-md-3" id="timer-bid-chat">
          <div class="row" id="timer">
            <session-timer
              ref="timer"
              :remainingTime="remainingTime"
              :isHost="isHost"
              @finishAuction="finishAuctionFromHost"
              @tickTimer="tickTimer"
            ></session-timer>
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
    <!-- 입찰 모달 modal  -->
    <Teleport to="body">
      <div v-if="openBid && !isFinished" class="bid-modal">
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
    <Teleport to="body">
      <div v-if="isFinished" class="result-modal">
        <div class="row">
          <div>{{ this.currentPrice }}</div>
        </div>
        <div class="row">
          <h1>{{ this.currentBidder }}에게 최종 낙찰 되었습니다!</h1>
        </div>
        <div class="row"><button @click="leaveSession">OK</button></div>
      </div>
    </Teleport>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import ParticipantVideo from "@/components/Session/ParticipantVideo.vue";
import ChatMessage from "@/components/Session/ChatMessage.vue";
import InSessionPanel from "@/components/Session/InSessionPanel.vue";
import JoinSession from "@/components/Session/JoinSession.vue";
import SessionTimer from "@/components/Session/SessionTimer.vue";
import MainVideo from "@/components/Session/MainVideo.vue";
import VueCookies from "vue-cookies";

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
    const URLParams = new URL(window.location).searchParams;
    console.log(`isHost: ${URLParams.get("isHost")}`);
    this.isHost = URLParams.get("isHost") === "true" ? true : false;
  },

  data() {
    return {
      OVCamera: undefined,
      OVScreen: undefined,
      sessionCamera: undefined,
      sessionScreen: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      publisherMonitor: undefined,
      subscribers: [],

      mySessionId: null,
      myUserName: null,
      mode: "basic",
      message: null,
      messageHistory: [],
      now: null,
      isHost: false,

      // 마이크, 카메라 설정
      isVideoOn: true,
      isAudioOn: true,
      isMonitor: false,
      openBid: false,
      isManual: false,

      top3List: [],
      currentBidder: null,
      currentPrice: "0",
      priceToBid: "0",
      isFinished: false,
      hostId: null,
      remainingTime: 0,
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
    // 호스트 사이드 로직
    startAuctionFromHost() {
      // 다른 클라이언트들에게 startAuction 메시지를 보내 isFinished를 false로 만든다.
      this.sessionCamera
        .signal({
          data: JSON.stringify({
            sender: this.myUserName,
          }),
          to: [],
          type: this.mySessionId + "/startAuction",
        })
        .then(() => {
          console.log();
        });
      this.$refs.timer.resetTimer();
      this.$refs.timer.initTimer();
    },

    finishAuctionFromHost() {
      // 다른 클라이언트들에게 endAuction 메시지를 보내 isFinished를 true로 만든다.
      this.sessionCamera.signal({
        data: JSON.stringify({
          sender: this.myUserName,
        }),
        to: [],
        type: this.mySessionId + "/endAuction",
      });

      // 서버에 경매 종료 신호를 보낸다.
      axios
        .post(
          "/sessions/bid",
          JSON.stringify({ sessionName: this.mySessionId })
        )
        .then((data) => {
          console.log(data);
        });
    },
    // 경매 시작
    // 클라이언트 사이드 로직
    startAuctionInClient() {
      this.isFinished = false;
    },
    // 경매 종료
    // 클라이언트 사이드 로직
    finishAuctionInClient() {
      this.isFinished = true;
    },

    // 호스트 로직
    // 다른 클라이언트에 현재 기준 시간(호스트 시간)을 전송한다.
    tickTimer(currentTime) {
      this.sessionCamera
        .signal({
          data: JSON.stringify({
            sender: this.myUserName,
            currentTime: currentTime,
          }),
          to: [],
          type: this.mySessionId + "/tick",
        })
        .then(() => {
          console.log();
        });
    },

    // 세션 연결
    joinSession(userName) {
      this.myUserName = userName;
      // --- Get an OpenVidu object ---
      this.OVCamera = new OpenVidu();
      this.OVScreen = new OpenVidu();

      // --- Init a session ---
      this.sessionCamera = this.OVCamera.initSession();
      this.sessionScreen = this.OVScreen.initSession();

      // --- Specify the actions when events take place in the session ---
      // 세션과 연결이 되면 아래의 코드가 실행된다.
      this.sessionCamera.on("connectionCreated", (event) => {
        console.log("connection created");
        console.log(event.connection.data);
        let data = JSON.parse(event.connection.data);
        if (data.isHost) {
          this.hostId = event.connection.connectionId;
        }
      });

      // On every new Stream received...
      this.sessionCamera.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "CAMERA") {
          const subscriber = this.sessionCamera.subscribe(stream);
          console.log("Camera Stream created");
          console.log(stream);
          this.subscribers.push(subscriber); // 해당 스트림을 subscribe하고 video player를 삽입한다.
          // 해당 스트림이 호스트 스트림일 경우 초기 공유화면으로 설정한다.
          if (this.hostId == subscriber.stream.connection.connectionId) {
            this.updateMainVideoStreamManager(subscriber);
          }
        }
      });

      this.sessionScreen.on("streamCreated", ({ stream }) => {
        if (stream.typeOfVideo == "SCREEN") {
          const subscriber = this.sessionScreen.subscribe(stream);
          console.log("Screen Stream created");
          console.log(stream);
          console.log("change main video stream to shared screen");
          this.updateMainVideoStreamManager(subscriber);
        }
      });
      // On every Stream destroyed...
      this.sessionCamera.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      this.sessionScreen.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      this.sessionCamera.on("publisherStartSpeaking", (event) => {
        console.log(
          "User " + event.connection.connectionId + " start speaking"
        );
      });

      this.sessionCamera.on("publisherStopSpeaking", (event) => {
        console.log("User " + event.connection.connectionId + " stop speaking");
      });

      // On every asynchronous exception...
      this.sessionCamera.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      this.sessionScreen.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // 채팅 메시지 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/message`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.appendMessage(event);
      });

      // 경매 시작 신호 수신
      this.sessionCamera.on(
        `signal:${this.mySessionId}/startAuction`,
        (event) => {
          console.log(event.data); // Message
          console.log(event.from); // Connection object of the sender
          console.log(event.type); // The type of message ("my-chat")
          this.startAuctionInClient();
        }
      );
      // 현재 호스트 시간 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/tick`, (event) => {
        console.log(`currentTime: ${event.data}`);
        this.remainingTime = JSON.parse(event.data).currentTime;
      });
      // 입찰 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/bid`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.updateBid(event);
      });

      // 경매 종료 신호 수신
      this.sessionCamera.on(
        `signal:${this.mySessionId}/endAuction`,
        (event) => {
          console.log(event.data); // Message
          console.log(event.from); // Connection object of the sender
          console.log(event.type); // The type of message ("my-chat")
          this.finishAuctionInClient();
        }
      );

      // 경매 결과 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/result`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.showResult(event);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken().then((token) => {
        console.log(`received token for Camera: ${token}`);
        this.sessionCamera
          .connect(token)
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OVCamera.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            if (this.isHost) {
              this.mainStreamManager = publisher;
            }
            this.publisher = publisher;

            // --- Publish your stream ---
            this.sessionCamera.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      this.getToken()
        .then((token) => {
          console.log(`received token for Screen: ${token}`);
          this.sessionScreen.connect(token);
        })
        .catch((error) => {
          console.log(
            "There was an error connecting to the session:",
            error.code,
            error.message
          );
        });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    // 백엔드를 통해 토큰 받기
    getToken() {
      return new Promise((resolve, reject) => {
        axios
          .post(
            "/sessions/getToken",
            JSON.stringify({
              sessionName: this.mySessionId,
              loggedUser: this.myUserName,
              isHost: this.isHost,
            })
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },

    // 채팅 보내기
    submitMessage() {
      this.sessionCamera
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

    // 채팅 받기
    appendMessage(event) {
      const message = JSON.parse(event.data);
      this.messageHistory.push(message);
    },

    // 입찰하기
    makeBid() {
      // 동일인 입찰 검증
      if (
        this.top3List.length != 0 &&
        this.top3List[0].sender == this.myUserName
      ) {
        alert("현재 최고가로 입찰하셨습니다.");
        return;
      }

      // 최고가 입찰 검증
      if (
        this.top3List.length != 0 &&
        Number(this.top3List[0].priceToBid) >= Number(this.priceToBid)
      ) {
        alert("현재 최고가보다 높은 값을 입력해야 합니다.");
        return;
      }

      axios
        .put(
          "/sessions/bid",
          JSON.stringify({
            bidder: this.myUserName,
            userNo: VueCookies.get("login.userNo"),
            priceToBid: this.priceToBid,
            sessionName: this.mySessionId,
          })
        )
        .then(() => {
          this.sessionCamera
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

    // 타이머를 설정 시간으로 초기화하고 금액을 전달받은 금액으로 업데이트한다.
    // 만약 전달받은 금액이 이전 금액보다 작거나 같으면 업데이트하지 않는다.
    updateBid(event) {
      if (this.isHost) {
        this.$refs.timer.timerReset();
        this.$refs.timer.timerStart();
      }
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
    },

    // 영상 on/off
    toggleVideo() {
      this.isVideoOn = !this.isVideoOn;
      if (this.publisher) {
        this.publisher.publishVideo(this.isVideoOn);
      }
    },

    // 마이크 on/off
    toggleAudio() {
      this.isAudioOn = !this.isAudioOn;
      if (this.publisher) {
        this.publisher.publishAudio(this.isAudioOn);
      }
    },

    // 화면공유 on/off
    toggleScreen() {
      if (!this.isMonitor) {
        let screen = this.OVScreen.initPublisher(undefined, {
          videoSource: "screen",
          publishAudio: false,
        });
        this.sessionScreen.publish(screen);
        console.log("update mainVideoStreamManager");
        this.publisherMonitor = screen;
        this.updateMainVideoStreamManager(screen);
      } else {
        this.sessionScreen.unpublish(this.publisherMonitor);
        this.mainStreamManager = this.publisher;
      }
      this.isMonitor = !this.isMonitor;
    },

    // 입찰 모달 닫기
    closeModal() {
      this.openBid = false;
      this.resetPriceToBid();
    },

    // 입찰 모달에 입력했던 입찰할 금액 초기화
    resetPriceToBid() {
      this.top3List.length == 0
        ? (this.priceToBid = "0")
        : (this.priceToBid = this.top3List[0].priceToBid);
    },

    // 입찰 모달 on/off
    switchModal() {
      this.openBid = !this.openBid;
    },

    leaveSession() {
      this.closeModal();
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.sessionCamera) this.sessionCamera.disconnect();

      this.sessionCamera = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OVCamera = undefined;
      this.messageHistory = [];

      this.currentBidder = null;
      this.currentPrice = "0";
      this.priceToBid = "0";
      this.isFinished = false;

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },
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
.result-modal {
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
