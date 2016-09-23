package com.chat.service;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zec on 2016/9/22.
 */
public class ExceptionService{

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        AllService.getChatService().sendMsg(ctx, "Your message is too long");
    }
}