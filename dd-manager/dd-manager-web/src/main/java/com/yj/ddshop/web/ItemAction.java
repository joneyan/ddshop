package com.yj.ddshop.web;

import com.yj.ddshop.common.dto.MessageResult;
import com.yj.ddshop.common.dto.Order;
import com.yj.ddshop.common.dto.Page;
import com.yj.ddshop.common.dto.Result;
import com.yj.ddshop.pojo.po.TbItem;
import com.yj.ddshop.pojo.vo.TbItemCustom;
import com.yj.ddshop.pojo.vo.TbItemQuery;
import com.yj.ddshop.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jms.*;
import java.util.List;

/**
 * User: DHC
 * Date: 2017/11/6
 * Time: 9:42
 * Version:V1.0
 */
@Controller
@Scope("prototype")
public class ItemAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ItemService itemService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination topicDestination;

    @ResponseBody
    @RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
    public TbItem getById(@PathVariable("itemId") Long itemId) {
        System.out.println(itemId);
        TbItem tbItem = itemService.getById(itemId);
        return tbItem;
    }

//    @ResponseBody
//    @RequestMapping("/items")
//    public List<TbItem> listItems(){
//        List<TbItem> list = null;
//        try {
//            list = itemService.listItems();
//        }catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        }
//        return list;
//    }

    @ResponseBody
    @RequestMapping("/items")
    public Result<TbItemCustom> listItemsByPage(Page page, Order order, TbItemQuery query) {
        Result<TbItemCustom> list = null;
        try {
            list = itemService.listItemsByPage(page, order, query);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return list;
    }


    @ResponseBody
    @RequestMapping("/items/batch")
    public int updateBatch(@RequestParam("ids[]") List<Long> ids) {
        int i = 0;
        try {
            i = itemService.updateBatch(ids);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
    @ResponseBody
    @RequestMapping("/items/batchdown")
    public int updateBatchDown(@RequestParam("ids[]") List<Long> ids) {
        int i = 0;
        try {
            i = itemService.updateBatchdown(ids);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }
    @ResponseBody
    @RequestMapping("/items/batchup")
    public int updateBatchDownup(@RequestParam("ids[]") List<Long> ids) {
        int i = 0;
        try {
            i = itemService.updateBatchup(ids);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return i;
    }

    @ResponseBody
    @RequestMapping("/item")
    public MessageResult saveItem(TbItem tbItem, String content, String paramData){
        MessageResult mr=new MessageResult();
        try {
            final Long itemId  = (Long)itemService.saveItem(tbItem, content, paramData);
            jmsTemplate.send(topicDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = session.createTextMessage(itemId + "");
                    return textMessage;

                }
            });
            mr.setMessage("发布手机成功");
            mr.setSuccess(true);
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return mr;
    }

}
