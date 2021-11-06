package com.atguigu.eduService.client;

import com.atguigu.commonutils.R;
import com.atguigu.eduService.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author
 */
@FeignClient(value = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {

    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAliyunVideoById/{id}")
    public R removeAliyunVideoById(@PathVariable("id") String id);

    //根据id删除多个阿里云视频
    @DeleteMapping("/eduvod/video/removeBatch")
    public R removeBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
