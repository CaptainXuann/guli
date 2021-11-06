package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author
 */
public interface VodService {
    String uploadVideoAliyun(MultipartFile file);

    void removeAliyunVideoById(String id);

    void removeMoreVideo(List<String> videoIdList);

    String getPlayAuth(String id);
}
