package com.demo.NewsApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Result {
    private String status;
    private long totalResults;

    @JsonProperty("articles")
    private List<Article> newsItems;

    public Result(){
    }


    public Result(String status, long totalResults, List<Article> newsItems) {
        this.status = status;
        this.totalResults = totalResults;
        this.newsItems = newsItems;
    }



    public List<Article> getnewsItems() {
        return newsItems;
    }

    public void setnewsItems(List<Article> newsItems) {
        this.newsItems = newsItems;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", newsItems=" + newsItems +
                '}';
    }
}
