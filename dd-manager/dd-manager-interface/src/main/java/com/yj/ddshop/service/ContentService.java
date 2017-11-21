package com.yj.ddshop.service;

import com.yj.ddshop.pojo.po.TbContent;

import java.util.List;

public interface ContentService {
    /**
     * 根据内容分类的编号查询出内容
     * @param id
     * @return
     */
    List<TbContent> listContentByCid(Long id);
}
