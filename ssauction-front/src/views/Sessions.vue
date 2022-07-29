<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!session">
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
            <input
              v-model="mySessionId"
              class="form-control"
              type="text"
              required
            />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success" @click="joinSession()">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div class="row" id="session" v-if="session">
      <div id="session-header">
        <div class="col">
          <h1 id="session-title">{{ mySessionId }}</h1>
        </div>
        <div class="col">
          <input
            class="btn btn-large btn-danger"
            type="button"
            id="buttonLeaveSession"
            @click="leaveSession"
            value="Leave session"
          />
        </div>
      </div>
      <div id="session-view" v-if="mode == 'basic'">
        <div id="video-container">
          <participant-video
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
        <div class="row main-video">
          <div class="col-md-9" id="main-video">
            <main-video :stream-manager="mainStreamManager" />
          </div>
          <div class="col-md-3" id="session-chat">
            <div id="chat-history">
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
      <in-session-panel :isVideoOn="this.isVideoOn" :isMicOn="this.isMicOn" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import MainVideo from "@/components/MainVideo.vue";
import ParticipantVideo from "./components/ParticipantVideo.vue";
import ChatMessage from "./components/ChatMessage.vue";
import InSessionPanel from "./components/InSessionPanel.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

// const OPENVIDU_SERVER_URL = "https://" + location.hostname + ":4443";
// const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "Sessions",
  components: {
    MainVideo,
    ParticipantVideo,
    ChatMessage,
    InSessionPanel,
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
    };
  },
  methods: {
    joinSession() {
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
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

      // On every message received...
      this.session.on(`signal:${this.mySessionId}`, (event) => {
        console.log(event.data); // Message
        console.log(event.from); // Connection object of the sender
        console.log(event.type); // The type of message ("my-chat")
        this.appendMessage(event);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken().then((token) => {
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            let publisher = this.OV.initPublisher(undefined, {
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

            this.session.publish(this.publisher);
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

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      this.messageHistory = [];

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
          .then((data) => resolve(data[0]))
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
          type: this.mySessionId,
        })
        .then(() => {
          console.log();
        });
      this.message = null;
    },

    appendMessage(event) {
      const c = JSON.parse(event.data);
      this.messageHistory.push(c);
    },

    turnOnVideo() {
      this.session.publish(this.publisher);
    },

    turnOffVideo() {
      this.session.unpublish(this.publisher);
    },

    turnOnMic() {},
    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    // createSession(sessionId) {
    //   return new Promise((resolve, reject) => {
    //     axios
    //       .post(
    //         `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
    //         JSON.stringify({
    //           customSessionId: sessionId,
    //         }),
    //         {
    //           auth: {
    //             username: "OPENVIDUAPP",
    //             password: OPENVIDU_SERVER_SECRET,
    //           },
    //         }
    //       )
    //       .then((response) => response.data)
    //       .then((data) => resolve(data.id))
    //       .catch((error) => {
    //         if (error.response.status === 409) {
    //           resolve(sessionId);
    //         } else {
    //           console.warn(
    //             `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`
    //           );
    //           if (
    //             window.confirm(
    //               `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`
    //             )
    //           ) {
    //             location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
    //           }
    //           reject(error.response);
    //         }
    //       });
    //   });
    // },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
    // createToken(sessionId) {
    //   return new Promise((resolve, reject) => {
    //     axios
    //       .post(
    //         `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
    //         {},
    //         {
    //           auth: {
    //             username: "OPENVIDUAPP",
    //             password: OPENVIDU_SERVER_SECRET,
    //           },
    //         }
    //       )
    //       .then((response) => response.data)
    //       .then((data) => resolve(data.token))
    //       .catch((error) => reject(error.response));
    //   });
    // },
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
</style>
