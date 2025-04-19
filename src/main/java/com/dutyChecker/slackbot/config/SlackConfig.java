package com.dutyChecker.slackbot.config;

import com.dutyChecker.slackbot.repository.DutyInfoRepository;
import com.dutyChecker.slackbot.service.DutyInfoService;
import com.slack.api.Slack;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SlackConfig {
    
    @Bean
    public Slack slack() {
        return Slack.getInstance();
    }
    
    @Bean
    public DutyInfoService dutyInfoService(Slack slack, DutyInfoRepository dutyInfoRepository) {
        return new DutyInfoService(slack, dutyInfoRepository);
    }
} 