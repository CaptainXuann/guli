package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-02
 */
@RestController
@RequestMapping("/educms/bannerFront")
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有幻灯片
    @GetMapping("getAll")
    public R getAll(){
        List<CrmBanner> list = crmBannerService.getAllBanner();
        return R.ok().data("list",list);
    }
}

