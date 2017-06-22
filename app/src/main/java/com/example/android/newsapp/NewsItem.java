package com.example.android.newsapp;

/**
 * A custom class to store information about a news item, e.g section, title, web url.
 */

public class NewsItem {

    /**
     * Section of the news
     */
    private String mSection;

    /**
     * Title of the news
     */
    private String mTitle;

    /**
     * WebURL of the news
     */
    private String mUrl;

    /**
     * Public constructor to create a new {@link NewsItem} object
     *
     * @param section is the section of the news
     * @param title   is the title of the news
     * @param url     is the url of the news
     */
    public NewsItem(String section, String title, String url) {
        mSection = section;
        mTitle = title;
        mUrl = url;
    }

    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
