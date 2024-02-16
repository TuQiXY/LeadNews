package com.heima.search.service.impl;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.model.user.pojos.ApUser;
import com.heima.search.service.ApUserSearchService;
import com.heima.search.service.ArticleSearchService;
import com.heima.utils.thread.AppThreadLocalUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleSearchServiceImpl implements ArticleSearchService {

    @Resource
    private RestHighLevelClient client;
    @Resource
    private ApUserSearchService apUserSearchService;

    /**
     * es搜索文章
      * @param dto
     * @return
     * @throws IOException
     */
    @Override
    public ResponseResult search(UserSearchDto dto) throws IOException {

        //1.检查参数
        if(dto == null || StringUtils.isBlank(dto.getSearchWords())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }


        ApUser user = AppThreadLocalUtil.getUser();

        //异步调用 保存搜索记录
        if(user != null && dto.getFromIndex() == 0){
            apUserSearchService.insert(dto.getSearchWords(), user.getId());
        }

        //构建搜索请求
        SearchRequest searchRequest = new SearchRequest("app_info_article");

        //构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //布尔查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        //关键词分词之后查询
        QueryStringQueryBuilder stringQueryBuilder =
                QueryBuilders.queryStringQuery(dto.getSearchWords()).field("title").field("content")
                        .defaultOperator(Operator.OR);
        //必须参与相关性
        boolQueryBuilder.must(stringQueryBuilder);

        //查询小于mindate的数据
        RangeQueryBuilder rangeQueryBuilder =
                QueryBuilders.rangeQuery("publishTime").lte(dto.getMinBehotTime().getTime());
       //不参与相关性
        boolQueryBuilder.filter(rangeQueryBuilder);

        //分页查询
        searchSourceBuilder.from(0).size(dto.getPageSize());

        //按照发布时间倒序排序
        searchSourceBuilder.sort("publishTime", SortOrder.DESC);

        //设置高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").preTags("<font style='color: red; font-size: inherit;'>")
                .postTags("</font>");

        //封装查询条件
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);


        //构建响应对象
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        //封装高亮字段
        List<Map<String, Object>> list = new ArrayList<>();
        SearchHit[] hits = searchResponse.getHits().getHits();

        for (SearchHit hit : hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            //处理高亮
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
             if( highlightFields != null && highlightFields.size() > 0 ){
                 Text[] titles = highlightFields.get("title").getFragments();
                 String title = StringUtils.join(titles);
                 //高亮字段
                 map.put("h_title",title);
             }else{
                 //原始标题
                 map.put("h_title",map.get("title"));
             }
             list.add(map);
        }



        return ResponseResult.okResult(list);
    }
}
