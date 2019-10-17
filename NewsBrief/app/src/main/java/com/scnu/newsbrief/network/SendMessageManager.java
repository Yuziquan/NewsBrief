package com.scnu.newsbrief.network;

import com.scnu.newsbrief.constant.Constants;
import com.scnu.newsbrief.bean.network.LoginStatus;
import com.scnu.newsbrief.bean.network.NewsResponseInfo;
import com.scnu.newsbrief.bean.network.RegisterStatus;

import io.reactivex.Observable;

/**
 * Created by WuchangI on 2018/11/17.
 */

public class SendMessageManager {
    private static SendMessageManager sSendMessageManager;

    private HttpChannel mHttpChannel;

    private ApiService mApiService;

    public static SendMessageManager getInstance() {
        return sSendMessageManager == null ? sSendMessageManager = new SendMessageManager() : sSendMessageManager;
    }

    private SendMessageManager() {
        mHttpChannel = HttpChannel.getInstance();
        mApiService = mHttpChannel.getApiService();
    }


    public void getRegisterStatus(String username, String email, String password) {
        Observable<RegisterStatus> observable = mApiService.getRegisterStatus(username, email, password);
        mHttpChannel.sendMessage(observable, Constants.URL.GET_REGISTER_STATUS);
    }

    public void getLoginStatus(String email, String password) {
        Observable<LoginStatus> observable = mApiService.getLoginStatus(email, password);
        mHttpChannel.sendMessage(observable, Constants.URL.GET_LOGIN_STATUS);
    }


    public void getNews() {
        Observable<NewsResponseInfo> observable = mApiService.getNews();
        mHttpChannel.sendMessage(observable, Constants.URL.GET_NEWS_CONTENT);
    }


}
