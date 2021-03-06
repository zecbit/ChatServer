package com.chat.server;

/**
 * Created by zec on 2016/9/22.
 */

import com.chat.service.AllService;
import com.chat.entity.ChannelGroupFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Handles a server-side channel.
 */
public class SecureChatServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                new GenericFutureListener<Future<Channel>>() {
                    @Override
                    public void operationComplete(Future<Channel> future) throws Exception {
                        AllService.getChatService().sendMsg(ctx, AllService.getMessageService().getText("welcome.message"));
                        AllService.getChatService().sendMsg(ctx, AllService.getMessageService().getText("chat.msg.ask.login.name"));
                        ChannelGroupFactory.getChannels().add(ctx.channel());
                    }
                });
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Send the received message to all channels but the current one.
        AllService.getChatService().handle(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        AllService.getExceptionService().exceptionCaught(ctx, cause);
    }

}
