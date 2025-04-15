package com.dutyChecker.slackbot.controller;

import com.dutyChecker.slackbot.service.SlackService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/slack")
public class SlackController {

    private final SlackService slackService;

    public SlackController(SlackService slackService) {
        this.slackService = slackService;
    }

    @PostMapping("/command")
    public String handleCommand(@RequestParam("text") String commandText) {
        if ("당직정보".equals(commandText)) {
            slackService.sendMessage("현재 당직 정보를 조회 중입니다...");
            return "당직 정보를 Slack 채널에 전송했습니다.";
        }
        return "알 수 없는 명령어입니다.";
    }
}