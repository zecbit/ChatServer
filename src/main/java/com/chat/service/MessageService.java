package com.chat.service;

import com.chat.entity.UserFactory;
import com.chat.entity.Users;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by zec on 2016/9/23.
 */
public class MessageService {
    private ResourceBundle bundle;

    public MessageService(){
        bundle = ResourceBundle.getBundle("messages");
    }
    public String getText(String key){
        return bundle.getString(key);
    }

}
