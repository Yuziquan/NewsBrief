package com.scnu.newsbrief.network;

import com.scnu.newsbrief.bean.network.base.BaseResponseInfo;
import com.scnu.newsbrief.bean.network.LoginStatus;
import com.scnu.newsbrief.bean.network.NewsResponseInfo;
import com.scnu.newsbrief.bean.network.RegisterStatus;
import com.scnu.newsbrief.constant.Constants;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by WuchangI on 2018/11/17.
 */

public class ReceiveMessageManager {
    private static ReceiveMessageManager sReceiveMessageManager;

    public static ReceiveMessageManager getInstance() {
        return sReceiveMessageManager == null ? sReceiveMessageManager = new ReceiveMessageManager() : sReceiveMessageManager;
    }

    private ReceiveMessageManager() {
    }


    /**
     * 分发消息
     *
     * @param baseResponseInfo
     * @param appendUrl
     */
    public void
    dispatchMessage(BaseResponseInfo baseResponseInfo, String appendUrl) {
        switch (appendUrl) {
            case Constants.URL.GET_REGISTER_STATUS:
                RegisterStatus registerStatus = (RegisterStatus) baseResponseInfo;
                EventBus.getDefault().post(registerStatus);
                break;

            case Constants.URL.GET_LOGIN_STATUS:
                LoginStatus loginStatus = (LoginStatus) baseResponseInfo;
                EventBus.getDefault().post(loginStatus);
                break;

            case Constants.URL.GET_NEWS_CONTENT:
                NewsResponseInfo newsResponseInfo = (NewsResponseInfo) baseResponseInfo;
                EventBus.getDefault().post(newsResponseInfo);
                break;

            default:
                break;
        }
    }


}
