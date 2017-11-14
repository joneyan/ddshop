package com.yj.ddshop.service.impl;

import com.yj.ddshop.common.dto.Page;
import com.yj.ddshop.common.dto.Result;
import com.yj.ddshop.dao.TbItemParamCustomMapper;
import com.yj.ddshop.dao.TbItemParamMapper;
import com.yj.ddshop.pojo.po.TbItemParam;
import com.yj.ddshop.pojo.po.TbItemParamExample;
import com.yj.ddshop.pojo.vo.TbItemParamCustom;
import com.yj.ddshop.service.ItemParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: DHC
 * Date: 2017/11/13
 * Time: 17:45
 * Version:V1.0
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TbItemParamMapper itemParamDao;
    @Autowired
    private TbItemParamCustomMapper itemParamCustomDao;

    @Override
    public Result<TbItemParamCustom> listItemParamsByPage(Page page) {
        Result<TbItemParamCustom> result = null;
        try {
            //DAO层接口多多个参数解决方案之一
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("page", page);
            //取出tb_item_param这张表记录条数
            int count = itemParamCustomDao.countItemParams();
            //取出对应页码的记录集合
            List<TbItemParamCustom> list = itemParamCustomDao.listItemParamsByPage(map);
            result = new Result<TbItemParamCustom>();
            result.setTotal(count);
            result.setRows(list);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int saveItemParam(Long cid, String jsonStr) {
        int i=0;
        try{
            TbItemParam tbItemParam = new TbItemParam();
            tbItemParam.setItemCatId(cid);
            tbItemParam.setParamData(jsonStr);
            tbItemParam.setCreated(new Date());
            tbItemParam.setUpdated(new Date());
            i=itemParamDao.insert(tbItemParam);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public TbItemParam getItemParamByCid(Long cid) {
        TbItemParam tbItemParam = null;
        try {
            //创建查询模板
            TbItemParamExample example = new TbItemParamExample();
            TbItemParamExample.Criteria criteria = example.createCriteria();
            criteria.andItemCatIdEqualTo(cid);
            //执行查询
            List<TbItemParam> list = itemParamDao.selectByExampleWithBLOBs(example);
            if(list != null && list.size() > 0){
                tbItemParam = list.get(0);
            }
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return tbItemParam;
    }
}
