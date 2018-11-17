package com.scnu.newsbrief.network;

import com.scnu.newsbrief.entity.network.LoginResponseInfo;
import com.scnu.newsbrief.entity.network.NewsResponseInfo;
import com.scnu.newsbrief.entity.network.RegisterResponseInfo;
import io.reactivex.Observable;
import retrofit2.http.*;


/**
 * Created by WuchangI on 2018/11/17.
 */

public interface ApiService
{
    /**
     * 获取用户登录状态
     * @param email
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("login.php")
    Observable<LoginResponseInfo> getLoginStatus(@Field("email") String email, @Field("password") String password);

    /**
     * 获取用户注册状态
     * @param username
     * @param email
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("register.php")
    Observable<RegisterResponseInfo> getRegisterStatus(@Field("name") String username, @Field("email") String email,
                                                       @Field("password") String password);

    /**
     * 获取新闻列表
     * @return
     */
    @GET("newsContent.php")
    Observable<NewsResponseInfo> getNews();

}
