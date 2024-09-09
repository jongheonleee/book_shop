package com.fastcampus.ch4.service.order;

import com.fastcampus.ch4.dao.order.OrderDao;
import com.fastcampus.ch4.dao.order.OrderHistoryDao;
import com.fastcampus.ch4.dao.order.OrderProductDao;
import com.fastcampus.ch4.dao.order.OrderProductStatusHistoryDao;
import com.fastcampus.ch4.dto.order.OrderProductStatusHistoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderHistoryDao orderHistoryDao;

    @Autowired
    OrderProductDao orderProductDao;

    @Autowired
    OrderProductStatusHistoryDao orderProductStatusHistoryDao;
}
