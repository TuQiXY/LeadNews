package com.heima.model.behavior.dtos;

import com.heima.model.common.annotation.IdEncrypt;



public class UnLikesBehaviorDto {
    // 文章ID
    @IdEncrypt
    Long articleId;

    /**
     * 不喜欢操作方式
     * 0 不喜欢
     * 1 取消不喜欢
     */
    Short type;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UnLikesBehaviorDto{" +
                "articleId=" + articleId +
                ", type=" + type +
                '}';
    }
}