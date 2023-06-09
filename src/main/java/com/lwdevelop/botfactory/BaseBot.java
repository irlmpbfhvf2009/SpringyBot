package com.lwdevelop.botfactory;

import java.util.HashMap;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.lwdevelop.dto.SpringyBotDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseBot extends TelegramLongPollingBot {

    protected Common common;
    protected Message message;
    protected SpringyBotDTO dto;

    public BaseBot(SpringyBotDTO springyBotDTO) {
        super(new DefaultBotOptions());
        this.dto = springyBotDTO;
        this.common = initializeCommon(springyBotDTO);
    }

    private Common initializeCommon(SpringyBotDTO springyBotDTO) {
        try {
            Common common = new Common(this.dto.getId(), getMe().getId(), getMe().getUserName());
            common.setBot(this);
            common.setUserState(new HashMap<>());
            common.setGroupMessageMap(new HashMap<>());
            common.setGroupMessageMap2(new HashMap<>());
            return common;
        } catch (TelegramApiException e) {
            log.error("Failed to initialize Common", e);
            throw new RuntimeException("Failed to initialize Common", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        common.setUpdate(update);
        message = update.getMessage();

        if (update.hasMessage()) {
            if (message.hasText()) {
                if (message.isUserMessage()) {
                    handlePrivateMessage();
                    User user = message.getFrom();
                    String userInfo = String.format("[%s] @%s (%s %s)", user.getId(), user.getUserName(),
                            user.getFirstName(), user.getLastName());
                    log.info("[{}] Private message received from {}: {}", this.common.getUsername(), userInfo,
                            this.message.getText());
                }
                if (message.isSuperGroupMessage() || message.isGroupMessage()) {
                    handleGroupMessage();
                }
            }

            if (message.hasPhoto()) {
                handlePhotoMessage();
            }
        }

        if (update.hasCallbackQuery()) {
            User user = update.getCallbackQuery().getFrom();
            String userInfo = String.format("[%s] %s (%s %s)", user.getId(), user.getUserName(), user.getFirstName(),
                    user.getLastName());
            handleCallbackQuery();
            log.info("CallbackQuery Data received from {}: {}", userInfo, update.getCallbackQuery().getData());
        }

        if (update.hasChannelPost()) {
            if (update.getChannelPost().getChat().getType().equals("channel")) {
                handleChannelPost();
            }

        }

        if (update.hasMyChatMember() || update.hasChatMember()) {
            String type = null;
            ChatMember chatMember = null;
            if (update.hasMyChatMember()) {
                type = update.getMyChatMember().getChat().getType();
                chatMember = update.getMyChatMember().getNewChatMember();
            }
            if (update.hasChatMember()) {
                type = update.getChatMember().getChat().getType();
                chatMember = update.getChatMember().getNewChatMember();
            }

            if (chatMember != null && type != null) {
                handleChatMemberUpdate(chatMember, type);
            }

        }
    }

    protected abstract void handlePrivateMessage();

    protected abstract void handleGroupMessage();

    protected abstract void handlePhotoMessage();

    protected abstract void handleCallbackQuery();

    protected abstract void handleChannelPost();

    protected abstract void handleChatMemberUpdate(ChatMember chatMember, String type);

    @Override
    public String getBotToken() {
        return this.dto.getToken();
    }

    @Override
    public String getBotUsername() {
        return this.dto.getUsername();
    }
}
