package com.scnu.newsbrief.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.scnu.newsbrief.bean.biz.Channel;


/**
 * 水平滚动条
 * <p>
 *
 * @author liuyk
 */
public class MyHorizontalNavigationBar extends HorizontalNavigationBar<Channel>
{
    public MyHorizontalNavigationBar(Context paramContext)
    {
        super(paramContext);
    }

    public MyHorizontalNavigationBar(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public MyHorizontalNavigationBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @Override
    public void renderingItemView(HorizontalNavigationItemView itemView, int index, int currentPosition)
    {
        Channel channel = getItem(index);
        itemView.setChannelTitle(channel.getChannelName());
        itemView.setChecked(index == currentPosition);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }
}
