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
public class MessageHandler{

    public void handle(ChannelHandlerContext ctx, String msg) throws Exception{

        //msg's speaker
        //msg's room
        String currentUserNameOfMsg = null;
        String currentRoomOfMsg = null;
        Channel currentChannel = ctx.channel();

        if(msg == null || msg.trim().equals("")){
            Random random = new Random();
            int seed = random.nextInt(3);
            switch(seed){
                case 0:currentChannel.writeAndFlush("Please say something...\n");break;
                case 1:currentChannel.writeAndFlush("You must be kidding.\n");break;
                case 2:currentChannel.writeAndFlush("Aha! You are right here!\n");break;
            }
            return;
        }

        //self command <-> interacting
        if(UserFactory.getUsers().containsKey(ctx.channel().hashCode())){
            Users user = UserFactory.getUsers().get(ctx.channel().hashCode());
            currentUserNameOfMsg = user.getUsername();
            currentRoomOfMsg = user.getCurrentRoom();
            if("/rooms".equals(msg)){
                currentChannel.writeAndFlush("Active rooms are\n");
                Set<String> rooms = RoomFactory.getRoomNames();
                for(String eachRoomName : rooms){
                    int userNumberOfRoom = AllService.getUserService().countUserNumbersByRoomName(eachRoomName);
                    currentChannel.writeAndFlush("* "+eachRoomName+" ("+userNumberOfRoom+")\n");
                }
                currentChannel.writeAndFlush("end of list.\n");
                return;
            }
            if(msg.contains("/create")){

                if(user.getCurrentRoom() != null){
                    currentChannel.writeAndFlush("You are in the chat room. Please leave current room first. \n");
                    return;
                }

                String roomName = msg.substring(msg.indexOf(" ")+1);
                RoomFactory.getRoomNames().add(roomName);
                AllService.getUserService().setUsersByChannelHashCode(currentChannel, user);
                return;
            }
            if(msg.contains("/leave")){

                if(user.getCurrentRoom() == null){
                    currentChannel.writeAndFlush("You are NOT in the chat room. \n");
                    return;
                }

                user.setCurrentRoom(null);
                AllService.getUserService().setUsersByChannelHashCode(currentChannel, user);
            }
            if(msg.contains("/join")){

                if(user.getCurrentRoom() != null){
                    currentChannel.writeAndFlush("You are in the chat room. Please leave current room first. \n");
                    return;
                }

                String roomName = msg.substring(msg.indexOf(" ")+1);
                user.setCurrentRoom(roomName);
                currentRoomOfMsg = roomName;
                AllService.getUserService().setUsersByChannelHashCode(currentChannel, user);

                currentChannel.writeAndFlush("entering room: "+roomName+"\n");
                List<String> userNames = AllService.getUserService().findUsersByRoomName(roomName);
                for(String username : userNames){
                    currentChannel.writeAndFlush("* "+username);
                    if(username.equals(currentUserNameOfMsg)){
                        currentChannel.writeAndFlush("(** this is you)\n");
                    }
                    else{
                        currentChannel.writeAndFlush("\n");
                    }
                }
                currentChannel.writeAndFlush("end of list.\n");
                //return;
            }
        }
        else{
            //msg is user name;
            if(UserFactory.getUserNames().contains(msg)){
                currentChannel.writeAndFlush("Sorry, name taken.\n");
                currentChannel.writeAndFlush("Login Name?\n");
            }
            else{
                Users user = new Users();
                user.setUsername(msg);
                UserFactory.getUserNames().add(user.getUsername());
                AllService.getUserService().setUsersByChannelHashCode(currentChannel, user);
                currentChannel.writeAndFlush("Welcome " + user.getUsername() + "!\n");
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
                        c.writeAndFlush("* new user joined chat: "+currentUserNameOfMsg+ '\n');
                        break;
                    }

                    if(msg.contains("/leave")){
                        c.writeAndFlush("* user has left chat: "+currentUserNameOfMsg+ '\n');
                        break;
                    }

                    c.writeAndFlush("[" + currentUserNameOfMsg + "] " + msg + '\n');
                }
            } else {
                c.writeAndFlush("[you] " + msg + '\n');
            }
        }

        // Close the connection if the client has sent 'bye'.
        if ("bye".equals(msg.toLowerCase())) {
            ctx.close();
        }
    }

    public void sendMessageToSpecifiedClient(ChannelHandlerContext ctx, String message){
        StringBuffer line = new StringBuffer(message).append("\n");
        for (Channel c: ChannelGroupFactory.getChannels()) {
            if (c == ctx.channel()) {
                c.writeAndFlush(line.toString());
            }
        }
    }

}
