package com.dutyChecker.slackbot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DutyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tps;
    private String webConnection;

    public DutyInfo(String tps, String webConnection) {
        this.tps = tps;
        this.webConnection = webConnection;
    }
}
