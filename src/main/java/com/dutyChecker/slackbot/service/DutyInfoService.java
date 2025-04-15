package com.dutyChecker.slackbot.service;

import com.dutyChecker.slackbot.repository.DutyInfoRepository;
import com.dutyChecker.slackbot.model.DutyInfo;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DutyInfoService {

    @Value("${slack.api.token}")
    private String slackToken;

    @Value("${slack.channel}")
    private String slackChannel;

    private final DutyInfoRepository repository;

    public DutyInfoService(DutyInfoRepository repository) {
        this.repository = repository;
    }

    public String getDutyInfo() {
        List<DutyInfo> dutyInfos = repository.findAll();
        StringBuilder response = new StringBuilder("당직정보:\n");

        for (DutyInfo info : dutyInfos) {
            response.append(String.format("TPS: %s, Web Connection: %s\n", info.getTps(), info.getWebConnection()));
        }

        sendToSlack(response.toString());
        return response.toString();
    }

    private void sendToSlack(String message) {
        Slack slack = Slack.getInstance();
        try {
            slack.methods(slackToken).chatPostMessage(req -> req
                    .channel(slackChannel)
                    .text(message));
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }
}