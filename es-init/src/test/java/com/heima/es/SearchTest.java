package com.heima.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 搜索测试类
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchTest {
    @Autowired
    private RestHighLevelClient client;

    /**
     * 查询所有
     */
    @Test
    public void testqueryAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        //指定索引库
        searchRequest.indices("app_info_article");

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(sourceBuilder);

        SearchResponse res = client.search(searchRequest, RequestOptions.DEFAULT);

        //查询匹配
        SearchHits hits = res.getHits();
        System.out.println("took：" + res.getTook());
        System.out.println("是否超时：" + res.isTimedOut());
        System.out.println("TotalHits：" + hits.getTotalHits());
        System.out.println("MaxScore：" + hits.getMaxScore());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }


    }

    /**
     * 根据term条件查询
     * @throws IOException
     */
    @Test
    public void testTermQuery() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("user");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有对象
        sourceBuilder.query(QueryBuilders.termQuery("name","zhangsan"));
        request.source(sourceBuilder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took：" + response.getTook());
        System.out.println("是否超时：" + response.isTimedOut());
        System.out.println("TotalHits：" + hits.getTotalHits());
        System.out.println("MaxScore：" + hits.getMaxScore());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    /**
     * 分页查询
     */
    @Test
    public void testPageQuery() throws IOException {

        SearchRequest request = new SearchRequest();
        request.indices("user");
        //构建查询的请求体
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //查询所有对象
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //第一页
        sourceBuilder.from(0);
        //三条记录
        sourceBuilder.size(11);

        //年龄排序 倒序，从大到小
        sourceBuilder.sort("age", SortOrder.DESC);
        //查询过滤字段
        String[] excludes = {"age"};
        //过滤掉age属性
        String[] includes = {};
        sourceBuilder.fetchSource(includes,excludes);


        request.source(sourceBuilder);
        //添加分页信息


        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //查询匹配
        SearchHits hits = response.getHits();
        System.out.println("took：" + response.getTook());
        System.out.println("是否超时：" + response.isTimedOut());
        System.out.println("TotalHits：" + hits.getTotalHits());
        System.out.println("MaxScore：" + hits.getMaxScore());
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }


}
