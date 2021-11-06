package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
