package com.ssafy.ssauction.domain.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import com.ssafy.ssauction.web.dto.Sessions.SessionsCreateJoinRequestDto;
import com.ssafy.ssauction.web.dto.Sessions.SessionsRemoveUserRequestDto;
import com.ssafy.ssauction.web.dto.Sessions.SessionsTokenResponseDto;
import io.swagger.annotations.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;

@Api(value="세션 API", tags={"Sessions"})
@RestController
@RequestMapping("/sessions")
public class SessionsController {

    // OpenVidu object as entrypoint of the SDK
    private OpenVidu openVidu;

    // Collection to pair session names and OpenVidu Session objects
    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    // Collection to pair session names and tokens (the inner Map pairs tokens and
    // role associated)
    private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

    // URL where our OpenVidu server is listening
    private String OPENVIDU_URL;
    // Secret shared with our OpenVidu server
    private String SECRET;

    public SessionsController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }


    //    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    @PostMapping("/getToken")
    @ApiOperation(value = "Token 요청", notes = "<strong>OpenVidu 서버</strong>에 토큰을 요청한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = SessionsTokenResponseDto.class),
            @ApiResponse(code = 404, message = "잘못된 요청", response = SessionsTokenResponseDto.class),
            @ApiResponse(code = 500, message = "서버 오류", response = SessionsTokenResponseDto.class)
    })
//    public ResponseEntity<JSONObject> getToken(@RequestBody String sessionNameParam, @ApiIgnore HttpSession httpSession)
    public ResponseEntity<SessionsTokenResponseDto> getToken(@RequestBody @ApiParam(value="세션 이름 및 유저 정보", required = true) SessionsCreateJoinRequestDto sessionNameLoggedUserParam)
            throws ParseException {

//        try {
//            checkUserLogged(httpSession);
//        } catch (Exception e) {
//            return getErrorResponse(e);
//        }
        System.out.println("Getting a token from OpenVidu Server | {sessionName, loggedUser, createNewSession}=" + sessionNameLoggedUserParam);

//        JSONObject sessionJSON = (JSONObject) new JSONParser().parse(sessionNameLoggedUserParam);
//        JSONObject sessionUserJSON = (JSONObject) new JSONParser().parse(sessionNameLoggedUserParam);

        // The video-call to connect
        String sessionName = (String) sessionNameLoggedUserParam.getSessionName();
        String loggedUser = (String) sessionNameLoggedUserParam.getLoggedUser();
        Boolean createNewSession = (Boolean) sessionNameLoggedUserParam.getCreateNewSession();

        // Role associated to this user
//        OpenViduRole role = LoginController.users.get(httpSession.getAttribute("loggedUser")).role;
        OpenViduRole role = null;
        if (createNewSession)
            role = OpenViduRole.MODERATOR;
        else
            role = OpenViduRole.PUBLISHER;

        // Optional data to be passed to other users when this user connects to the
        // video-call. In this case, a JSON with the value we stored in the HttpSession
        // object on login
//        String serverData = "{\"serverData\": \"" + httpSession.getAttribute("loggedUser") + "\"}";
        String serverData = "{\"serverData\": \"" + loggedUser + "\"}";

        // Build connectionProperties object with the serverData and the role
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(role).build();

        SessionsTokenResponseDto response = new SessionsTokenResponseDto();

        if (this.mapSessions.get(sessionName) != null) {
            // Session already exists
            System.out.println("Existing session " + sessionName);
            try {

                // Generate a new Connection with the recently created connectionProperties
                String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

                // Update our collection storing the new token
                this.mapSessionNamesTokens.get(sessionName).put(token, role);

                // Prepare the response with the token
                response.setToken(token);

                // Return the response to the client
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (OpenViduJavaClientException e1) {
                // If internal error generate an error message and return it to client
                return getErrorResponse(e1);
            } catch (OpenViduHttpException e2) {
                if (404 == e2.getStatus()) {
                    // Invalid sessionId (user left unexpectedly). Session object is not valid
                    // anymore. Clean collections and continue as new session
                    this.mapSessions.remove(sessionName);
                    this.mapSessionNamesTokens.remove(sessionName);
                }
            }
        }

        // New session
        System.out.println("New session " + sessionName);
        try {

            // Create a new OpenVidu Session
            Session session = this.openVidu.createSession();
            // Generate a new Connection with the recently created connectionProperties
            String token = session.createConnection(connectionProperties).getToken();

            // Store the session and the token in our collections
            this.mapSessions.put(sessionName, session);
            this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
            this.mapSessionNamesTokens.get(sessionName).put(token, role);

            // Prepare the response with the token
            response.setToken(token);

            // Return the response to the client
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // If error generate an error message and return it to client
            return getErrorResponse(e);
        }
    }

//    @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
    @PostMapping("/removeUser")
    @ApiOperation(value = "User 제거", notes = "session에 접속해있는 User의 token을 제거한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = JSONObject.class),
            @ApiResponse(code = 404, message = "잘못된 요청", response = JSONObject.class),
            @ApiResponse(code = 500, message = "서버 오류", response = JSONObject.class)
    })
//    public ResponseEntity<JSONObject> removeUser(@RequestBody String sessionNameToken, @ApiIgnore HttpSession httpSession)
    public ResponseEntity<JSONObject> removeUser(@RequestBody @ApiParam(value="세션 이름 및 토큰", required = true) SessionsRemoveUserRequestDto sessionNameToken)
            throws Exception {

//        try {
//            checkUserLogged(httpSession);
//        } catch (Exception e) {
//            return getErrorResponse(e);
//        }
        System.out.println("Removing user | {sessionName, token}=" + sessionNameToken);

        // Retrieve the params from BODY
//        JSONObject sessionNameTokenJSON = (JSONObject) new JSONParser().parse(sessionNameToken);
        String sessionName = (String) sessionNameToken.getSessionName();
        String token = (String) sessionNameToken.getToken();

        // If the session exists
        if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {

            // If the token exists
            if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
                // User left the session
                if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
                    // Last user left: session must be removed
                    this.mapSessions.remove(sessionName);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // The TOKEN wasn't valid
                System.out.println("Problems in the app server: the TOKEN wasn't valid");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            // The SESSION does not exist
            System.out.println("Problems in the app server: the SESSION does not exist");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<SessionsTokenResponseDto> getErrorResponse(Exception e) {
        SessionsTokenResponseDto response = new SessionsTokenResponseDto();
        response.setCause(e.getCause().toString());
        response.setError(e.getMessage());
        response.setException(e.getClass().toString());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void checkUserLogged(HttpSession httpSession) throws Exception {
        if (httpSession == null || httpSession.getAttribute("loggedUser") == null) {
            throw new Exception("User not logged");
        }
    }
    // 현재 열려있는 모든 세션을 조회한다.
    @GetMapping
    public ResponseEntity<List<String>> getSessions() throws Exception {
        List<String> sessionNames = new ArrayList<String>(this.mapSessions.keySet());
        return new ResponseEntity<>(sessionNames, HttpStatus.OK);
    }

    @GetMapping({"{sessionName}"})
    public ResponseEntity<List<String>> getSessionUsers(@PathVariable String sessionName) throws Exception {
        List<String> sessionUsers = new ArrayList<>(this.mapSessionNamesTokens.get(sessionName).keySet());
        return new ResponseEntity<>(sessionUsers, HttpStatus.OK);
    }

    // 입력한 이름을 가진 세션을 닫는다.
    @PostMapping("/closeSession")
    public ResponseEntity<String> closeSession(@RequestParam String sessionName) throws Exception {
        Session sessionToClose = mapSessions.get(sessionName);
        System.out.println(sessionToClose);
        if (sessionToClose.equals(null)) {
            return new ResponseEntity<String>("Unknown session", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        sessionToClose.close();
        mapSessions.remove(sessionName);
        mapSessionNamesTokens.remove(sessionName);
        return new ResponseEntity<>("Session closed", HttpStatus.OK);
    }
}
