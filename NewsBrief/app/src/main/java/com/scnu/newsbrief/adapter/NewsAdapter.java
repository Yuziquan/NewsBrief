package com.scnu.newsbrief.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.scnu.newsbrief.R;
import com.scnu.newsbrief.constant.Constants;
import com.scnu.newsbrief.activity.FullContentActivity;
import com.scnu.newsbrief.activity.NewsBriefActivity;
import com.scnu.newsbrief.bean.network.NewsResponseInfo;

import java.util.List;


/**
 * Created by lw on 2017/4/14.
 */


public class NewsAdapter extends ArrayAdapter {
    private final int resourceId;

    private Context context;

    private int categoryid = 0;

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public NewsAdapter(Context context, int textViewResourceId, List<NewsResponseInfo.NewsContentsBean> objects) {

        super(context, textViewResourceId, objects);

        this.context = context;

        resourceId = textViewResourceId;

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        NewsResponseInfo.NewsContentsBean news = (NewsResponseInfo.NewsContentsBean) getItem(position); // 获取当前项的Fruit实例

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

        ImageView SourceImage = (ImageView) view.findViewById(R.id.source_image);//获取该布局内的图片视图

        Integer integer = Constants.newsfromimg.get(news.getNewFrom());
        if (integer != null) {
            SourceImage.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), integer.intValue()));
        } else {
            SourceImage.setImageResource(R.drawable.tengxun);
        }

        TextView SourceName = (TextView) view.findViewById(R.id.source_name);//获取该布局内的文本视图

        TextView news_time = (TextView) view.findViewById(R.id.news_time);
        news_time.setText(news.getTimes());
        TextView Newscontent = (TextView) view.findViewById(R.id.news_content);


        Newscontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullContentActivity.actionStart(context, news.getUrl());
            }
        });

        Button getdigest = (Button) view.findViewById(R.id.getdigest);
        // SourceImage.setImageResource(news.get);//为图片视图设置图片资源

        if (news.getTitle().length() > 15)
            SourceName.setText(news.getTitle().substring(0, 15) + "...");//为文本视图设置文本内容
        else
            SourceName.setText(news.getTitle());//为文本视图设置文本内容


        if (news.getContent().length() > 100)
            Newscontent.setText(news.getContent().substring(0, 100) + "...");
        else
            Newscontent.setText(news.getContent());

        getdigest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsBriefActivity.actionStart(context, news.getContent());
            }
        });


        return view;

    }

}
