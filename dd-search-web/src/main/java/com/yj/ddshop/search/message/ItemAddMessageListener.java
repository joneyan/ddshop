package com.yj.ddshop.search.message;

import com.yj.ddshop.dao.TbItemSearchCustomMapper;
import com.yj.ddshop.pojo.vo.TbItemSearchCustom;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ItemAddMessageListener implements MessageListener{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TbItemSearchCustomMapper tbItemSearchCustomDao;
    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try{
            TextMessage textMessage=(TextMessage)message;
            String text = textMessage.getText();
            Long itemId = Long.parseLong(text);
            TbItemSearchCustom tbItemSearchCustom=tbItemSearchCustomDao.getById(itemId);
            //添加到索引库
            SolrInputDocument document=new SolrInputDocument();
            document.addField("id",tbItemSearchCustom.getId());
            document.addField("item_title",tbItemSearchCustom.getTitle());
            document.addField("item_sell_point",tbItemSearchCustom.getSellPoint());
            document.addField("item_price",tbItemSearchCustom.getPrice());
            document.addField("item_image",tbItemSearchCustom.getImage());
            document.addField("item_category_name",tbItemSearchCustom.getCatName());
            //添加到索引库
            solrServer.add(document);
            solrServer.commit();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
    }
}
