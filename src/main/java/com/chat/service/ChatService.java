package com.chat.service;

import com.chat.entity.ChannelGroupFactory;
import com.chat.entity.RoomFactory;
import com.chat.entity.UserFactory;
import com.chat.entity.Users;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by zec on 2016/9/22.
 */
public class ChatService {

    public void handle(ChannelHandlerContext ctx, String msg) throws Exception{

        //msg's speaker
        //msg's room
        String currentUserNameOfMsg = null;
        String currentRoomOfMsg = null;
        Channel currentChannelOfMsg = ctx.channel();

        if(msg == null || msg.trim().equals("")){
            Random random = new Random();
            int seed = random.nextInt(3);
            switch(seed){
                case 0: sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.emtpy.0"));break;
                case 1: sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.emtpy.1"));break;
                case 2: sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.emtpy.2"));break;
            }
            return;
        }

        //self command <-> interacting
        if(UserFactory.getUsers().containsKey(ctx.channel().hashCode())){
            Users user = UserFactory.getUsers().get(ctx.channel().hashCode());
            currentUserNameOfMsg = user.getUsername();
            currentRoomOfMsg = user.getCurrentRoom();
            if("/help".equals(msg)){
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("/help"));
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("/rooms"));
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("/create"));
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("/join"));
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("/leave"));
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("/quit"));
                return;
            }
            if("/rooms".equals(msg)){

                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.activeRoom.title"));
                Set<String> rooms = RoomFactory.getRoomNames();
                for(String eachRoomName : rooms){
                    int userNumberOfRoom = AllService.getUserService().countUserNumbersByRoomName(eachRoomName);
                    currentChannelOfMsg.writeAndFlush("* " + eachRoomName + " (" + userNumberOfRoom + ")\n");
                }
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.activeRoom.endOfList"));
                return;
            }
            if(msg.contains("/create")){

                if(user.getCurrentRoom() != null){
                    sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.cannot.dosth"));
                    return;
                }

                String roomName = msg.substring(msg.indexOf(" ")+1);
                RoomFactory.getRoomNames().add(roomName);
                AllService.getUserService().setUsersByChannelHashCode(currentChannelOfMsg, user);
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.room.created.success"));
                return;
            }
            if(msg.contains("/leave")){

                if(user.getCurrentRoom() == null){
                    sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.not.in.the.room"));
                    return;
                }

                user.setCurrentRoom(null);
                AllService.getUserService().setUsersByChannelHashCode(currentChannelOfMsg, user);
            }
            if(msg.contains("/join")){

                if(user.getCurrentRoom() != null){
                    sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.cannot.dosth"));
                    return;
                }

                String roomName = msg.substring(msg.indexOf(" ")+1);
                user.setCurrentRoom(roomName);
                currentRoomOfMsg = roomName;
                AllService.getUserService().setUsersByChannelHashCode(currentChannelOfMsg, user);
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.enter.room") + roomName);

                List<String> userNames = AllService.getUserService().findUsersByRoomName(roomName);
                for(String username : userNames){
                    currentChannelOfMsg.writeAndFlush("* "+username);
                    if(username.equals(currentUserNameOfMsg)){
                        sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.its.you"));
                    }
                    else{
                        currentChannelOfMsg.writeAndFlush("\n");
                    }
                }
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.activeRoom.endOfList"));
                //return;
            }
        }
        else{
            //msg is user name;
            if(UserFactory.getUserNames().contains(msg)){
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.name.taken"));
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.ask.login.name"));
            }
            else{
                Users user = new Users();
                user.setUsername(msg);
                UserFactory.getUserNames().add(user.getUsername());
                AllService.getUserService().setUsersByChannelHashCode(currentChannelOfMsg, user);
                sendMsg(currentChannelOfMsg, AllService.getMessageService().getText("chat.msg.welcome.title") + " "+user.getUsername() + "!");
            }
            return;
        }

        //broadcasting messages
        for (Channel c: ChannelGroupFactory.getChannels()) {
            if (c != ctx.channel()) {
                //Same room
                Users user = AllService.getUserService().getUsersByChannelHashCode(c);
                if(user.getCurrentRoom().equals(currentRoomOfMsg)){

                    if(msg.contains("/join")){
                        sendMsg(c, AllService.getMessageService().getText("chat.msg.new.user.join") + currentUserNameOfMsg);
                        break;
                    }

                    if(msg.contains("/leave")){
                        sendMsg(c, AllService.getMessageService().getText("chat.msg.user.has.left") + currentUserNameOfMsg);
                        break;
                    }

                    c.writeAndFlush("[" + currentUserNameOfMsg + "] " + msg + '\n');
                }
            } else {
                sendMsg(c, AllService.getMessageService().getText("chat.msg.you.title") + msg);
            }
        }

        // Close the connection if the client has sent 'bye'.
        if ("/quit".equals(msg.toLowerCase())) {
            ctx.close();
        }
    }

    public void sendMsg(ChannelHandlerContext ctx, String message){
        StringBuffer line = new StringBuffer(message).append("\n");
        ctx.channel().writeAndFlush(line.toString());
    }

    public void sendMsg(Channel channel, String message){
        StringBuffer line = new StringBuffer(message).append("\n");
        channel.writeAndFlush(line.toString());
    }

}
