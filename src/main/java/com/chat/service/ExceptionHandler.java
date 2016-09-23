package com.chat.service;

import com.chat.server.SecureChatServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zec on 2016/9/22.
 */
public class ExceptionHandler {

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ALL_SERVICE.MESSAGE_HANDLER.sendMessageToSpecifiedClient(ctx, "Your message is too long");
    }
}