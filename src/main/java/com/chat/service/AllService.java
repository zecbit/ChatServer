package com.chat.service;

/**
 * Created by zec on 2016/9/23.
 */
public class AllService {
    private static final ChatService chatService = new ChatService();
    private static final ExceptionService exceptionService = new ExceptionService();
    private static final UserService userService = new UserService();
    private static final MessageService messageService = new MessageService();
    private static final ConfigService configService = new ConfigService();

    public static UserService getUserService() {
        return userService;
    }

    public  static ChatService getChatService() {
        return chatService;
    }

    public  static ExceptionService getExceptionService() {
        return exceptionService;
    }

    public static MessageService getMessageService() {
        return messageService;
    }

    public static ConfigService getConfigService() {
        return configService;
    }
}
