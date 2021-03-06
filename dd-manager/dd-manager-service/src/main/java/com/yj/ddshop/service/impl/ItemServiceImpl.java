package com.yj.ddshop.service.impl;

import com.yj.ddshop.common.dto.Order;
import com.yj.ddshop.common.dto.Page;
import com.yj.ddshop.common.dto.Result;
import com.yj.ddshop.common.util.IDUtils;
import com.yj.ddshop.dao.TbItemCustomMapper;
import com.yj.ddshop.dao.TbItemDescMapper;
import com.yj.ddshop.dao.TbItemMapper;
import com.yj.ddshop.dao.TbItemParamItemMapper;
import com.yj.ddshop.pojo.po.TbItem;
import com.yj.ddshop.pojo.po.TbItemDesc;
import com.yj.ddshop.pojo.po.TbItemExample;
import com.yj.ddshop.pojo.po.TbItemParamItem;
import com.yj.ddshop.pojo.vo.TbItemCustom;
import com.yj.ddshop.pojo.vo.TbItemQuery;
import com.yj.ddshop.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: DHC
 * Date: 2017/11/6
 * Time: 9:48
 * Version:V1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbItemMapper itemDao;
    @Autowired
    private TbItemCustomMapper itemCustomDao;
    @Autowired
    private TbItemDescMapper itemDescDao;
    @Autowired
    private TbItemParamItemMapper itemParamItemDao;

    @Override
    public TbItem getById(Long itemId) {
        TbItem tbItem = itemDao.selectByPrimaryKey(itemId);
        return tbItem;
    }

//    @Override
//    public List<TbItem> listItems() {
//        List<TbItem> list = null;
//        try {
//            list = itemDao.selectByExample(null);
//        }catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        }
//        return list;
//    }


    @Override
    public Result<TbItemCustom> listItemsByPage(Page page, Order order, TbItemQuery query) {
        Result<TbItemCustom> result = null;
        try {
            //0 创建一个Map封装前台传递过来的参数
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("page", page);
            map.put("order", order);
            map.put("query", query);
            //1 创建一个响应参数实体类
            result = new Result<TbItemCustom>();
            //2 对total进行设值(符合条件的总记录数)
            int total = itemCustomDao.countItems(map);
            result.setTotal(total);
            //3 对rows进行设值(指定页码显示记录集合)
            List<TbItemCustom> list = itemCustomDao.listItemsByPage(map);
            result.setRows(list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateBatch(List<Long> ids) {
        int i = 0;
        try {
            //准备商品对象，这个对象包含了状态为3的字段
            TbItem record = new TbItem();
            record.setStatus((byte) 3);
            //创建更新模板 update tb_item set status=? where id in (?,?,?)
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(ids);
            //执行更新
            i = itemDao.updateByExampleSelective(record, example);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
    @Override
    public int updateBatchdown(List<Long> ids) {
        int i = 0;
        try {
            //准备商品对象，这个对象包含了状态为3的字段
            TbItem record = new TbItem();
            record.setStatus((byte) 2);
            //创建更新模板 update tb_item set status=? where id in (?,?,?)
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(ids);
            //执行更新
            i = itemDao.updateByExampleSelective(record, example);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
    @Override
    public int updateBatchup(List<Long> ids) {
        int i = 0;
        try {
            //准备商品对象，这个对象包含了状态为3的字段
            TbItem record = new TbItem();
            record.setStatus((byte) 1);
            //创建更新模板 update tb_item set status=? where id in (?,?,?)
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andIdIn(ids);
            //执行更新
            i = itemDao.updateByExampleSelective(record, example);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }

    //加上注解@Transactional之后，这个方法就变成了事务方法
    //并不是事务方法越多越好，查询方法不需要添加为事务方法
    @Transactional
    @Override
    public Long saveItem(TbItem tbItem, String content,String paramData) {
        int i = 0;
        Long itemId = IDUtils.getItemId();
        try {
            //这个方法中需要处理两张表格tb_item tb_item_desc
            //调用工具类生成商品的ID
            //处理tb_item

            tbItem.setId(itemId);
            tbItem.setStatus((byte)2);
            tbItem.setCreated(new Date());
            tbItem.setUpdated(new Date());
            i = itemDao.insert(tbItem);
            //处理tb_item_desc
            TbItemDesc desc = new TbItemDesc();
            desc.setItemId(itemId);
            desc.setItemDesc(content);
            desc.setCreated(new Date());
            desc.setUpdated(new Date());
            i += itemDescDao.insert(desc);

            //处理tb_item_param_item
            TbItemParamItem tbItemParamItem = new TbItemParamItem();
            tbItemParamItem.setItemId(itemId);
            tbItemParamItem.setParamData(paramData);
            tbItemParamItem.setCreated(new Date());
            tbItemParamItem.setUpdated(new Date());
            i += itemParamItemDao.insert(tbItemParamItem);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return itemId;
    }
}
