package com.lwdevelop.bot.handler.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import com.lwdevelop.bot.utils.Common;
import com.lwdevelop.bot.utils.KeyboardButton;
import com.lwdevelop.dto.JobPostingDTO;
import com.lwdevelop.entity.JobPosting;
import com.lwdevelop.entity.JobSeeker;
import com.lwdevelop.entity.JobUser;
import com.lwdevelop.entity.SpringyBot;
import com.lwdevelop.service.impl.JobManagementServiceImpl;
import com.lwdevelop.service.impl.SpringyBotServiceImpl;
import com.lwdevelop.utils.SpringUtils;

public class Job {
    SendMessage response;

    @Autowired
    private JobManagementServiceImpl jobManagementServiceImpl = SpringUtils.getApplicationContext()
            .getBean(JobManagementServiceImpl.class);

    @Autowired
    private SpringyBotServiceImpl springyBotServiceImpl = SpringUtils.getApplicationContext()
            .getBean(SpringyBotServiceImpl.class);

    private void jobMessageSetting(Message message) {
        this.response = new SendMessage();
        this.response.setChatId(String.valueOf(message.getChatId()));
        this.response.setDisableNotification(false);
        this.response.setDisableWebPagePreview(false);
    }

    public void setResponse_jobSeeker_management(Common common) {
        this.jobMessageSetting(common.getUpdate().getMessage());
        this.response.setText("求职人员\n" +
                "姓名：\n" +
                "男女：\n" +
                "出生_年_月_日\n" +
                "年龄：\n" +
                "国籍：\n" +
                "学历：\n" +
                "技能：\n" +
                "目标职位：\n" +
                "手上有什么资源：\n" +
                "期望薪资：\n" +
                "工作经历:(限50字以内)\n\n" +
                "自我介绍:(限50字以内)");
        this.response
                .setReplyMarkup(new KeyboardButton().keyboard_jobSeeker(common, new JobSeeker()));
        // common.sendResponseAsync(this.response);

    }

    public void setResponse_jobPosting_management(Common common) {
        this.jobMessageSetting(common.getUpdate().getMessage());
        String userId = String.valueOf(common.getUpdate().getMessage().getChatId());
        String firstname = common.getUpdate().getMessage().getChat().getFirstName();
        String username = common.getUpdate().getMessage().getChat().getUserName();
        String lastname = common.getUpdate().getMessage().getChat().getLastName();

        if (firstname == null) {
            firstname = "";
        }
        if (username == null) {
            username = "";
        }
        if (lastname == null) {
            lastname = "";
        }

        SpringyBot springyBot = springyBotServiceImpl.findById(common.getSpringyBotId()).get();
        JobPosting jobPosting = this.jobManagementServiceImpl.findByUserIdWithJobPosting(userId);
        JobUser jobUser = new JobUser();
        jobUser.setFirstname(firstname);
        jobUser.setLastname(lastname);
        jobUser.setUserId(userId);
        jobUser.setUsername(username);
        springyBot.getJobUser().stream().filter(j -> j.getUserId().equals(userId))
                .findFirst().ifPresentOrElse(j -> {
                    j.getJobPosting().stream().filter(ju -> ju.getUserId().equals(userId)).findFirst()
                            .ifPresentOrElse(jp -> {
                                this.response.setText("招聘人才\n" +
                                        "公司：" + jp.getCompany() + "\n" +
                                        "职位：" + jp.getPosition() + "\n" +
                                        "底薪：" + jp.getBaseSalary() + "\n" +
                                        "提成：" + jp.getCommission() + "\n" +
                                        "上班时间：" + jp.getWorkTime() + "\n" +
                                        "要求内容：" + jp.getRequirements() + "\n" +
                                        "🐌 地址：" + jp.getLocation() + "\n" +
                                        "✈️咨询飞机号： " + jp.getFlightNumber());

                                JobPostingDTO jobPostingDTO = new JobPostingDTO(userId, jp.getCompany(),
                                        jp.getPosition(), jp.getBaseSalary(), jp.getCommission(), jp.getWorkTime(),
                                        jp.getRequirements(), jp.getLocation(), jp.getFlightNumber());
                                this.response.setReplyMarkup(
                                        new KeyboardButton().keyboard_jobPosting(jobPostingDTO));
                                Integer lastMessageId = common.sendResponseAsync(this.response);
                                jobPosting.setLastMessageId(lastMessageId);
                                this.jobManagementServiceImpl.saveJobPosting(jobPosting);
                            }, () -> {
                                this.response.setText("招聘人才\n" +
                                        "公司：\n" +
                                        "职位：\n" +
                                        "底薪：\n" +
                                        "提成：\n" +
                                        "上班时间：\n" +
                                        "要求内容：\n" +
                                        "🐌 地址：\n" +
                                        "✈️咨询飞机号： ");
                                JobPostingDTO jobPostingDTO = new JobPostingDTO(userId,
                                        String.valueOf(common.getSpringyBotId()));
                                this.response.setReplyMarkup(
                                        new KeyboardButton().keyboard_jobPosting(jobPostingDTO));
                                Integer lastMessageId = common.sendResponseAsync(this.response);
                                JobPosting jobPosting_ = new JobPosting(userId,
                                        String.valueOf(common.getSpringyBotId()), lastMessageId);
                                springyBot.getJobUser().stream().filter(ju -> ju.getUserId().equals(userId)).findFirst()
                                        .ifPresent(ju -> ju.getJobPosting().add(jobPosting_));
                                springyBotServiceImpl.save(springyBot);
                            });
                }, () -> {
                    springyBot.getJobUser().add(jobUser);
                    springyBotServiceImpl.save(springyBot);
                });

    }

}
