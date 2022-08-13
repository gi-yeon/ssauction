<template>
  <div id="session-container" class="container">
    <join-session
      v-if="!sessionCamera"
      :isAudioOn="isAudioOn"
      :isVideoOn="isVideoOn"
      :sessionName="sessionName"
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
      </div>
      <div class="row">
        <div class="col-md-9" id="session-main-container">
          <div class="row">
            <div id="session-video-container">
              <participant-video
                class="session-publisher float-left m-1"
                :stream-manager="publisher"
                :key="publisher"
                ref="publisher"
                @click="updateMainVideoStreamManager(publisher)"
                style="display: inline-block"
              />
              <participant-video
                class="session-participant"
                v-for="sub in subscribersCamera"
                :key="sub"
                :stream-manager="sub"
                ref="subscribersCamera"
                @click="updateMainVideoStreamManager(sub)"
              />
            </div>
          </div>
          <div class="row">
            <div class="session-main-video">
              <main-video
                :stream-manager="mainStreamManager"
                :key="mainStreamManager"
              />
            </div>
          </div>
        </div>
        <div class="col-md-3 session-side-container">
          <div class="row mb-3">
            <div class="row mb-3" id="timer">
              <session-timer
                ref="timer"
                :remainingTime="remainingTime"
                :isHost="isHost"
                @finishAuction="finishAuctionFromHost"
                @tickTimer="tickTimer"
              ></session-timer>
              <div>
                <button @click="openBid = !openBid" :disabled="isFinished">
                  입찰하기
                </button>
              </div>
            </div>
            <div class="row">최고 입찰자 : {{ currentBidder }}</div>
            <div class="row">최고 금액 : {{ currentPrice }}</div>
          </div>
          <div class="row" id="session-chat-panel">
            <div class="row mb-3" id="session-chat-history">
              <el-tabs type="border-card">
                <el-tab-pane label="채팅"
                  ><div id="chat-history">
                    <chat-message
                      v-for="(m, index) in messageHistory"
                      :key="index"
                      :sender="m.sender"
                      :message="m.message"
                    /></div
                ></el-tab-pane>
                <el-tab-pane label="참여자">
                  <div id="participant-list">
                    <div class="participant-name">{{ myUserName }} (나)</div>
                    <div v-for="sub in subscribersCamera" :key="sub + 'c'">
                      <div class="participant-name">{{ getClientId(sub) }}</div>
                      <div v-if="isHost">
                        <button
                          class="kickout"
                          @click="kickoutFromHost(sub.stream.connection)"
                        >
                          강퇴
                        </button>
                      </div>
                    </div>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </div>
            <div class="row mb-1" id="chat-control-panel">
              <el-select
                v-model="toWhisper"
                placeholder="메시지를 보낼 상대를 선택하세요"
                clearable
              >
                <el-option
                  v-for="sub in subscribersCamera"
                  :key="sub"
                  :label="getClientId(sub)"
                  :value="sub.stream.connection"
                />
              </el-select>
            </div>
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
        <footer>
          <in-session-panel
            :isVideoOn="isVideoOn"
            :isAudioOn="isAudioOn"
            :isHost="isHost"
            :isFreeze="isFreeze"
            :isFinished="isFinished"
            @leaveSession="leaveSession"
            @toggleVideo="toggleVideo"
            @toggleAudio="toggleAudio"
            @startAuction="startAuctionFromHost"
            @setFreeze="setFreezeFromHost"
            @undoFreeze="undoFreezeFromHost"
          />
        </footer>
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
            <button @click="makeBid" :disabled="isFinished">입찰</button>
          </div>
          <div class="col"><button @click="closeModal">닫기</button></div>
        </div>
      </div>
    </Teleport>
    <Teleport to="body">
      <div v-if="isFinished" class="result-modal">
        <div class="row">
          <h1>최종 가격 : {{ this.currentPrice }}</h1>
        </div>
        <div class="row">
          <h1 v-if="this.currentBidder">
            {{ this.currentBidder }}에게 최종 낙찰 되었습니다!
          </h1>
          <h1 v-else>유찰되었습니다.</h1>
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
import { mapState } from "vuex";

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
    const URLParams = new URL(window.location).searchParams;
    console.log(`isHost: ${URLParams.get("isHost")}`);
    this.mySessionId = URLParams.get("sessionId");
    this.isHost = URLParams.get("isHost") === "true" ? true : false;
    this.userNo = this.$store.getters["user/userNo"];
  },

  data() {
    return {
      OVCamera: undefined,
      // OVScreen: undefined,
      sessionCamera: undefined,
      // sessionScreen: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      publisherId: undefined,
      publisherMonitor: undefined,
      subscribersCamera: [],
      // subscribersScreen: [],

      cameraToken: null,
      // screenToken: null,
      mySessionId: null,
      sessionName: null,
      myUserName: null,
      userNo: null,
      mode: "basic",
      message: null,
      messageHistory: [],
      toWhisper: null,
      now: null,
      isHost: false,
      isFreeze: false,

      // 마이크, 카메라 설정
      isVideoOn: true,
      isAudioOn: true,
      // isMonitor: false,
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
    ...mapState(["user"]),

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
    setFreezeFromHost(connection) {
      let connections = [];
      if (connection) {
        connections.push(connection);
      }
      this.sessionCamera.signal({
        data: JSON.stringify({
          sender: "system :",
          message: "방장이 방을 얼렸습니다.",
        }),
        to: connections,
        type: this.mySessionId + "/setFreeze",
      });
    },

    undoFreezeFromHost() {
      this.sessionCamera.signal({
        data: JSON.stringify({
          sender: "system :",
          message: "음성 및 채팅 제한이 해제되었습니다.",
        }),
        to: [],
        type: this.mySessionId + "/undoFreeze",
      });
    },

    setFreezeInClient(data) {
      this.isFreeze = true;
      this.messageHistory.push(JSON.parse(data));
      if (this.publisher && !this.isHost) {
        this.publisher.publishAudio(false);
      }
    },

    undoFreezeInClient(data) {
      this.isFreeze = false;
      this.messageHistory.push(JSON.parse(data));
      if (this.publisher && !this.isHost) {
        this.publisher.publishAudio(this.isAudioOn);
      }
    },

    // 호스트 사이드 로직
    startAuctionFromHost() {
      // 다른 클라이언트들에게 startAuction 메시지를 보내 isFinished를 false로 만든다.
      this.sessionCamera
        .signal({
          data: JSON.stringify({
            sender: "system :",
            message: "경매가 시작되었습니다.",
          }),
          to: [],
          type: this.mySessionId + "/startAuction",
        })
        .then(() => {
          console.log();
        });
      this.$refs.timer.timerReset();
      this.$refs.timer.timerStart();
    },

    finishAuctionFromHost() {
      // 다른 클라이언트들에게 endAuction 메시지를 보내 isFinished를 true로 만든다.
      this.sessionCamera.signal({
        data: JSON.stringify({
          sender: "system :",
          message: "경매가 종료되었습니다.",
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
    startAuctionInClient(data) {
      this.isFinished = false;
      this.messageHistory.push(JSON.parse(data));
    },
    // 경매 종료
    // 클라이언트 사이드 로직
    finishAuctionInClient(data) {
      this.isFinished = true;
      this.messageHistory.push(JSON.parse(data));
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
      // this.OVScreen = new OpenVidu();

      // 음성 인식 관련 설정
      this.OVCamera.setAdvancedConfiguration({
        publisherSpeakingEventsOptions: {
          interval: 100, // Frequency of the polling of audio streams in ms (default 100)
          threshold: -50, // Threshold volume in dB (default -50)
        },
      });

      // --- Init a session ---
      this.sessionCamera = this.OVCamera.initSession();
      // this.sessionScreen = this.OVScreen.initSession();

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
        if (this.isHost && this.isFreeze) {
          this.setFreezeFromHost(stream.connection);
        }
        if (
          this.publisher &&
          stream.creationTime > this.publisher.stream.creationTime
        ) {
          this.messageHistory.push({
            sender: "system :",
            message:
              JSON.parse(stream.connection.data).clientData +
              "이(가) 입장했습니다.",
          });
        }

        const subscriber = this.sessionCamera.subscribe(stream);
        console.log("Camera Stream created");
        console.log(subscriber);
        this.subscribersCamera.push(subscriber); // 해당 스트림을 subscribe하고 video player를 삽입한다.
      });

      // On every Stream destroyed...
      this.sessionCamera.on("streamDestroyed", ({ stream }) => {
        if (stream.typeOfVideo != "CAMERA") return;
        this.messageHistory.push({
          sender: "system :",
          message:
            JSON.parse(stream.connection.data).clientData +
            "이(가) 퇴장했습니다.",
        });
        const index = this.subscribersCamera.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribersCamera.splice(index, 1);
        }
      });

      this.sessionCamera.on("publisherStartSpeaking", (event) => {
        console.log(
          "User " + event.connection.connectionId + " start speaking"
        );
        this.sendHighlight(event.connection.connectionId);
      });

      this.sessionCamera.on("publisherStopSpeaking", (event) => {
        console.log("User " + event.connection.connectionId + " stop speaking");
        this.sendUndoHighlight(event.connection.connectionId);
      });

      // 말하고 있는 사람 하이라이트 표시 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/highlight`, (event) => {
        console.log(event.data); // Message
        this.highlightSpeaker(event.data);
      });

      this.sessionCamera.on(
        `signal:${this.mySessionId}/undohighlight`,
        (event) => {
          console.log(event.data); // Message
          this.undoHighlightSpeaker(event.data);
        }
      );

      // On every asynchronous exception...
      this.sessionCamera.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // 채팅 메시지 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/message`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.appendMessage(event);
      });

      // 방 얼리기
      this.sessionCamera.on(`signal:${this.mySessionId}/setFreeze`, (event) => {
        this.setFreezeInClient(event.data);
      });

      // 방 얼리기 해제
      this.sessionCamera.on(
        `signal:${this.mySessionId}/undoFreeze`,
        (event) => {
          this.undoFreezeInClient(event.data);
        }
      );

      // 경매 시작 신호 수신
      this.sessionCamera.on(
        `signal:${this.mySessionId}/startAuction`,
        (event) => {
          this.startAuctionInClient(event.data);
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
          this.finishAuctionInClient(event.data);
        }
      );

      // 경매 결과 신호 수신
      this.sessionCamera.on(`signal:${this.mySessionId}/result`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.showResult(event);
      });

      this.sessionCamera.on(`signal:${this.mySessionId}/kickout`, () => {
        this.kickoutInClient();
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken().then((token) => {
        this.cameraToken = token;
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

            this.mainStreamManager = publisher;
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
              userNo: this.userNo,
              isHost: this.isHost,
            })
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },

    // 지금 말하고 있는 사람 주위에 테두리를 만든다.
    highlightSpeaker(data) {
      console.log(this.$refs);
      const speakerId = JSON.parse(data).connectionId;
      if (speakerId == this.publisher.stream.connection.connectionId) {
        this.$refs.publisher.$el.style.border = "2px solid white";
      }

      // $refs.subscribersCamera 배열을 선형순회하는 건 오래 걸리므로
      // 인덱스를 통해 접근하는 방법을 생각해봤지만,
      // v-for에 refs를 적용해 만들어진 배열이 원본 배열의 순서를 보장하지 않으므로
      // 일단 포기..
      if (
        this.$refs.subscribersCamera &&
        this.$refs.subscribersCamera.length > 0
      ) {
        for (let sub of this.$refs.subscribersCamera) {
          console.log("connectionId");
          if (speakerId == sub.streamManager.stream.connection.connectionId) {
            sub.$el.style.border = "2px solid red";
          }
        }
      }
    },

    // 말을 하다가 마친 사람 주위에 있던 테두리를 없앤다.
    undoHighlightSpeaker(data) {
      const speakerId = JSON.parse(data).connectionId;
      if (speakerId == this.publisher.stream.connection.connectionId) {
        this.$refs.publisher.$el.style.border = "";
      }

      if (
        this.$refs.subscribersCamera &&
        this.$refs.subscribersCamera.length > 0
      ) {
        for (let sub of this.$refs.subscribersCamera) {
          console.log("connectionId");
          if (speakerId == sub.streamManager.stream.connection.connectionId) {
            sub.$el.style.border = "";
          }
        }
      }
    },

    sendHighlight(connectionId) {
      this.sessionCamera
        .signal({
          data: JSON.stringify({
            connectionId: connectionId,
          }),
          to: [],
          type: this.mySessionId + "/highlight",
        })
        .then(() => {
          console.log();
        });
    },

    sendUndoHighlight(connectionId) {
      this.sessionCamera
        .signal({
          data: JSON.stringify({
            connectionId: connectionId,
          }),
          to: [],
          type: this.mySessionId + "/undohighlight",
        })
        .then(() => {
          console.log();
        });
    },

    // 채팅 보내기
    submitMessage() {
      if (!this.isHost && this.isFreeze) {
        alert(
          "호스트가 방을 얼렸습니다. 호스트가 해제하기 전까지 메시지를 보낼 수 없습니다."
        );
        return;
      }
      let toArray = [];
      let sender = this.myUserName;
      if (this.toWhisper) {
        toArray.push(this.toWhisper);
        toArray.push(this.publisher.stream.connection);
        sender +=
          "이(가) " + JSON.parse(this.toWhisper.data).clientData + "에게";
      } else {
        sender += "이(가) 모두에게";
      }
      sender += " : ";

      this.sessionCamera
        .signal({
          data: JSON.stringify({
            sender: sender,
            message: this.message,
          }),
          to: toArray,
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
      if (!this.isHost && this.isFreeze) {
        alert("방장이 방을 얼렸습니다. 음성이 제한됩니다.");
        return;
      }
      this.isAudioOn = !this.isAudioOn;
      if (this.publisher) {
        this.publisher.publishAudio(this.isAudioOn);
      }
    },

    // },

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

    leaveSession(reason) {
      this.closeModal();
      console.log("leaveSession");
      console.log(this.cameraToken);
      let data = {
        sessionName: this.mySessionId,
        cameraToken: this.cameraToken,
        // screenToken: this.screenToken,
        userNo: this.userNo,
      };
      if (reason == "kick-out") {
        data.reason = reason;
        alert("퇴장당했습니다.");
      } else {
        data.reason = "normal";
      }
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.sessionCamera) this.sessionCamera.disconnect();

      axios
        .post("sessions/removeUser", JSON.stringify(data))
        .then(() => {
          this.clearData();
        })
        .catch(() => {
          console.log("에러 발생");
        });

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    clearData() {
      this.OVCamera = undefined;
      // this.OVScreen = undefined;
      this.sessionCamera = undefined;
      // this.sessionScreen = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.publisherId = undefined;
      // this.publisherMonitor = undefined;
      this.subscribersCamera = [];
      // this.subscribersScreen = [];

      this.cameraToken = null;
      // this.screenToken = null;
      this.mySessionId = null;
      this.myUserName = null;
      this.userNo = null;
      this.message = null;
      this.messageHistory = [];
      this.toWhisper = null;
      this.now = null;
      this.isHost = false;
      this.isFreeze = false;

      this.openBid = false;
      this.isManual = false;

      this.top3List = [];
      this.currentBidder = null;
      this.currentPrice = "0";
      this.priceToBid = "0";
      this.isFinished = false;
      this.hostId = null;
      this.remainingTime = 0;
    },
    updateMainVideoStreamManager(stream) {
      // if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    setWhisper(event) {
      console.log("setWhisper");
      console.log(event);
    },

    getClientId(sub) {
      return JSON.parse(sub.stream.connection.data).clientData;
    },

    kickoutFromHost(connection) {
      const kickoutList = [];
      kickoutList.push(connection);
      console.log("kickout");
      if (this.isHost) {
        // 서버에 블랙리스트 등록 먼저 해야 함
        this.sessionCamera.signal({
          data: JSON.stringify({
            sender: "system :",
            message: "강제 퇴장.",
          }),
          to: kickoutList,
          type: this.mySessionId + "/kickout",
        });
      }
    },

    kickoutInClient() {
      this.leaveSession("kick-out");
    },
  },
};
</script>
<style scoped>
#buttonLeaveSession {
  margin-top: 20px;
}
#session-video-container {
  overflow-x: auto;
  white-space: nowrap;
  height: auto;
  text-align: left;
}
#chat-history {
  height: 80%;
  background-color: white;
  overflow-y: auto;
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

#session-container {
  background-color: black;
}

#session-chat-panel {
  height: 70%;
}

#session-chat-history {
  height: 80%;
}

#chat-control-panel {
  height: 8%;
}
#chat-input {
  height: 12%;
}

footer {
  bottom: 0;
  padding: 1em;
  background-color: black;
}
textarea {
  resize: none;
}
</style>
