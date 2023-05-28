package com.lwdevelop.bot.event;

import org.springframework.beans.factory.annotation.Autowired;

import com.lwdevelop.bot.Common;
import com.lwdevelop.entity.InvitationThreshold;
import com.lwdevelop.entity.RobotChannelManagement;
import com.lwdevelop.entity.SpringyBot;
import com.lwdevelop.service.impl.SpringyBotServiceImpl;
import com.lwdevelop.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinChannel {

    @Autowired
    private SpringyBotServiceImpl springyBotServiceImpl = SpringUtils.getApplicationContext()
            .getBean(SpringyBotServiceImpl.class);

    private Common common;
    private Long botId;
    private Long inviteId;
    private String inviteFirstname;
    private String inviteUsername;
    private String inviteLastname;
    private Long channelId;
    private String channelTitle;
    private SpringyBot springyBot;

    public JoinChannel(Common common){
        this.common = common;
        this.botId = common.getBotId();
        this.inviteId = common.getChatMemberUpdated().getFrom().getId();
        this.inviteFirstname = common.getChatMemberUpdated().getFrom().getFirstName();
        this.inviteUsername = common.getChatMemberUpdated().getFrom().getUserName();
        this.inviteLastname = common.getChatMemberUpdated().getFrom().getLastName();
        this.channelId = common.getChatMemberUpdated().getChat().getId();
        this.channelTitle = common.getChatMemberUpdated().getChat().getTitle();
        this.springyBot = springyBotServiceImpl.findById(common.getSpringyBotId()).get();
    }

    public void isUserJoinChannel() {


        // springyBot.getRestrictMember().stream()
        // .filter(rm->rm.getChatId().equals(this.channelId.toString() && rm.getUserId()))


        springyBot.getInvitationThreshold().stream()
                .filter(it -> hasTarget(it))
                .findFirst()
                .ifPresentOrElse(it -> {
                    // it.setStatus(true);
                }, () -> {
                    springyBot.getInvitationThreshold().add(this.getInvitationThreshold());
                });
        springyBotServiceImpl.save(springyBot);
        // log.info("{} invite {} join channel {}", invite_name, invited_name,
        // this.channelTitle);
    }

    public void isBotJoinChannel() {

        springyBot.getRobotChannelManagement().stream()
                .filter(rcm -> hasTarget(rcm))
                .findFirst()
                .ifPresentOrElse(rcm -> {
                    rcm.setStatus(true);
                }, () -> {
                    springyBot.getRobotChannelManagement().add(getRobotChannelManagement());
                });
        springyBotServiceImpl.save(springyBot);
        log.info("{} bot join {} channel", common.getBot().getBotUsername(), this.channelTitle);
    }

    private Boolean hasTarget(RobotChannelManagement rcm) {
        return rcm.getChannelId().equals(this.channelId) && rcm.getBotId().equals(this.botId);
    }

    private Boolean hasTarget(InvitationThreshold it) {
        return it.getChatId().equals(this.channelId) && it.getType().equals("channel");
    }

    private RobotChannelManagement getRobotChannelManagement() {
        RobotChannelManagement robotGroupManagement = new RobotChannelManagement();
        robotGroupManagement.setBotId(this.botId);
        robotGroupManagement.setInviteId(this.inviteId);
        robotGroupManagement.setInviteFirstname(this.inviteFirstname);
        robotGroupManagement.setInviteUsername(this.inviteUsername);
        robotGroupManagement.setInviteLastname(this.inviteLastname);
        robotGroupManagement.setChannelId(this.channelId);
        robotGroupManagement.setChannelTitle(this.channelTitle);
        robotGroupManagement.setLink(this.common.getInviteLink());
        robotGroupManagement.setStatus(true);
        return robotGroupManagement;
    }

    private InvitationThreshold getInvitationThreshold() {
        InvitationThreshold invitationThreshold = new InvitationThreshold();
        return invitationThreshold;
    }

}
