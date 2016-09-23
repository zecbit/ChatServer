package com.chat.entity;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by zec on 2016/9/23.
 */
public class ChannelGroupFactory {

    private static ChannelGroup channels;

    public synchronized static ChannelGroup getChannels() {
        if(channels == null){
            channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        }
        return channels;
    }
}
