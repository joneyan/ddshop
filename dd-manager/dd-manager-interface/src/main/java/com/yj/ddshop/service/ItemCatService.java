package com.yj.ddshop.service;

import com.yj.ddshop.common.dto.TreeNode;

import java.util.List;

/**
 * User: DHC
 * Date: 2017/11/10
 * Time: 15:08
 * Version:V1.0
 */
public interface ItemCatService {
    List<TreeNode> listItemCatsByPid(Long parentId);
}
