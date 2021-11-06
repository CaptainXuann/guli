package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.servicebase.config.exception.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoAliyun(MultipartFile file) {
        try {
            String fileName=file.getOriginalFilename();
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream=file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESSKEY_ID, ConstantVodUtils.ACCESSKEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                return response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                return response.getVideoId();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void removeAliyunVideoById(String id) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESSKEY_ID, ConstantVodUtils.ACCESSKEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(20001,"视频删除失败");
        }


    }

    @Override
    public void removeMoreVideo(List<String> videoIdList) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESSKEY_ID, ConstantVodUtils.ACCESSKEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String ids = StringUtils.join(videoIdList, ",");
            request.setVideoIds(ids);
            client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new GuliException(20001,"视频删除失败");
        }
    }

    @Override
    public String getPlayAuth(String id) {
        String accesskeyId = ConstantVodUtils.ACCESSKEY_ID;
        String accesskeySecret = ConstantVodUtils.ACCESSKEY_SECRET;

        try {
            //创建初始化对象
            DefaultAcsClient cl = InitObject.initVodClient(accesskeyId,accesskeySecret);
            //创建获取视频地址request对象和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request对象设置视频id值
            request.setVideoId(id);

            GetVideoPlayAuthResponse response = cl.getAcsResponse(request);

            //获取视频播放凭证
            return response.getPlayAuth();

        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"获取视频凭证失败");
        }
    }
}
