package com.sky.service;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressBook;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCart;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.vo.OrderReportVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Override
    public OrderSubmitVO order(OrdersSubmitDTO ordersSubmitDTO) {
        //判断传来的数据中，地址以及购物车数据是否为空
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        AddressBook addressBook = addressBookMapper.getById(orders.getAddressBookId());
        if(addressBook == null)//地址判断
        {
            throw  new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> ShoppingCartlist = shoppingCartMapper.list(shoppingCart);
        if(ShoppingCartlist == null || ShoppingCartlist.size() ==0)//用户购物车数据判断
        {
            throw  new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        orders.setUserId(userId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        //插入一条订单数据
        orderMapper.insert(orders);
        List<OrderDetail> orderDetails = new ArrayList<>();

        //插入多条订单详情数据
        for(ShoppingCart cart: ShoppingCartlist) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart,orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetails.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetails);
        //清空购物车数据
        shoppingCartMapper.deleteById(userId);
        //将结果封装为VO对象进行返回
       OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
               .id(orders.getId())
               .orderTime(orders.getOrderTime())
               .orderNumber(orders.getNumber())
               .orderAmount(orders.getAmount())
               .build();

        return orderSubmitVO;
    }
}
