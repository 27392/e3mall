package cn.haohaoli.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @author Liwenhao
 * @date 2018/8/13 12:20
 */
public class TestSolrj {

    HttpSolrClient httpSolrClient;

    @Before
    public void before(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/spring-solr.xml");
        httpSolrClient = applicationContext.getBean(HttpSolrClient.class);
    }

/*

    @Test
    public void addDocument() throws Exception {

        HttpSolrClient httpSolrClient = new HttpSolrClient.Builder("http://118.89.26.70:8080/solr/order").build();

        SolrInputDocument document = new SolrInputDocument();
//        document.setField("id","doc01");
//        document.setField("item_title","测试商品01");
//        document.setField("item_price","1000");

        document.addField("id","doc012");
//        document.addField("title","1");
        document.addField("sell_point","1");
        document.addField("price","1");
        document.addField("image","1");
        document.addField("category_name","1");

        httpSolrClient.add(document);
        httpSolrClient.commit();
    }
*/

    @Test
    public void addDocumentSpring() throws Exception {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/spring-solr.xml");

        HttpSolrClient bean = applicationContext.getBean(HttpSolrClient.class);
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id","11111");
        document.setField("item_title","测试商品01");
        document.setField("item_price","1000");
        bean.add(document);
        bean.commit();
    }

    @Test
    public void delDocument() throws Exception {

//        HttpSolrClient httpSolrClient = new HttpSolrClient.Builder("http://118.89.26.70:8080/solr/order").build();
//        httpSolrClient.deleteById("doc01");
        httpSolrClient.deleteByQuery("id:11111");
        httpSolrClient.commit();
    }

    @Test
    public void query() throws Exception {
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //设置查询参数
//        query.setQuery("*:*");
        query.set("q", "*:*");
        //执行查询
        QueryResponse queryResponse = httpSolrClient.query(query);
        //获取文档列表，取查询结构总记录数
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("查询的总记录数：" + results.getNumFound());
        //遍历文档列表 获取内容
        for (SolrDocument result : results) {
            System.out.println(result.get("id"));
            System.out.println(result.get("item_title"));
            System.out.println(result.get("item_sell_point"));
            System.out.println(result.get("item_price"));
            System.out.println(result.get("item_image"));
            System.out.println(result.get("item_category_name"));
        }
    }


    @Test
    public void complexQuery() throws Exception {
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //设置查询参数
        query.set("q", "手机");
        query.setStart(0);
        query.setRows(20);
        query.set("df","item_title");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em>");
        query.setHighlightSimplePost("</em>");
        //执行查询
        QueryResponse queryResponse = httpSolrClient.query(query);
        //获取文档列表，取查询结构总记录数
        SolrDocumentList results = queryResponse.getResults();
        System.out.println("查询的总记录数：" + results.getNumFound());
        //获取高亮
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //遍历文档列表 获取内容
        for (SolrDocument result : results) {
            List<String> strings = highlighting.get(result.get("id")).get("item_title");
            String title = "";
            if (strings != null && strings.size() > 0) {
                title = strings.get(0);
            } else {
                title = result.get("item_title").toString();
            }
            System.out.println(result.get("id"));
            System.out.println(title);
            System.out.println(result.get("item_sell_point"));
            System.out.println(result.get("item_price"));
            System.out.println(result.get("item_image"));
            System.out.println(result.get("item_category_name"));
        }
    }



}
