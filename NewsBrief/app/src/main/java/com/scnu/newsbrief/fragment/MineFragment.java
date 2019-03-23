package com.scnu.newsbrief.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.bumptech.glide.Glide;
import com.scnu.newsbrief.R;
import com.scnu.newsbrief.base.BaseFragment;
import jp.wasabeef.glide.transformations.BlurTransformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by WuchangI on 2018/10/31.
 */

/**
 * "我的" 页面
 */
public class MineFragment extends BaseFragment
{
    @BindView(R.id.iv_head_bg)
    protected ImageView mIvHeadBg;

    @BindView(R.id.lv_personal_information)
    protected ListView mLvPersonalInformation;

    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        initView();

        return rootView;
    }


    private void initView()
    {
        int[] imageIdList = new int[]{R.drawable.collect, R.drawable.history, R.drawable.score, R.drawable.settings};

        String[] titleList = new String[]{"我的收藏", "浏览历史", "我的积分", "设置"};

        List<Map<String, Object>> messageItemsList = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < imageIdList.length; i++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imageIdList[i]);
            map.put("title", titleList[i]);

            messageItemsList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), messageItemsList, R.layout.
                personal_information_item, new String[]{"image", "title"}, new int[]{R.id.image, R.id.title});

        mLvPersonalInformation.setAdapter(adapter);

    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(getActivity().getApplicationContext()).load(R.drawable.head)
                .apply(bitmapTransform(new BlurTransformation(25, 3)))
                .into(mIvHeadBg);
    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
