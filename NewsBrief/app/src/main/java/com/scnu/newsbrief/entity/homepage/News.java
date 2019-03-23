package com.scnu.newsbrief.entity.homepage;

/**
 * Created by lw on 2017/4/14.
 */


public class News
{
    private String sourcename;
    private int imageId;
    private String content;
    private String url;

    public News()
    {

    }

    public News(String sourcename, int imageId, String content, String url)
    {
        this.sourcename = sourcename;
        this.imageId = imageId;
        this.content = content;
        this.url = url;
    }

    public String getSourcename()
    {
        return sourcename;
    }

    public void setSourcename(String sourcename)
    {
        this.sourcename = sourcename;
    }

    public int getImageId()
    {
        return imageId;
    }

    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
