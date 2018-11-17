package com.scnu.newsbrief.network;

import com.scnu.newsbrief.constant.Constants;
import com.scnu.newsbrief.entity.network.LoginResponseInfo;
import com.scnu.newsbrief.entity.network.NewsResponseInfo;
import com.scnu.newsbrief.entity.network.RegisterResponseInfo;
import io.reactivex.Observable;

/**
 * Created by WuchangI on 2018/11/17.
 */

public class SendMessageManager
{
    private static SendMessageManager sendMessageManager;

    private HttpChannel httpChannel;

    private ApiService apiService;

    public static SendMessageManager getInstance()
    {
        return sendMessageManager == null ? sendMessageManager = new SendMessageManager() : sendMessageManager;
    }

    private SendMessageManager()
    {
        httpChannel = HttpChannel.getInstance();
        apiService = httpChannel.getApiService();
    }

    /**
     * 发送“获取用户登录状态”的消息
     * @param email
     * @param password
     */
    public void getLoginStatus(String email, String password)
    {
        Observable<LoginResponseInfo> observable = apiService.getLoginStatus(email, password);
        httpChannel.sendMessage(observable, Constants.GET_LOGIN_STATUS_URL);
    }


    /**
     * 发送“获取用户注册状态”的消息
     * @param username
     * @param email
     * @param password
     */
    public void getRegisterStatus(String username, String email, String password)
    {
        Observable<RegisterResponseInfo> observable = apiService.getRegisterStatus(username, email, password);
        httpChannel.sendMessage(observable, Constants.GET_REGISTER_STATUS_URL);
    }

    /**
     * 发送“获取新闻列表”的消息
     */
    public void getNews()
    {
        Observable<NewsResponseInfo> observable = apiService.getNews();
        httpChannel.sendMessage(observable, Constants.GET_NEWS_URL);
    }


}
