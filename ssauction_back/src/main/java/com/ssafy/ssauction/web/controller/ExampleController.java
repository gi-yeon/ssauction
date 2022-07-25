package com.ssafy.ssauction.web.controller;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.model.StorageType;
import net.nurigo.sdk.message.request.MessageListRequest;
import net.nurigo.sdk.message.request.MultipleMessageSendingRequest;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MessageListResponse;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.MultipleMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
public class ExampleController {

    final DefaultMessageService messageService;

    public ExampleController() {
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
        message.setTo("01064503145");
        message.setText("[Web 발신] SSAFY 7기 공통 프로젝트 A806 - API TEST 문자 메세지"+rand);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
}