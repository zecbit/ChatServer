package com.chat.service;

import com.chat.entity.UserFactory;
import com.chat.entity.Users;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zec on 2016/9/23.
 */
public class UserService{

    public static Users getUsersByChannelHashCode(Channel channel){
        Users user = null;
        user = UserFactory.getUsers().get(channel.hashCode());
        return user;
    }

    public static Users setUsersByChannelHashCode(Channel channel, Users user){
        user = UserFactory.getUsers().put(channel.hashCode(), user);
        return user;
    }

    public List<String> findUsersByRoomName(String roomName){
        List<String> userNames = new ArrayList<String>();
        if(roomName == null || "".equals(roomName.trim())){
            return userNames;
        }
        HashMap<Integer, Users> users = UserFactory.getUsers();
        for(Users user : users.values()){
            if(roomName.equals(user.getCurrentRoom())){
                userNames.add(user.getUsername());
            }
        }
        return userNames;
    }

    public int countUserNumbersByRoomName(String roomNoom){
        List<String> userNames = this.findUsersByRoomName(roomNoom);
        return userNames.size();
    }
}
