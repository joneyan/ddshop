package com.yj.ddshop.service;

import com.yj.ddshop.common.dto.Page;
import com.yj.ddshop.common.dto.Result;
import com.yj.ddshop.pojo.po.TbItemParam;
import com.yj.ddshop.pojo.vo.TbItemParamCustom;

/**
 * User: DHC
 * Date: 2017/11/13
 * Time: 17:44
 * Version:V1.0
 */
public interface ItemParamService {
    /**
     * 对参数规格说明表进行分页操作
     * @param page
     * @return
     */
    Result<TbItemParamCustom> listItemParamsByPage(Page page);

    int saveItemParam(Long cid, String jsonStr);

    TbItemParam getItemParamByCid(Long cid);
}
