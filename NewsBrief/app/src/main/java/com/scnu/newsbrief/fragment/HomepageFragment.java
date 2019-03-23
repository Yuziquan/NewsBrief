package com.scnu.newsbrief.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.scnu.newsbrief.R;
import com.scnu.newsbrief.activity.MainInterfaceActivity;
import com.scnu.newsbrief.adapter.NewsAdapter;
import com.scnu.newsbrief.base.BaseFragment;
import com.scnu.newsbrief.constant.Constants;
import com.scnu.newsbrief.entity.homepage.Channel;
import com.scnu.newsbrief.entity.homepage.News;
import com.scnu.newsbrief.activity.SearchPageActivity;
import com.scnu.newsbrief.entity.network.NewsResponseInfo;
import com.scnu.newsbrief.widget.HorizontalNavigationBar;
import com.scnu.newsbrief.widget.MyHorizontalNavigationBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by WuchangI on 2018/10/31.
 */

/**
 * “首页” 页面
 */
public class HomepageFragment extends BaseFragment implements HorizontalNavigationBar.OnHorizontalNavigationSelectListener
{
    //新闻分类水平滑动导航条
    private MyHorizontalNavigationBar mHorizontalNavigationBar;
    //顶部水平导航条新闻分类
    private String[] newclass = new String[]{"热点", "军事", "汽车", "娱乐", "财经", "其他"};

    private Handler refreshhandler;
    //新闻列表，listview子项
    //private List<News> newsList = new ArrayList<News>();

    //viewpager的每一页
    private ArrayList<View> mFragments;
    private ViewPager viewPager;
    private Myadapter viewpageradapter;

    //记录新闻分类的种类数
    private int pagenum = 0;

    private SmartRefreshLayout refreshLayout;

    private TextView edt_search;
    private ImageView addnewsclass;
    //记录的是所有新闻
    List<NewsResponseInfo.NewsContentsBean> newsContents;
    //分好类的全部新闻
    public static Vector<List<NewsResponseInfo.NewsContentsBean>> classifynewsContents=new Vector<>();
    //显示的新闻，一个向量，每个元素是一个列表，每个列表都是一类新闻
    Vector<List<NewsResponseInfo.NewsContentsBean>> newscontentsdisplay=new Vector<>();
    //每一页的listview的适配器
    private List<NewsAdapter> newsAdapters=new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (!EventBus.getDefault().isRegistered(this))
        EventBus.getDefault().register(this);
        View rootView = inflater.inflate(R.layout.fragment_homepage, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initpage();
        initNews();
    }

    //初始化不同新闻类别的每一页
    private void initpage()
    {
        for (int i = 0; i < pagenum; i++)
        {
            View view = getLayoutInflater().inflate(R.layout.content_page_homepage, null);
            refreshLayout=view.findViewById(R.id.refresh_news);
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    pudownrefresh();
                    refreshlayout.finishRefresh();
                }
            });
            refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    loadmorenews();
                    refreshlayout.finishLoadmore();
                }
            });
            mFragments.add(view);

        }
    }


    private void initView(View view)
    {

        newsContents=new LinkedList<>();
        addnewsclass = (ImageView) view.findViewById(R.id.addnewclass);
        pagenum = newclass.length;
        //点击搜索事件
        edt_search = (TextView) view.findViewById(R.id.search);
        edt_search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity().getApplicationContext(), SearchPageActivity.class);
                getActivity().getApplicationContext().startActivity(intent);
            }
        });
        //顶部水平导航条
        mHorizontalNavigationBar = (MyHorizontalNavigationBar) view.findViewById(R.id.horizontal_navigation);
        mHorizontalNavigationBar.setChannelSplit(true);//需要设置在数据之前
        mHorizontalNavigationBar.setItems(getData());
        mHorizontalNavigationBar.addOnHorizontalNavigationSelectListener(this);
        mHorizontalNavigationBar.setCurrentChannelItem(0);
//listview适配器
        newscontentsdisplay=new Vector<>();
        for (int i=0;i<pagenum;i++){
            classifynewsContents.add(new LinkedList<NewsResponseInfo.NewsContentsBean>());
            newscontentsdisplay.add(new LinkedList<NewsResponseInfo.NewsContentsBean>());
        }
        viewPager = (ViewPager) view.findViewById(R.id.contentpage);
        mFragments = new ArrayList<>();
        refreshhandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                pudownrefresh();
            }
        };

    }

    //返回顶部导航条新闻分类数据
    private ArrayList<Channel> getData()
    {
        final ArrayList<Channel> items = new ArrayList<>();


        for (int i = 0; i < pagenum; i++)
        {
            final Channel channel = new Channel();
            channel.setChannelName(newclass[i]);

            items.add(channel);
        }

        return items;
    }

    //点击顶部新闻分类导航条
    @Override
    public void select(int index)
    {
        Toast.makeText(getActivity().getApplicationContext(), "您点击的是: " + newclass[index], Toast.LENGTH_SHORT).show();
        viewPager.setCurrentItem(index);

    }

    private void initNews()
    {
        for (int i=0;i<6;i++){
            SharedPreferences share=getContext().getSharedPreferences("news"+i, Activity.MODE_WORLD_READABLE);
            NewsResponseInfo.NewsContentsBean temp=new NewsResponseInfo.NewsContentsBean();
            temp.setTitle(share.getString("title","暂无数据"));
            temp.setContent(share.getString("content","暂无数据"));
            temp.setUrl(share.getString("url","暂无数据"));
            temp.setTimes(share.getString("time","暂无数据"));
            temp.setCategory(share.getString("category","暂无数据"));
            temp.setNewFrom(share.getString("newFrom","暂无数据"));
            for (int j=0;j<pagenum;j++){
                newscontentsdisplay.get(j).add(temp);
            }
            //viewpageradapter.notifyDataSetChanged();
        }

        viewpageradapter=new Myadapter();
        viewPager.setAdapter(viewpageradapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                mHorizontalNavigationBar.setCurrentChannelItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(NewsResponseInfo messageEvent) {
        //Toast.makeText(this, messageEvent.getError(), Toast.LENGTH_SHORT).show();
        if (messageEvent.getCode().equals("0")){
            //Toast.makeText(this, "请求数据失败", Toast.LENGTH_SHORT).show();
            return;
        }
if (Constants.newsContents!=null){
            newsContents=Constants.newsContents;
            classifynewsContents=Constants.classifynewsContents;
            newscontentsdisplay=Constants.newscontentsdisplay;
    viewpageradapter.notify();
    return;
}


        newsContents=messageEvent.getNewsContents();
        for (int i=0;i<6;i++){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("news"+i, Context.MODE_PRIVATE); //私有数据
            NewsResponseInfo.NewsContentsBean temp=newsContents.get(i);
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
            editor.putString("title",temp.getTitle());
            editor.putString("content", temp.getContent());
            editor.putString("url", temp.getUrl());
            editor.putString("time", temp.getTimes());
            editor.putString("category", temp.getCategory());
            editor.putString("newFrom",temp.getNewFrom());
            editor.commit();//提交修改
        }

        for (int i=0;i<newsContents.size();i++){
            String category=newsContents.get(i).getCategory();
            int j=0;
            for (;j<pagenum;j++){
                if (category.equals(newclass[j])){
                    classifynewsContents.get(j).add(newsContents.get(i));
                    break;
                }
            }
            if (j==pagenum){
                classifynewsContents.get(pagenum-1).add(newsContents.get(i));
            }

        }
        for (int i=0;i<pagenum;i++){
            newscontentsdisplay.get(i).clear();
            for (int j=0;j<6;j++){
                NewsResponseInfo.NewsContentsBean temp=classifynewsContents.get(i).get(j);
                //classifynewsContents.get(i).add(temp);
                newscontentsdisplay.get(i).add(temp);
            }
        }


       for (int i=0;i<pagenum;i++){
           ListView listView = (ListView) mFragments.get(i).findViewById(R.id.list_view);
           NewsAdapter newsAdapter=new NewsAdapter(this.getActivity(), R.layout.news_item_homepage, newscontentsdisplay.get(i));
           newsAdapter.setCategoryid(i);
           newsAdapters.add(newsAdapter);
           listView.setAdapter(newsAdapter);
       }

        //adapter.notify();
        viewpageradapter.notify();

        Constants.newsContents=newsContents;
        Constants.classifynewsContents=classifynewsContents;
        Constants.newscontentsdisplay=newscontentsdisplay;
    }

    //下拉刷新
    private void pudownrefresh() {
        if (newsContents.size()==0)return;
        for (int n=0;n<pagenum;n++) {
            newscontentsdisplay.get(n).clear();
            for (int j = 0; j < 6; j++) {
                int no=(int) (Math.random()*classifynewsContents.get(n).size()-1);
                NewsResponseInfo.NewsContentsBean temp = classifynewsContents.get(n).get(no);
                //classifynewsContents.get(n).add(temp);
                newscontentsdisplay.get(n).add(temp);
            }
        }
        Log.d("","有刷新");
        //Constants.classifynewsContents=classifynewsContents;
        viewpageradapter.notifyDataSetChanged();
    }
//上拉加载更多
    private void loadmorenews() {
        if (newsContents.size()==0)return;
        for (int n=0;n<pagenum;n++) {
            for (int j = newscontentsdisplay.size(); j <newscontentsdisplay.size()+ 6; j++) {
                NewsResponseInfo.NewsContentsBean temp = classifynewsContents.get(n).get(j);
                newscontentsdisplay.get(n).add(temp);
            }
        }
        viewpageradapter.notifyDataSetChanged();
    }


    //viewpage适配器，mFragments
    private class Myadapter extends PagerAdapter
    {
        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View v = mFragments.get(position);

            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            View v = mFragments.get(position);
            container.removeView(v);
        }

        @Override
        public int getCount()
        {
            return mFragments.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;//官方也是这样写的
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}

