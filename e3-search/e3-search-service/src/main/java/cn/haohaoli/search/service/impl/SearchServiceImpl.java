package cn.haohaoli.search.service.impl;

import cn.haohaoli.common.pojo.SearchResult;
import cn.haohaoli.search.dao.SearchDao;
import cn.haohaoli.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Liwenhao
 * @date 2018/8/13 17:18
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int page, int rows) throws Exception {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", keyword);
        if (page < 0) page = 1;
        solrQuery.setStart((page - 1) * rows);
        solrQuery.setRows(rows);
        solrQuery.set("df", "item_title");
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");
        SearchResult search = searchDao.search(solrQuery);
        long recordCount = search.getRecordCount();
        int totalPage = (int) (recordCount / rows);
        if (totalPage % rows > 0) {
            totalPage++;
        }
        search.setTotalPages(totalPage);
        int s = 1/0;
        return search;
    }
}
