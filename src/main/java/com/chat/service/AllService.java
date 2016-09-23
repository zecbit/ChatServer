package com.chat.service;

/**
 * Created by zec on 2016/9/23.
 */
public class AllService {
    private static final MessageHandler messageService = new MessageHandler();
    private static final ExceptionHandler exceptionService = new ExceptionHandler();
    private static final UserService userService = new UserService();

    public static UserService getUserService() {
        return userService;
    }

    public  static MessageHandler getMessageService() {
        return messageService;
    }

    public  static ExceptionHandler getExceptionService() {
        return exceptionService;
    }

}
