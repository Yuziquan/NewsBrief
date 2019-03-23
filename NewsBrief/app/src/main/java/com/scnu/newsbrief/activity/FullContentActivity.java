package com.scnu.newsbrief.activity;

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

public class FullContentActivity extends AppCompatActivity
{

    @BindView(R.id.tv_full_content)
    protected TextView mTvFullContent;

    @BindView(R.id.toolbar1)
    protected Toolbar toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_content);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar1);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("新闻全文");
        }



        String content = getIntent().getStringExtra("content");
        mTvFullContent.setText(content);
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


    public static void actionStart(Context context, String content)
    {
        Intent intent = new Intent(context, FullContentActivity.class);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }
}
