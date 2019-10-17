package com.scnu.newsbrief.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.scnu.newsbrief.R;
import com.scnu.newsbrief.bean.network.RegisterStatus;
import com.scnu.newsbrief.network.SendMessageManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class RegisterActivity extends AppCompatActivity
{
    @BindView(R.id.fab)
    protected FloatingActionButton mFab;

    @BindView(R.id.cv_register)
    protected CardView mCvRegister;

    @BindView(R.id.et_username)
    protected EditText et_username;

    @BindView(R.id.et_password)
    protected EditText et_password;

    @BindView(R.id.et_repeatpassword)
    protected EditText et_repeatpassword;

    @BindView(R.id.bt_go)
    protected Button bt_go;

    @BindView(R.id.et_email)
    protected EditText et_email;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        initTransparentStatusBar();

        EventBus.getDefault().register(this);
        ShowEnterAnimation();
        mFab.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v)
            {
                animateRevealClose();
            }
        });

        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_password.getText().toString().equals(et_repeatpassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                SendMessageManager.getInstance().getRegisterStatus(et_username.getText().toString(),
                        et_email.getText().toString(),
                        et_password.getText().toString());
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void ShowEnterAnimation()
    {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener()
        {
            @Override
            public void onTransitionStart(Transition transition)
            {
                mCvRegister.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition)
            {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition)
            {

            }

            @Override
            public void onTransitionPause(Transition transition)
            {

            }

            @Override
            public void onTransitionResume(Transition transition)
            {

            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow()
    {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCvRegister, mCvRegister.getWidth() / 2, 0, mFab.getWidth() / 2, mCvRegister.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation)
            {
                mCvRegister.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose()
    {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mCvRegister, mCvRegister.getWidth() / 2, 0, mCvRegister.getHeight(), mFab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                mCvRegister.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                mFab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }


    /**
     * 初始化透明状态栏
     */
    private void initTransparentStatusBar() {
        //实现透明状态栏效果
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed()
    {
        animateRevealClose();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(RegisterStatus messageEvent) {
        //Toast.makeText(this, messageEvent.getError(), Toast.LENGTH_SHORT).show();
        if (messageEvent.getError().equals("true")){
            Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
            animateRevealClose();
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
