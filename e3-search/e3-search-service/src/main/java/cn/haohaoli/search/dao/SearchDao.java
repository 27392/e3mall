package cn.haohaoli.search.dao;

import cn.haohaoli.common.enums.SolrSearchField;
import cn.haohaoli.common.pojo.SearchItem;
import cn.haohaoli.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索
 * @author Liwenhao
 * @date 2018/8/13 16:48
 */
@Repository
public class SearchDao {

    @Autowired
    private HttpSolrClient httpSolrClient;

    public SearchResult search(SolrQuery query) throws Exception {
        QueryResponse queryResponse = httpSolrClient.query(query);
        //高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //获取文档列表
        SolrDocumentList documentList = queryResponse.getResults();
        //获取查询结果总数量
        long numFound = documentList.getNumFound();
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument document : documentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String) document.get(SolrSearchField.ID.getName()));
            searchItem.setCategory_name((String) document.get(SolrSearchField.ITEM_CATEGORY_NAME.getName()));
            searchItem.setImage((String) document.get(SolrSearchField.ITEM_IMAGE.getName()));
            searchItem.setPrice((Long) document.get(SolrSearchField.ITEM_PRICE.getName()));
            searchItem.setSell_point((String) document.get(SolrSearchField.ITEM_SELL_POINT.getName()));
            //获取高亮
            List<String> itemTitle = highlighting.get(searchItem.getId()).get("item_title");
            String title = "";
            if (itemList != null && itemList.size() > 0) {
                title = itemTitle.get(0);
            } else {
                title = (String) document.get(SolrSearchField.ITEM_TITLE.getName());
            }
            searchItem.setTitle(title);
            itemList.add(searchItem);
        }
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(numFound);
        searchResult.setItemList(itemList);
        return searchResult;
    }

}
