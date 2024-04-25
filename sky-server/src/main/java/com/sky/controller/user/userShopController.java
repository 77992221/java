package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/shop")
@Api("店铺相关接口")
@Slf4j
public class userShopController {
    public static final String Key = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/status")
    @ApiOperation("获取店铺状态接口")
    public Result<Integer> getStatus()
    {
        Integer status = (Integer) redisTemplate.opsForValue().get(Key);
        log.info("获取店铺营业状态:{}",status == 1?"营业中":"打烊中");
        return Result.success(status);
    }
}
