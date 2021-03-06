package com.scnu.newsbrief.constant;

import com.scnu.newsbrief.R;
import com.scnu.newsbrief.bean.network.NewsResponseInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Created by WuchangI on 2018/11/4.
 */

public class Constants {
    public static class URL {
        public static final String BASE_URL = "http://39.108.122.17/";

        public static final String GET_LOGIN_STATUS = "login.php";
        public static final String GET_REGISTER_STATUS = "register.php";
        public static final String GET_NEWS_CONTENT = "newsContent.php";

        public static final String FORUM_URL = "http://bbs1.people.com.cn/board/1/129.html";
    }


    //记录的是所有新闻
    public static List<NewsResponseInfo.NewsContentsBean> newsContents = null;
    //分好类的全部新闻
    public static Vector<List<NewsResponseInfo.NewsContentsBean>> classifynewsContents = new Vector<>();
    //要显示的新闻，一个向量，每个元素是一个列表，每个列表都是一类新闻
    public static Vector<List<NewsResponseInfo.NewsContentsBean>> newscontentsdisplay = new Vector<>();

    public static Map<String, Integer> newsfromimg = new HashMap<String, Integer>() {{
        put("新浪新闻", R.drawable.sina);
        put("央视新闻", R.drawable.yangshi);
        put("腾讯新闻", R.drawable.tengxun);
        put("网易新闻", R.drawable.wangyi);
        put("搜狐新闻", R.drawable.souhu);

    }};
}
