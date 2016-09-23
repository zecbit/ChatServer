package com.chat.service;

/**
 * Created by zec on 2016/9/23.
 */
public class AllService {
    private MessageHandler messageService;
    private ExceptionHandler exceptionService;

    public MessageHandler getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageHandler messageService) {
        this.messageService = messageService;
    }

    public ExceptionHandler getExceptionService() {
        return exceptionService;
    }

    public void setExceptionService(ExceptionHandler exceptionService) {
        this.exceptionService = exceptionService;
    }
}
