package com.heima.model.behavior.dtos;


public class ReadBehaviorDto {

    // 文章、动态、评论等ID
    Long articleId;

    /**
     * 阅读次数
     */
    Short count;

    /**
     * 阅读时长（S)
     */
    Integer readDuration;

    /**
     * 阅读百分比
     */
    Short percentage;

    /**
     * 加载时间
     */
    Short loadDuration;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

    public Integer getReadDuration() {
        return readDuration;
    }

    public void setReadDuration(Integer readDuration) {
        this.readDuration = readDuration;
    }

    public Short getPercentage() {
        return percentage;
    }

    public void setPercentage(Short percentage) {
        this.percentage = percentage;
    }

    public Short getLoadDuration() {
        return loadDuration;
    }

    public void setLoadDuration(Short loadDuration) {
        this.loadDuration = loadDuration;
    }

    @Override
    public String toString() {
        return "ReadBehaviorDto{" +
                "articleId=" + articleId +
                ", count=" + count +
                ", readDuration=" + readDuration +
                ", percentage=" + percentage +
                ", loadDuration=" + loadDuration +
                '}';
    }
}