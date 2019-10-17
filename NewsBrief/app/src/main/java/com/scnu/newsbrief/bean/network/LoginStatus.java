package com.scnu.newsbrief.bean.network;

import com.scnu.newsbrief.bean.network.base.BaseResponseInfo;

/**
 * Created by WuchangI on 2018/11/17.
 */

public class LoginStatus extends BaseResponseInfo {
    /**
     * 登录状态，error = true表示登录失败，error = false 表示登录成功
     */
    private String error;

    public String getError()
    {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
