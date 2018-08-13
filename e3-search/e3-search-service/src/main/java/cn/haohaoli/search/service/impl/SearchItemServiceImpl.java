package cn.haohaoli.search.service.impl;

import cn.haohaoli.common.enums.SolrSearchField;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.common.pojo.SearchItem;
import cn.haohaoli.search.mapper.ItemMapper;
import cn.haohaoli.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/13 15:20
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public E3Result importAllItems() {
        try {
            List<SearchItem> itemList = itemMapper.selectList();
            for (SearchItem searchItem : itemList) {
                SolrInputDocument solrInputFields = new SolrInputDocument();
                solrInputFields.addField(SolrSearchField.ID.getName(),searchItem.getId());
                solrInputFields.addField(SolrSearchField.ITEM_TITLE.getName(),searchItem.getTitle());
                solrInputFields.addField(SolrSearchField.ITEM_SELL_POINT.getName(),searchItem.getSell_point());
                solrInputFields.addField(SolrSearchField.ITEM_PRICE.getName(),searchItem.getPrice());
                solrInputFields.addField(SolrSearchField.ITEM_IMAGE.getName(),searchItem.getImage());
                solrInputFields.addField(SolrSearchField.ITEM_CATEGORY_NAME.getName(),searchItem.getCategory_name());
                httpSolrClient.add(solrInputFields);
            }
            httpSolrClient.commit();
            return E3Result.ok();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        return E3Result.error("导入失败！");
    }
}
