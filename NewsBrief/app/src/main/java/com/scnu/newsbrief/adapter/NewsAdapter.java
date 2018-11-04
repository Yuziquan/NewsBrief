package com.scnu.newsbrief.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.scnu.newsbrief.R;
import com.scnu.newsbrief.entity.News;
import com.scnu.newsbrief.main_interface.MainInterfaceActivity;
import com.scnu.newsbrief.main_interface.homepage.activity.FullContentActivity;
import com.scnu.newsbrief.main_interface.homepage.activity.NewsBriefActivity;

import java.util.List;


/**
 * Created by lw on 2017/4/14.
 */


public class NewsAdapter extends ArrayAdapter
{
    private final int resourceId;

    private Context context;

    public NewsAdapter(Context context, int textViewResourceId, List<News> objects)
    {

        super(context, textViewResourceId, objects);

        this.context = context;

        resourceId = textViewResourceId;

    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent)
    {

        News news = (News) getItem(position); // 获取当前项的Fruit实例

        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

        ImageView SourceImage = (ImageView) view.findViewById(R.id.source_image);//获取该布局内的图片视图

        TextView SourceName = (TextView) view.findViewById(R.id.source_name);//获取该布局内的文本视图

        TextView Newscontent = (TextView) view.findViewById(R.id.news_content);


        Newscontent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int result = position % 6;

                String content;

                switch(result)
                {
                    case 0:
                        content = context.getResources().getString(R.string.news1);

                        break;

                    case 1:
                        content = context.getResources().getString(R.string.news2);

                        break;
                    case 2:
                        content = context.getResources().getString(R.string.news3);


                        break;
                    case 3:
                        content = context.getResources().getString(R.string.news4);

                        break;
                    case 4:
                        content = context.getResources().getString(R.string.news5);

                        break;
                    case 5:
                        content = context.getResources().getString(R.string.news6);

                        break;

                    default: content = "";

                }

                FullContentActivity.actionStart(context, content);
            }
        });

        Button getdigest = (Button) view.findViewById(R.id.getdigest);
        SourceImage.setImageResource(news.getImageId());//为图片视图设置图片资源

        SourceName.setText(news.getSourcename());//为文本视图设置文本内容

        Newscontent.setText(news.getContent());

        getdigest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                int result = position % 6;

                String brief;

                switch(result)
                {
                    case 0:
                        brief = context.getResources().getString(R.string.news1_brief);

                        break;

                    case 1:
                        brief = context.getResources().getString(R.string.news2_brief);

                        break;
                    case 2:
                        brief = context.getResources().getString(R.string.news3_brief);


                        break;
                    case 3:
                        brief = context.getResources().getString(R.string.news4_brief);

                        break;
                    case 4:
                        brief = context.getResources().getString(R.string.news5_brief);

                        break;
                    case 5:
                        brief = context.getResources().getString(R.string.news6_brief);

                        break;

                    default: brief = "";

                }


                NewsBriefActivity.actionStart(context, brief);
            }
        });


        return view;

    }

}
