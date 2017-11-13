package com.yj.ddshop.service;

import com.yj.ddshop.common.dto.Order;
import com.yj.ddshop.common.dto.Page;
import com.yj.ddshop.common.dto.Result;
import com.yj.ddshop.pojo.po.TbItem;
import com.yj.ddshop.pojo.vo.TbItemCustom;
import com.yj.ddshop.pojo.vo.TbItemQuery;

import java.util.List;

/**
 * User: DHC
 * Date: 2017/11/6
 * Time: 9:48
 * Version:V1.0
 */
public interface ItemService {

    TbItem getById(Long itemId);

//    List<TbItem> listItems();

    /**
     * 分页
     * @param page
     * @return
     */
    Result<TbItemCustom> listItemsByPage(Page page, Order order, TbItemQuery query);

    /**
     * 批量修改
     * @param ids
     * @return
     */
    int updateBatch(List<Long> ids);

    /**
     * 新增商品
     * @param tbItem 商品实体类
     * @param content 商品描述
     * @return 受到影响的行数
     */
    int saveItem(TbItem tbItem, String content);
}
