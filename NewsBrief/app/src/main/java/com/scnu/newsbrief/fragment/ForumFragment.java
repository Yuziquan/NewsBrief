package com.scnu.newsbrief.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.scnu.newsbrief.R;
import com.scnu.newsbrief.base.BaseFragment;
import com.scnu.newsbrief.constant.Constants;

/**
 * Created by WuchangI on 2018/10/31.
 */

/**
 * “论坛” 页面
 */
public class ForumFragment extends BaseFragment
{
    private static final String TAG = "ForumFragment";

    @BindView(R.id.wv_forum)
    protected WebView mWvForum;

    @BindView(R.id.tv_forum)
    protected TextView mTvForum;

    private Unbinder mUnbinder;

    private Typeface mTypeface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_forum, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        try
        {
            mTypeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font1.ttf");
        }
        catch (Exception e)
        {
            Log.i(TAG, "加载第三方字体失败。");
            mTypeface = null;
        }

        if (mTypeface != null)
        {
            mTvForum.setTypeface(mTypeface);
        }

        initWebView();

        return rootView;
    }


    private void initWebView()
    {
        mWvForum.getSettings().setJavaScriptEnabled(true);
        mWvForum.setWebViewClient(new WebViewClient());
        mWvForum.loadUrl(Constants.FORUM_URL);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mUnbinder.unbind();
    }


    public boolean canWebViewGoBack()
    {
        return mWvForum.canGoBack();
    }

    public void webViewGoBack()
    {
        mWvForum.goBack();
    }

}

