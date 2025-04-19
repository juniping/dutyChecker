package com.dutyChecker.slackbot.service;

import com.dutyChecker.slackbot.model.DutyInfo;
import com.dutyChecker.slackbot.repository.DutyInfoRepository;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DutyInfoService {

    @Value("${slack.api.token}")
    private String slackToken;

    @Value("${slack.channel}")
    private String slackChannel;

    private final Slack slack;
    private final DutyInfoRepository dutyInfoRepository;

    public String getDutyInfo() {
        List<DutyInfo> dutyInfos = dutyInfoRepository.findAll();
        StringBuilder response = new StringBuilder("당직정보:\n");

        for (DutyInfo info : dutyInfos) {
            response.append(String.format("TPS: %s, Web Connection: %s\n", 
                info.getTps(), info.getWebConnection()));
        }
        return response.toString();
    }

    public void sendMessage(String message) throws IOException, SlackApiException {
        if (slackToken == null || slackToken.isEmpty()) {
            throw new IllegalStateException("Slack API 토큰이 설정되지 않았습니다.");
        }
        
        if (slackChannel == null || slackChannel.isEmpty()) {
            throw new IllegalStateException("Slack 채널 ID가 설정되지 않았습니다.");
        }

        System.out.println("Sending message to Slack: " + message);
        
        try {
            slack.methods(slackToken).chatPostMessage(req -> req
                    .channel(slackChannel)
                    .text(message));
        } catch (IOException | SlackApiException e) {
            System.err.println("Slack 메시지 전송 실패: " + e.getMessage());
            throw e;
        }
    }
}