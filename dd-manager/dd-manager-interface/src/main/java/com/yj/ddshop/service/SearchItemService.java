package com.yj.ddshop.service;

import com.yj.ddshop.pojo.vo.TbSearchItemResult;

public interface SearchItemService {
    boolean importAllItems();

    TbSearchItemResult search(String keyword, Integer page, int i);
}
