package com.scnu.newsbrief.main_interface.homepage.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.scnu.newsbrief.R;

public class NewsBriefActivity extends AppCompatActivity
{

    @BindView(R.id.tv_news_brief)
    protected TextView mTvNewsBrief;


    @BindView(R.id.toolbar2)
    protected Toolbar toolbar2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brief);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar2);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("新闻摘要");
        }




        String brief = getIntent().getStringExtra("brief");
        mTvNewsBrief.setText(brief);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:

                // 返回上一个Activity
                finish();
                break;

            default:
        }

        return true;
    }


    public static void actionStart(Context context, String newsBrief)
    {
        Intent intent = new Intent(context, NewsBriefActivity.class);

        intent.putExtra("brief", newsBrief);

        context.startActivity(intent);
    }

}
