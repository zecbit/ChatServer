package com.chat.service;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zec on 2016/9/22.
 */
public class ExceptionHandler extends BaseService {

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        this.getAllService().getMessageService().sendMessageToSpecifiedClient(ctx, "Your message is too long");
    }
}