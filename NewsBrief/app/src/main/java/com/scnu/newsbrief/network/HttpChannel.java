package com.scnu.newsbrief.network;

import android.util.Log;

import com.scnu.newsbrief.constant.Constants;
import com.scnu.newsbrief.bean.network.base.BaseResponseInfo;
import com.scnu.newsbrief.utils.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WuchangI on 2018/11/17.
 */

public class HttpChannel {
    private static HttpChannel sHttpChannel;

    private ApiService mApiService;

    public static HttpChannel getInstance() {
        return sHttpChannel == null ? sHttpChannel = new HttpChannel() : sHttpChannel;
    }

    private HttpChannel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(RetrofitUtils.getOkHttpClientWithLoggingInterceptor())
                .build();

        mApiService = retrofit.create(ApiService.class);
    }


    public ApiService getApiService() {
        return mApiService;
    }

    /**
     * 发送消息
     *
     * @param observable 被观察者
     * @param appendUrl  在BASE_REQUEST_URL后面添加的部分URL
     */
    public void sendMessage(Observable<? extends BaseResponseInfo> observable, String appendUrl) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponseInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponseInfo baseResponseInfo) {
                        Log.i("返回的数据：", baseResponseInfo.toString());
                        ReceiveMessageManager.getInstance().dispatchMessage(baseResponseInfo, appendUrl);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
