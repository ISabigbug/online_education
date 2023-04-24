package com.cn.eduorder.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cn.eduorder.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Lenovo
* @description 针对表【t_order(订单)】的数据库操作Mapper
* @createDate 2023-04-24 15:00:05
* @Entity com.cn.eduorder.domain.Order
*/
public interface OrderMapper extends BaseMapper<Order> {
    Order selectOneByOrderNo(@Param("orderNo") String orderNo);
}




