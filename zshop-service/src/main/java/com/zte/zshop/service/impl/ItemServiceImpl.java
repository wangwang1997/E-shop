package com.zte.zshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.dao.ItemDao;
import com.zte.zshop.dao.OrderDao;
import com.zte.zshop.dao.ProductDao;
import com.zte.zshop.entity.Item;
import com.zte.zshop.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author:helloboy
 * Date:2019-06-20 15:04
 * Description:<描述>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;


    @Override
    public void addOrderIdByOrderNo(Integer orderId, String orderNo) {

    }

    @Override
    public PageInfo<Item> findItemsByOrderId(Integer orderId) {

        PageHelper.startPage(1, 5);
        //List<Product> products = productDao.selectAll();

        List<Item> items = itemDao.selectByOrderId(orderId);
        PageInfo<Item> pageInfo = new PageInfo<>(items);

        return pageInfo;
    }

    @Override
    public List<Item> findItems(Integer orderId) {

        List<Item> items = itemDao.selectByOrderId(orderId);
        return items;
    }

    @Override
    public void insert(Item item) {
        itemDao.insertItem(item);
    }

    @Override
    public List<Item> findAllItems() {
        return itemDao.selectAll();
    }

    @Override
    public void delOrderById(Integer id) {
        itemDao.deleteItemById(id);
    }


}















