package com.dutyChecker.slackbot.controller;

import com.dutyChecker.slackbot.service.DutyInfoService;
import com.slack.api.methods.SlackApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SlackControllerTest {

    private SlackController slackController;

    @Mock
    private DutyInfoService dutyInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        slackController = new SlackController(dutyInfoService);
    }

    @Test
    void 당직보고_명령어_수신시_서비스_호출_테스트() throws SlackApiException, IOException {
        // given
        String command = "/당직보고";
        String expectedResponse = "명령어를 처리 중입니다...";
        String expectedDutyInfo = "당직정보:\nTPS: 100, Web Connection: 200\n";

        when(dutyInfoService.getDutyInfo()).thenReturn(expectedDutyInfo);

        // when
        String actualResponse = slackController.handleCommand(command);

        // then
        assertEquals(expectedResponse, actualResponse);
        
        // Service 메서드 호출 확인
        verify(dutyInfoService, timeout(1000).times(1)).getDutyInfo();
        verify(dutyInfoService, timeout(1000).times(1)).sendMessage(expectedDutyInfo);
    }

    @Test
    void 알수없는_명령어_테스트() throws SlackApiException, IOException {
        // given
        String command = "/알수없는명령어";
        String expectedResponse = "명령어를 처리 중입니다...";

        // when
        String actualResponse = slackController.handleCommand(command);

        // then
        assertEquals(expectedResponse, actualResponse);
        
        // Service 메서드가 호출되지 않았는지 확인
        verify(dutyInfoService, never()).getDutyInfo();
        verify(dutyInfoService, never()).sendMessage(anyString());
    }
} 