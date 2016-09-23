package com.chat.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zec on 2016/9/23.
 */
public class RoomFactory {

    private static Set<String> roomNames;

    public synchronized static Set<String> getRoomNames() {
        if(roomNames == null){
            roomNames = new HashSet<String>();
        }
        return roomNames;
    }

}
