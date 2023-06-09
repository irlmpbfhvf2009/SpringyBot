package com.lwdevelop.botfactory.bot.telent.handler;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.lwdevelop.botfactory.Common;
import com.lwdevelop.botfactory.bot.telent.TalentBot;
import com.lwdevelop.botfactory.bot.telent.utils.KeyboardButton;
import com.lwdevelop.botfactory.bot.telent.utils.SpringyBotEnum;
import com.lwdevelop.dto.JobPostingDTO;
import com.lwdevelop.dto.JobSeekerDTO;
import com.lwdevelop.dto.SpringyBotDTO;
import com.lwdevelop.entity.ChannelMessageIdPostCounts;
import com.lwdevelop.entity.GroupMessageIdPostCounts;
import com.lwdevelop.entity.JobPosting;
import com.lwdevelop.entity.JobSeeker;
import com.lwdevelop.entity.SpringyBot;
import com.lwdevelop.service.impl.JobManagementServiceImpl;
import com.lwdevelop.service.impl.SpringyBotServiceImpl;
import com.lwdevelop.utils.SpringUtils;

public class CallbackQuerys {

    @Autowired
    private JobManagementServiceImpl jobManagementServiceImpl = SpringUtils.getApplicationContext()
            .getBean(JobManagementServiceImpl.class);
    @Autowired
    private SpringyBotServiceImpl springyBotServiceImpl = SpringUtils.getApplicationContext()
            .getBean(SpringyBotServiceImpl.class);

    private SendMessage response;
    private Common common;
    private CallbackQuery callbackQuery;

    public CallbackQuerys(Common common){
        this.common = common;
        this.callbackQuery = common.getUpdate().getCallbackQuery();
        String chatId = String.valueOf(common.getUpdate().getCallbackQuery().getFrom().getId());
        this.response = new SendMessage();
        this.response.setChatId(chatId);
        this.response.setDisableNotification(false);
        this.response.setDisableWebPagePreview(false);
    }

    public void handler() {

        if (callbackQuery.getData().startsWith(SpringyBotEnum.CLEAR_JOBPOSTING.getText())) {

            String userId = callbackQuery.getData().substring(SpringyBotEnum.CLEAR_JOBPOSTING.getText().length(),
                    callbackQuery.getData().lastIndexOf("_"));
            String botId = callbackQuery.getData().substring(callbackQuery.getData().lastIndexOf("_") + 1);

            // 在这里根据 springyBotId 和 userId 进行相应的清除操作
            JobPosting jobPosting = jobManagementServiceImpl.findByUserIdAndBotIdWithJobPosting(userId, botId);
            jobManagementServiceImpl.saveJobPosting(new JobPostingDTO().resetJobPostingFields(jobPosting));

            // // 清除訊息
            Long id = Long.valueOf(jobPosting.getBotId());
            SpringyBot springyBot = springyBotServiceImpl.findById(id).get();
            SpringyBotDTO springyBotDTO = new SpringyBotDTO();
            springyBotDTO.setToken(springyBot.getToken());
            springyBotDTO.setUsername(springyBot.getUsername());
            TalentBot custom = new TalentBot(springyBotDTO);

            JobPostingDTO jobPostingDTO = new JobPostingDTO().convertToJobPostingDTO(jobPosting);

            Integer messageId = jobPosting.getLastMessageId();
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(userId);
            editMessageText.setMessageId(messageId);
            editMessageText.setText(SpringyBotEnum.JOBPOSTING_DEFAULT_FORM.getText());

            editMessageText.setReplyMarkup(new KeyboardButton().keyboard_jobPosting(jobPostingDTO, true));
            try {
                custom.executeAsync(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            List<ChannelMessageIdPostCounts> channelMessageIdPostCounts = jobManagementServiceImpl
                    .findAllByBotIdAndUserIdAndTypeWithChannelMessageIdPostCounts(jobPosting.getBotId(), userId,
                            SpringyBotEnum.JOBPOSTING.getText());
            List<GroupMessageIdPostCounts> groupMessageIdPostCounts = jobManagementServiceImpl
                    .findAllByBotIdAndUserIdAndTypeWithGroupMessageIdPostCounts(jobPosting.getBotId(), userId,
                            SpringyBotEnum.JOBPOSTING.getText());

            channelMessageIdPostCounts.stream().forEach(cmp -> {
                DeleteMessage dm = new DeleteMessage(String.valueOf(cmp.getChannelId()),cmp.getMessageId());
                common.executeAsync(dm);
                cmp.setMessageId(-1);
                cmp.setPostCount(0);
                jobManagementServiceImpl.saveChannelMessageIdPostCounts(cmp);
            });

            groupMessageIdPostCounts.stream().forEach(cmp -> {
                DeleteMessage dm = new DeleteMessage(String.valueOf(cmp.getGroupId()),cmp.getMessageId());
                common.executeAsync(dm);
                cmp.setMessageId(-1);
                cmp.setPostCount(0);
                jobManagementServiceImpl.saveGroupMessageIdPostCounts(cmp);
            });

            this.response.setText(SpringyBotEnum.SUCCESSFULLYDELETED.getText());
            common.executeAsync(this.response);
        } else if (callbackQuery.getData().startsWith(SpringyBotEnum.CLEAR_JOBSEEKER.getText())) {

            String userId = callbackQuery.getData().substring(SpringyBotEnum.CLEAR_JOBSEEKER.getText().length(),
                    callbackQuery.getData().lastIndexOf("_"));
            String botId = callbackQuery.getData().substring(callbackQuery.getData().lastIndexOf("_") + 1);
            // 在这里根据 springyBotId 和 userId 进行相应的清除操作
            JobSeeker jobSeeker = jobManagementServiceImpl.findByUserIdAndBotIdWithJobSeeker(userId, botId);

            jobManagementServiceImpl.saveJobSeeker(new JobSeekerDTO().resetJobSeekerFields(jobSeeker));

            // 清除訊息
            Long id = Long.valueOf(jobSeeker.getBotId());
            SpringyBot springyBot = springyBotServiceImpl.findById(id).get();
            SpringyBotDTO springyBotDTO = new SpringyBotDTO();
            springyBotDTO.setToken(springyBot.getToken());
            springyBotDTO.setUsername(springyBot.getUsername());
            TalentBot custom = new TalentBot(springyBotDTO);

            JobSeekerDTO jobSeekerDTO = new JobSeekerDTO().convertToJobSeekerDTO(jobSeeker);

            Integer messageId = jobSeeker.getLastMessageId();
            EditMessageText editMessageText = new EditMessageText();
            editMessageText.setChatId(userId);
            editMessageText.setMessageId(messageId);
            editMessageText.setText(SpringyBotEnum.JOBSEEKER_DEFAULT_FORM.getText());

            editMessageText.setReplyMarkup(new KeyboardButton().keyboard_jobSeeker(jobSeekerDTO, true));
            try {
                custom.executeAsync(editMessageText);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            List<ChannelMessageIdPostCounts> channelMessageIdPostCounts = jobManagementServiceImpl
                    .findAllByBotIdAndUserIdAndTypeWithChannelMessageIdPostCounts(jobSeeker.getBotId(), userId,
                            SpringyBotEnum.JOBSEEKER.getText());
            List<GroupMessageIdPostCounts> groupMessageIdPostCounts = jobManagementServiceImpl
                    .findAllByBotIdAndUserIdAndTypeWithGroupMessageIdPostCounts(jobSeeker.getBotId(), userId,
                            SpringyBotEnum.JOBSEEKER.getText());
            channelMessageIdPostCounts.stream().forEach(cmp -> {
                DeleteMessage dm = new DeleteMessage(String.valueOf(cmp.getChannelId()),cmp.getMessageId());
                common.executeAsync(dm);
                cmp.setMessageId(-1);
                cmp.setPostCount(0);
                jobManagementServiceImpl.saveChannelMessageIdPostCounts(cmp);
            });

            groupMessageIdPostCounts.stream().forEach(cmp -> {
                DeleteMessage dm = new DeleteMessage(String.valueOf(cmp.getGroupId()),cmp.getMessageId());
                common.executeAsync(dm);
                cmp.setMessageId(-1);
                cmp.setPostCount(0);
                jobManagementServiceImpl.saveGroupMessageIdPostCounts(cmp);
            });

            this.response.setText(SpringyBotEnum.SUCCESSFULLYDELETED.getText());
            common.executeAsync(this.response);
        } else if (callbackQuery.getData().equals(SpringyBotEnum.EDIT_JOBPOSTING.getText())) {
            response.setText(SpringyBotEnum.REMIND_EDITOR.getText());

            common.executeAsync(this.response);
        } else if (callbackQuery.getData().equals(SpringyBotEnum.EDIT_JOBSEEKER.getText())) {
            response.setText(SpringyBotEnum.REMIND_EDITOR.getText());
            common.executeAsync(this.response);
        }

    }

}
