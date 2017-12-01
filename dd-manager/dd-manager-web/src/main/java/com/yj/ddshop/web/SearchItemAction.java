package com.yj.ddshop.web;

import com.yj.ddshop.common.dto.MessageResult;
import com.yj.ddshop.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchItemAction {
    @Autowired
    private SearchItemService searchItemService;
    @ResponseBody
    @RequestMapping("/item/search/import")
    public MessageResult searchItemIndex(){
        boolean bool=searchItemService.importAllItems();
        MessageResult mr=new MessageResult();
        if(bool){
            mr.setSuccess(true);
            mr.setMessage("索引导入成功");
        }else{
            mr.setSuccess(false);
            mr.setMessage("索引导入失败");
        }

        return mr;
    }
}
