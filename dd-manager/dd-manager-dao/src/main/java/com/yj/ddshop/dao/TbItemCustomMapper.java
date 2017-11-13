package com.yj.ddshop.dao;

import com.yj.ddshop.pojo.vo.TbItemCustom;

import java.util.List;
import java.util.Map;

/**
 * 自定义的商品实体数据访问层接口
 * User: DHC
 * Date: 2017/11/7
 * Time: 14:33
 * Version:V1.0
 */
public interface TbItemCustomMapper {
    /**
     * 查询商品表中所有记录的数量
     * @return
     */
    int countItems(Map<String, Object> map);

    /**
     * 查询指定页码显示记录集合
     * @param map
     * @return
     */
    List<TbItemCustom> listItemsByPage(Map<String, Object> map);
}
