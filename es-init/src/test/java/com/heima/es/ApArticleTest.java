package com.heima.es;

import com.alibaba.fastjson.JSON;
import com.heima.es.mapper.ApArticleMapper;
import com.heima.es.pojo.SearchArticleVo;
import com.heima.es.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ApArticleTest {
    @Autowired
    private ApArticleMapper apArticleMapper;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 注意：数据量的导入，如果数据量过大，需要分页导入
     * @throws Exception
     */
    @Test
    public void init() throws Exception {

        //1.查询所有符合条件的文章数据
        List<SearchArticleVo> searchArticleVos = apArticleMapper.loadArticleList();

        //2.批量导入到es索引库

        BulkRequest bulkRequest = new BulkRequest("app_info_article");

        for (SearchArticleVo searchArticleVo : searchArticleVos) {

            IndexRequest indexRequest = new IndexRequest().id(searchArticleVo.getId().toString())
                    .source(JSON.toJSONString(searchArticleVo), XContentType.JSON);

            //批量添加数据
            bulkRequest.add(indexRequest);

        }
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

    }

    /**
     *  添加索引库
     */
    @Test
    public void AddIndex() throws IOException {
        //创建索引库
        CreateIndexRequest request = new CreateIndexRequest("user");
        //获取响应
        CreateIndexResponse response =
                restHighLevelClient.indices().create(request,RequestOptions.DEFAULT);

        boolean acknowledged = response.isAcknowledged();

        System.out.println("响应状态:"+acknowledged);

    }
    //查询索引库
    @Test
    public void testGetIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("user");

        GetIndexResponse response = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);

        //
        System.out.println(response.getAliases());
        //获取默认配置
        System.out.println(response.getDefaultSettings());
        //获取索引名称
        System.out.println(Arrays.toString(response.getIndices()));
        //获取映射
        System.out.println(response.getMappings());

    }

    /**
     * 删除索引库
     */
    @Test
    public void testDeleteIndex() throws IOException {
        //删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest("user");

        AcknowledgedResponse response = restHighLevelClient.indices().delete(request,RequestOptions.DEFAULT);

        System.out.println(response.isAcknowledged());

        restHighLevelClient.close();

    }

    /**
     *  添加文档
     */
    @Test
    public void testAddDoucument() throws IOException {

        /**
         * 第一次不存在时是创建，
         *  第二次存在时就是修改，将修改version版本号
         */

        IndexRequest indexRequest = new IndexRequest("user");

        indexRequest.id("122");

        User user = new User();
        user.setId(122);
        user.setName("张三");
        user.setAge(12);
        user.setScore(123);
        //添加数据
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        //获取索引库id
        System.out.println(response.getIndex());
        //获取文档id
        System.out.println(response.getId());
        //获取版本
        System.out.println(response.getVersion());
        //获取结果
        System.out.println(response.getResult());


    }

    /**
     * 修改文档
     */
    @Test
    public void testUpadteDoc() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("user","122");

        User user = new User();
        user.setId(122);
        user.setName("张三");
        user.setAge(13);
        user.setScore(123);
        updateRequest.doc(JSON.toJSONString(user), XContentType.JSON);

        UpdateResponse res = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);

        System.out.println(res.getId());
        System.out.println(res.getIndex());
        System.out.println(res.getResult());
        System.out.println(res.getVersion());

        restHighLevelClient.close();
    }


    /**
     *  删除文档
     */
    @Test
    public void testDelteDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("user","122");

        DeleteResponse res = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(res.getId());
        System.out.println(res.getIndex());
        System.out.println(res.getResult());

    }

    /**
     * 查询文档
     */
    @Test
    public void testGetDoc() throws IOException {
        GetRequest getRequest =
                new GetRequest("user","122");

        GetResponse res = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        System.out.println(res.getIndex());
        System.out.println(res.getId());
        System.out.println(res.getSource());
        System.out.println(res.getFields());
        System.out.println(res.getSourceAsString()); //返回json串
//        user
//        122
//        {score=123, name=张三, id=122, age=12}
//        {}
//        {"age":12,"id":122,"name":"张三","score":123}

    }

    /**
     * 批量添加文档
     */
    @Test
    public void testBulkAdd() throws IOException {


        User user1 = new User(1,"王五",12,213);
        User user2 = new User(2,"李四",321,213);
        User user3 = new User(3,"赵六",24,213);
        User user4 = new User(4,"zhangsan",41,213);
        User user5 = new User(5,"蔡徐坤",21,213);

        BulkRequest request = new BulkRequest("user");

        IndexRequest request1 = new IndexRequest("user").id(user1.getId().toString());
        request1.source(JSON.toJSONString(user1),XContentType.JSON);

        IndexRequest request2 = new IndexRequest("user").id(user2.getId().toString());
        request2.source(JSON.toJSONString(user2),XContentType.JSON);

        IndexRequest request3 = new IndexRequest("user").id(user3.getId().toString());
        request3.source(JSON.toJSONString(user3),XContentType.JSON);

        IndexRequest request4 = new IndexRequest("user").id(user4.getId().toString());;
        request4.source(JSON.toJSONString(user4),XContentType.JSON);

        IndexRequest request5 = new IndexRequest("user").id(user5.getId().toString());
        request5.source(JSON.toJSONString(user5),XContentType.JSON);

        request.add(request1);
        request.add(request2);
        request.add(request3);
        request.add(request4);
        request.add(request5);

        BulkResponse res = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);

        System.out.println(res.getIngestTook());



        System.out.println(res.getTook());
        System.out.println(res.getIngestTookInMillis());


    }



}