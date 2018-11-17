package com.scnu.newsbrief.main_interface.message;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.scnu.newsbrief.R;
import com.scnu.newsbrief.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WuchangI on 2018/10/31.
 */


/**
 * “消息” 页面
 */
public class MessageFragment extends BaseFragment
{
    private static final String TAG = "MessageFragment";

    private Unbinder mUnbinder;

    @BindView(R.id.lv_message)
    protected ListView mLvMessageList;


    @BindView(R.id.tv_message)
    protected TextView mTvMessage;

    private Typeface mTypeface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.message_fragment, container, false);

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
            mTvMessage.setTypeface(mTypeface);
        }

        initView();

        return rootView;
    }


    private void initView()
    {
        int[] imageIdList = new int[]{R.drawable.message_item_broadcast, R.drawable.message_item_hotspot, R.drawable.message_item_like};

        String[] titleList = new String[]{"系统通知", "热点话题", "我关注的"};

        String[] textList = new String[]{
                "系统已发布最新版本，欢迎升级体验！",
                "身边的人都在聊\"安倍晋三为何定在10月25日访华?\"",
                "亲爱的 Happy Dog，您关注的话题 \"重庆公交坠江监控曝光\" 已更新~~",
        };

        List<Map<String, Object>> messageItemsList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < imageIdList.length; i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imageIdList[i]);
            map.put("title", titleList[i]);
            map.put("text", textList[i]);

            messageItemsList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), messageItemsList, R.layout.
                message_item, new String[]{"image", "title", "text"}, new int[]{R.id.image, R.id.title, R.id.text});

        mLvMessageList.setAdapter(adapter);

    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mUnbinder.unbind();
    }

}
