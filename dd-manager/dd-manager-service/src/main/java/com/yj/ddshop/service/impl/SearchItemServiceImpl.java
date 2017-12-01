package com.yj.ddshop.service.impl;

import com.yj.ddshop.dao.SearchItemDao;
import com.yj.ddshop.dao.TbItemSearchCustomMapper;
import com.yj.ddshop.pojo.vo.TbItemSearchCustom;
import com.yj.ddshop.pojo.vo.TbSearchItemResult;
import com.yj.ddshop.service.SearchItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private TbItemSearchCustomMapper tbItemSearchCustomDao;
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchItemDao searchItemDao;
    @Override
    public boolean importAllItems() {
        List<TbItemSearchCustom> list = tbItemSearchCustomDao.listSearchItems();
        for (TbItemSearchCustom searchItem : list) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id",searchItem.getId());
            document.addField("item_title",searchItem.getTitle());
            document.addField("item_sell_point",searchItem.getSellPoint());
            document.addField("item_price",searchItem.getPrice());
            document.addField("item_image",searchItem.getImage());
            document.addField("item_category_name",searchItem.getCatName());
            try {
                solrServer.add(document);
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        try {
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public TbSearchItemResult search(String keyword, Integer page, int rows) {
        //创建一个solrQuery对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(keyword);
        //设置分页条件
        if(page<=0)page=1;
        query.setStart((page - 1) * rows);
        query.setRows(rows);

        //设置默认搜索域
        query.set("df","item_keywords");
        //开启高亮显示
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");


        //调用dao执行查询
        TbSearchItemResult searchResult =searchItemDao.search(query);
        long recordCount = searchResult.getRecordCount();

        int totalPage = (int) ((recordCount + rows - 1) / rows);
        //添加到返回结果
        searchResult.setTotalPages(totalPage);
        //返回结果
        return searchResult;
    }
}
