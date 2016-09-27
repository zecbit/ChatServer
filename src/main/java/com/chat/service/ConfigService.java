package com.chat.service;

import java.util.ResourceBundle;

/**
 * Created by zec on 2016/9/23.
 */
public class ConfigService {

    static final int PORT = Integer.parseInt(System.getProperty("port", "9399"));
    static final String HOST = System.getProperty("host", "114.115.200.173");

    private ResourceBundle bundle;

    public ConfigService(){
        bundle = ResourceBundle.getBundle("config");
    }
    public String getText(String key){
        return bundle.getString(key);
    }

    public int getMaxTextLength(){
        String maxTextLengthStr = AllService.getConfigService().getText("max.text.length");
        int maxTextLength = 8192;
        if(maxTextLengthStr != null){
            maxTextLength = Integer.parseInt(maxTextLengthStr);
        }
        return maxTextLength;
    }

    public int getServerPort(){
        String configPort = AllService.getConfigService().getText("server.port");
        int port = -1;
        if(configPort == null){
            port = PORT;
        }
        else{
            port = Integer.parseInt(configPort);
        }
        return port;
    }

    public String getServerHost(){
        String configHost = AllService.getConfigService().getText("server.host");
        String host = "127.0.0.1";
        if(configHost == null){
            host = HOST;
        }
        else{
            host = configHost;
        }
        return host;
    }
}
