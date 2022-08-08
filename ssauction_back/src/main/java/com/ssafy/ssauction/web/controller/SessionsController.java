package com.ssafy.ssauction.web.controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import com.ssafy.ssauction.web.dto.Sessions.BidInfoDto;
import com.ssafy.ssauction.web.dto.Sessions.SessionsCreateJoinRequestDto;
import com.ssafy.ssauction.web.dto.Sessions.SessionsRemoveUserRequestDto;
import com.ssafy.ssauction.web.dto.Sessions.SessionsTokenResponseDto;
import io.swagger.annotations.*;
import okhttp3.Response;
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

    // 경매장별 입찰 정보를 저장하는 map
    // 각 경매장 이름을 key로 사용하고, 각 경매장별 상위 3명의 정보를 ArrayDeque에 담아 value로 저장한다.
    // ArrayDeque는 적은 금액에서 큰 금액 순으로 3개까지만 저장한다.
    private Map<String, ArrayDeque<BidInfoDto>> mapBids = new ConcurrentHashMap<>();

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

    @PostMapping("/getToken")
    @ApiOperation(value = "Token 요청", notes = "<strong>OpenVidu 서버</strong>에 토큰을 요청한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = SessionsTokenResponseDto.class),
            @ApiResponse(code = 404, message = "잘못된 요청", response = SessionsTokenResponseDto.class),
            @ApiResponse(code = 500, message = "서버 오류", response = SessionsTokenResponseDto.class)
    })
    public ResponseEntity<SessionsTokenResponseDto> getToken(@RequestBody @ApiParam(value="세션 이름 및 유저 정보", required = true) SessionsCreateJoinRequestDto sessionNameLoggedUserParam)
            throws ParseException {

//        try {
//            checkUserLogged(httpSession);
//        } catch (Exception e) {
//            return getErrorResponse(e);
//        }
        System.out.println("Getting a token from OpenVidu Server | {sessionName, loggedUser, createNewSession}=" + sessionNameLoggedUserParam);

        // The video-call to connect
        String sessionName = (String) sessionNameLoggedUserParam.getSessionName();
        String loggedUser = (String) sessionNameLoggedUserParam.getLoggedUser();
        Boolean createNewSession = (Boolean) sessionNameLoggedUserParam.getCreateNewSession();

        // Role associated to this user
        OpenViduRole role = null;
        if (createNewSession)
            role = OpenViduRole.MODERATOR;
        else
            role = OpenViduRole.PUBLISHER;

        // Optional data to be passed to other users when this user connects to the
        // video-call.
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
    public ResponseEntity<JSONObject> removeUser(@RequestBody @ApiParam(value="세션 이름 및 토큰", required = true) SessionsRemoveUserRequestDto sessionNameToken)
            throws Exception {

//        try {
//            checkUserLogged(httpSession);
//        } catch (Exception e) {
//            return getErrorResponse(e);
//        }
        System.out.println("Removing user | {sessionName, token}=" + sessionNameToken);

        // Retrieve the params from BODY
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

    @GetMapping("/bid/{sessionName}")
    public ResponseEntity<ArrayDeque<BidInfoDto>> getBid(@PathVariable String sessionName) {
        if (mapBids.containsKey(sessionName)) {
            return new ResponseEntity<>(mapBids.get(sessionName), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/bid")
    public ResponseEntity<ArrayDeque<BidInfoDto>> updateBid(@RequestBody Map<String, Object> bid) throws Exception {
        String userName = (String) bid.get("bidder");
        String priceToBid = (String) bid.get("priceToBid");
        String sessionName = (String) bid.get("sessionName");

        BidInfoDto newBid = new BidInfoDto(userName, priceToBid);
        if (!mapBids.containsKey(sessionName)) {
            mapBids.put(sessionName, new ArrayDeque<>());
        }
        ArrayDeque<BidInfoDto> queue = mapBids.get(sessionName);
        // 적은 금액에서 큰 금액 순으로 3개까지만 저장한다.
        queue.addLast(newBid);
        // 3개를 초과하는 입찰 데이터는 필요 없으므로 제거한다.
        if (queue.size() > 3) {
            queue.removeFirst();
        }
        System.out.println("bidder: " + userName + " priceToBid: " + priceToBid + " mySessionId: " + sessionName);

        return new ResponseEntity<>(queue, HttpStatus.OK);
    }

    @DeleteMapping("/bid/{sessionName}")
    public ResponseEntity<ArrayDeque<BidInfoDto>> removeBid(@PathVariable String sessionName) throws Exception {
        if (mapBids.containsKey(sessionName)) {
            ArrayDeque<BidInfoDto> result = mapBids.get(sessionName);

            mapBids.remove(sessionName);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
}
