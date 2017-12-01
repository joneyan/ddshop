package com.yj.ddshop.dao;

import com.yj.ddshop.pojo.vo.TbItemSearchCustom;

import java.util.List;

public interface TbItemSearchCustomMapper {
    List<TbItemSearchCustom> listSearchItems();

    TbItemSearchCustom getById(Long  itemId);
}
