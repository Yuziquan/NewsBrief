package com.scnu.newsbrief.activity;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.scnu.newsbrief.R;
import com.scnu.newsbrief.entity.network.LoginResponseInfo;
import com.scnu.newsbrief.entity.network.RegisterResponseInfo;
import com.scnu.newsbrief.network.SendMessageManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends AppCompatActivity
{
    @BindView(R.id.et_user_name)
    protected EditText mEtUserName;

    @BindView(R.id.et_password)
    protected EditText mEtPassword;

    @BindView(R.id.bt_enter)
    protected Button mBtnEnter;

    @BindView(R.id.cv_login)
    protected CardView mCvLogin;

    @BindView(R.id.fab)
    protected FloatingActionButton mFab;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initTransparentStatusBar();

        setListener();
        EventBus.getDefault().register(this);

    }



    /**
     * 初始化透明状态栏
     */
    private void initTransparentStatusBar()
    {
        //实现透明状态栏效果
        if (Build.VERSION.SDK_INT >= 21)
        {
            View decorView = getWindow().getDecorView();

            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            decorView.setSystemUiVisibility(option);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }


        if (getSupportActionBar() != null)
        {
            getSupportActionBar().hide();
        }

    }



    private void setListener()
    {
        mBtnEnter.setOnClickListener(new View.OnClickListener()
        {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view)
            {


                SendMessageManager.getInstance().getLoginStatus(mEtUserName.getText().toString(),mEtPassword.getText().toString());

            }
        });
        mFab.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view)
            {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, mFab, mFab.getTransitionName());
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class), options.toBundle());
            }
        });
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        mFab.setVisibility(View.GONE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mFab.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LoginResponseInfo messageEvent) {
        //Toast.makeText(this, messageEvent.getError(), Toast.LENGTH_SHORT).show();
        if (messageEvent.getError().equals("true")){
            Toast.makeText(this, "登录成功，欢迎！", Toast.LENGTH_SHORT).show();

            Explode explode = new Explode();
            explode.setDuration(500);

            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);

            Intent i2 = new Intent(LoginActivity.this, MainInterfaceActivity.class);
            startActivity(i2, oc2.toBundle());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
