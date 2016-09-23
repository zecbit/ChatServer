package com.chat.service;

/**
 * Created by zec on 2016/9/23.
 */
public class BaseService {
    private AllService allService;

    public AllService getAllService() {
        return allService;
    }

    public void setAllService(AllService allService) {
        this.allService = allService;
    }
}
