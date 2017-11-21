package com.yj.ddshop.portal.web;

import com.yj.ddshop.common.util.PropKit;
import com.yj.ddshop.pojo.po.TbContent;
import com.yj.ddshop.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProtalIndexAction {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/")
    public String protalIndex(Model model){
        //第一步:使用service去查,根据tb_content_category的ID去查找
        Long id = PropKit.use("ftp.properties").getLong("ftp.gallery");
        List<TbContent> list=contentService.listContentByCid(id);
        //第二部放到model中
        model.addAttribute("ad1List",list);
        return "index";

    }
}
