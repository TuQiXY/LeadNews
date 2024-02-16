package com.heima.model.behavior.dtos;



public class LikesBehaviorDto {


    // 文章、动态、评论等ID
   private Long articleId;
    /**
     * 喜欢内容类型
     * 0文章
     * 1动态
     * 2评论
     */
   private Short type;

    /**
     * 喜欢操作方式
     * 0 点赞
     * 1 取消点赞
     */
   private Short operation;

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

    public Short getOperation() {
        return operation;
    }

    public void setOperation(Short operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "LikesBehaviorDto{" +
                "articleId=" + articleId +
                ", type=" + type +
                ", operation=" + operation +
                '}';
    }
}
