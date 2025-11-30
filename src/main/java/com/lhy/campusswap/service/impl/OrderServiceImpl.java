package com.lhy.campusswap.service.impl;

import com.lhy.campusswap.entity.Order;
import com.lhy.campusswap.mapper.OrderMapper;
import com.lhy.campusswap.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author LHY
 * @since 2025-11-25
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
