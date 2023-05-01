package com.lwdevelop.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 求职者ID

    private String userId;

    private String botId;

    private String name; // 姓名

    private String gender; // 性别

    private String dateOfBirth; // 出生日期

    private String age; // 年龄

    private String nationality; // 国籍

    private String education; // 学历

    private String skills; // 技能

    private String targetPosition; // 目标职位

    private String resources; // 手上资源

    private String expectedSalary; // 期望薪资

    private String workExperience; // 工作经历

    private String selfIntroduction; // 自我介绍

    private String flightNumber; // 咨询飞机号
    
    private Integer lastMessageId;


    // channelId  messageId 訊息ID  postCount 發送次數
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ChannelMessageIdPostCounts> channelMessageIdPostCounts;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<GroupMessageIdPostCounts> groupMessageIdPostCounts;

    public JobSeeker(String userId, String botId, Integer lastMessageId) {
        this.userId = userId;
        this.botId = botId;
        this.lastMessageId = lastMessageId;
    }


}
