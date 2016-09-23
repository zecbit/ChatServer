package com.chat.entity;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zec on 2016/9/23.
 */
public class UserFactory {

    private static HashMap<Integer, Users> users;
    private static Set<String> userNames;

    public synchronized static HashMap<Integer, Users> getUsers() {
        if(users == null){
            users = new HashMap<Integer, Users>();
        }
        return users;
    }

    public static Users getUsersByChannelHashCode(Channel channel){
        Users user = null;
        user = UserFactory.getUsers().get(channel.hashCode());
        return user;
    }

    public static Users setUsersByChannelHashCode(Channel channel, Users user){
        user = UserFactory.getUsers().put(channel.hashCode(), user);
        return user;
    }

    public synchronized static Set<String> getUserNames() {
        if(userNames == null){
            userNames = new HashSet<String>();
        }
        return userNames;
    }

    public static void setUserNames(Set<String> userNames) {
        UserFactory.userNames = userNames;
    }
}
