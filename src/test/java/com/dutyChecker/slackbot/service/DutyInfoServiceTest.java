package com.dutyChecker.slackbot.service;

import com.dutyChecker.slackbot.model.DutyInfo;
import com.dutyChecker.slackbot.repository.DutyInfoRepository;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DutyInfoServiceTest {

    @Mock
    private Slack slack;

    @Mock
    private MethodsClient methodsClient;

    @Mock
    private DutyInfoRepository dutyInfoRepository;

    @InjectMocks
    private DutyInfoService dutyInfoService;

    private static final String TEST_TOKEN = "xoxb-test-token";
    private static final String TEST_CHANNEL = "test-channel";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(dutyInfoService, "slackToken", TEST_TOKEN);
        ReflectionTestUtils.setField(dutyInfoService, "slackChannel", TEST_CHANNEL);
    }

    @Test
    void 당직정보_조회_테스트() {
        // given
        DutyInfo expectedDutyInfo = new DutyInfo("100", "200");
        List<DutyInfo> expectedList = List.of(expectedDutyInfo);

        when(dutyInfoRepository.findAll()).thenReturn(expectedList);

        // when
        String result = dutyInfoService.getDutyInfo();

        // then
        assertNotNull(result);
        assertTrue(result.contains("당직정보:"));
        assertTrue(result.contains("TPS: 100"));
        assertTrue(result.contains("Web Connection: 200"));
        
        verify(dutyInfoRepository, times(1)).findAll();
    }

    @Test
    void 당직정보_없을때_테스트() {
        // given
        when(dutyInfoRepository.findAll()).thenReturn(List.of());

        // when
        String result = dutyInfoService.getDutyInfo();

        // then
        assertNotNull(result);
        assertEquals("당직정보:\n", result);
        
        verify(dutyInfoRepository, times(1)).findAll();
    }

    @Test
    void 슬랙_메시지_전송_테스트() throws IOException, SlackApiException {
        // given
        String message = "테스트 메시지";
        when(slack.methods(TEST_TOKEN)).thenReturn(methodsClient);

        // when
        dutyInfoService.sendMessage(message);

        // then
        verify(slack, times(1)).methods(TEST_TOKEN);
    }
} 