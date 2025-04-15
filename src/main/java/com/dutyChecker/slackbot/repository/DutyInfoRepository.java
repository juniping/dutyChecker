package com.dutyChecker.slackbot.repository;

import com.dutyChecker.slackbot.model.DutyInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DutyInfoRepository extends JpaRepository<DutyInfo, Long> {
}