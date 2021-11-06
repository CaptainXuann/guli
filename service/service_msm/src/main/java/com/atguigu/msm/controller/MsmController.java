package com.atguigu.msm.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.atguigu.commonutils.R;
import com.atguigu.msm.service.MsmService;
import com.atguigu.msm.utils.RandomUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author
 */
@RestController
@RequestMapping("/eduMsm")
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信的方法
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone){
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        code = RandomUtils.getFourBitRandom();
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",code);
        boolean isSend=msmService.send(map,phone);
        if(isSend){
            System.out.println(map.get("code"));
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error();
        }
    }


}
