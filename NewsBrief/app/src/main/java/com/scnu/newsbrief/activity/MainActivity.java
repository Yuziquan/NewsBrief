package com.scnu.newsbrief.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.scnu.newsbrief.R;
import com.scnu.newsbrief.adapter.MyFragmentPagerAdapter;
import com.scnu.newsbrief.fragment.ForumFragment;
import com.scnu.newsbrief.fragment.HomepageFragment;
import com.scnu.newsbrief.fragment.MessageFragment;
import com.scnu.newsbrief.fragment.MineFragment;
import com.scnu.newsbrief.network.SendMessageManager;
import com.scnu.newsbrief.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bottom_navigation_bar)
    protected BottomNavigationBar mBottomNavigationBar;

    @BindView(R.id.view_pager)
    protected ViewPager mViewPager;

    private List<Fragment> mFragmentList;

    private int mCurFragmentIndex;


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SendMessageManager.getInstance().getNews();
        initTransparentStatusBar();

        ButterKnife.bind(this);

        initView();
    }


    /**
     * 初始化透明状态栏
     */
    private void initTransparentStatusBar() {
        // 实现透明状态栏效果
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();

            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            decorView.setSystemUiVisibility(option);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }


    /**
     * 界面初始化
     */
    private void initView() {
        initBottomNavigationBar();
        initViewPager();
    }


    /**
     * 初始化底部导航栏
     */
    private void initBottomNavigationBar() {
        mBottomNavigationBar.clearAll();

        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setActiveColor(R.color.home_pager_bar_color);

        TextBadgeItem messageBadgeItem = new TextBadgeItem().setBorderWidth(4).setAnimationDuration(200).setBackgroundColor(Color.RED).setText("3").setHideOnSelect(true);

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.homepage, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.forum, "论坛"))
                .addItem(new BottomNavigationItem(R.drawable.message, "消息").setBadgeItem(messageBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.mine, "我的")).
                setFirstSelectedPosition(0).initialise();


        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                mViewPager.setCurrentItem(position);
                mCurFragmentIndex = position;
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }


    /**
     * 初始化翻页器
     */
    private void initViewPager() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new HomepageFragment());
        mFragmentList.add(new ForumFragment());
        mFragmentList.add(new MessageFragment());
        mFragmentList.add(new MineFragment());

        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);
                mCurFragmentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
    }


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mCurFragmentIndex == 1) {
            ForumFragment forumFragment = (ForumFragment) mFragmentList.get(mCurFragmentIndex);

            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && (forumFragment.canWebViewGoBack())) {
                forumFragment.webViewGoBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}
