package cn.haohaoli.search.message;

import cn.haohaoli.common.enums.SolrSearchField;
import cn.haohaoli.common.pojo.SearchItem;
import cn.haohaoli.search.mapper.ItemMapper;
import com.alibaba.fastjson.JSON;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * 监听商品添加
 * @author Liwenhao
 * @date 2018/8/14 18:51
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private HttpSolrClient httpSolrClient;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            //等待事务提交
            Thread.sleep(100);
            long itemId = Long.parseLong(textMessage.getText());
            SearchItem searchItem = itemMapper.selectByPrimaryId(itemId);
            SolrInputDocument solrInputFields = new SolrInputDocument();
            solrInputFields.addField(SolrSearchField.ID.getName(),searchItem.getId());
            solrInputFields.addField(SolrSearchField.ITEM_TITLE.getName(),searchItem.getTitle());
            solrInputFields.addField(SolrSearchField.ITEM_SELL_POINT.getName(),searchItem.getSell_point());
            solrInputFields.addField(SolrSearchField.ITEM_PRICE.getName(),searchItem.getPrice());
            solrInputFields.addField(SolrSearchField.ITEM_IMAGE.getName(),searchItem.getImage());
            solrInputFields.addField(SolrSearchField.ITEM_CATEGORY_NAME.getName(),searchItem.getCategory_name());
            httpSolrClient.add(solrInputFields);
            httpSolrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
