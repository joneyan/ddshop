package com.yj.ddshop.search.web;

import com.yj.ddshop.pojo.vo.TbSearchItemResult;
import com.yj.ddshop.service.SearchItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchIndexAction {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/")
    public String portalIndex(String keyword,
                                @RequestParam(defaultValue = "1") Integer page, Model model){
        try {
            if (keyword != null) {
                keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
                //从solr索引库中查询商品
                TbSearchItemResult searchResult = searchItemService.search(keyword, page, 60);
                //把结果传递给页面
                model.addAttribute("query", keyword);
                model.addAttribute("totalPages", searchResult.getTotalPages());
                model.addAttribute("page", page);
                model.addAttribute("recourdCount", searchResult.getRecordCount());
                model.addAttribute("itemList", searchResult.getItemList());

            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }




        return "search";
    }
}
