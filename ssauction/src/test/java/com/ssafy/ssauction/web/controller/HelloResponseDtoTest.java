package com.ssafy.ssauction.web.controller;

import com.ssafy.ssauction.web.dto.HelloResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
public class HelloResponseDtoTest {
    @Test
    public void lombockFunctionalTest(){
        String name="test";
        int amount=10000;

        HelloResponseDto dto= new HelloResponseDto(name,amount);
        Assertions.assertThat(dto.getName()).isEqualTo(name);
        Assertions.assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
