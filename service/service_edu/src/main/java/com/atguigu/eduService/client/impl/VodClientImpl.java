package com.atguigu.eduService.client.impl;

import com.atguigu.commonutils.R;
import com.atguigu.eduService.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 */
@Component
public class VodClientImpl implements VodClient {
    //出错之后会执行，兜底方法
    @Override
    public R removeAliyunVideoById(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R removeBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
