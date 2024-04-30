package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

public interface OrderService {
    /**
     * 用户西单
     * @param ordersSubmitDTO
     * @return
     */
    public OrderSubmitVO order(OrdersSubmitDTO ordersSubmitDTO);
}
