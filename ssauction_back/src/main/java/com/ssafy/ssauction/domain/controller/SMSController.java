package com.ssafy.ssauction.domain.controller;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {

    final DefaultMessageService messageService;

    public SMSController() {
        this.messageService = NurigoApp.INSTANCE.initialize("NCSDPSA7LRS9NHSM", "QGB5CCFBGQON2WT5LDXHBYUJRHSFQ8QG", "https://api.coolsms.co.kr");
    }

    /**
     * 단일 메시지 발송 예제
     */
    @PostMapping("/users/sendSMS")
    public SingleMessageSentResponse sendOne() {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        int rand=(int)(Math.random()*1000000);  // 6자리 난수 생성
        message.setFrom("01047280165");
        message.setTo("01047280165");
        message.setText("[Web 발신] SSAFY 7기 공통 프로젝트 A806 - API TEST 문자 메세지"+rand);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
}