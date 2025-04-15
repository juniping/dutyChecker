package com.dutyChecker.slackbot.service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackService {

    @Value("${slack.api.token}")
    private String slackToken;

    @Value("${slack.channel}")
    private String slackChannel;

    public void sendMessage(String message) {
        Slack slack = Slack.getInstance();
        try {
            // Slack 메시지 전송
            slack.methods(slackToken).chatPostMessage(req -> req
                    .channel(slackChannel)
                    .text(message));
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }
}