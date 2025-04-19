package com.dutyChecker.slackbot.controller;

import com.dutyChecker.slackbot.service.DutyInfoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/slack")
public class SlackController {

    private final DutyInfoService dutyInfoService;

    public SlackController(DutyInfoService dutyInfoService) {
        this.dutyInfoService = dutyInfoService;
    }

    @PostMapping("/command")
    public String handleCommand(
            @RequestParam("command") String command) {
        
        // 즉시 응답 반환
        String immediateResponse = "명령어를 처리 중입니다...";
        
        // 비동기로 메시지 전송
        if ("/당직보고".equals(command)) {
            new Thread(() -> {
                try {
                    String dutyInfo = dutyInfoService.getDutyInfo();
                    dutyInfoService.sendMessage(dutyInfo);
                } catch (Exception e) {
                    System.err.println("메시지 전송 중 오류 발생: " + e.getMessage());
                }
            }).start();
        }
        
        return immediateResponse;
    }
}