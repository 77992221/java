package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    /**
     * 查询历史订单方法
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> select(OrdersPageQueryDTO ordersPageQueryDTO);
@Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);

    /**
     * 根据id查询订单信息
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);
@Select("select count(*) from orders where status =#{status}")
    Integer getStatus(Integer status);

    /**
     * 根据订单状态以及下单时间确定订单
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status =#{status} and order_time <#{orderTime}")
List<Orders> getByStaqtusAndOrderTime(Integer status,LocalDateTime orderTime);
}