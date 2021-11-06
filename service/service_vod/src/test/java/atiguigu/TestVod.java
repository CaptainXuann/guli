package atiguigu;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

import static atiguigu.InitObject.initVodClient;

/**
 * @author
 */
public class TestVod {

    public static void main(String[] argv) {
//        String accessKeyId = "LTAI5tHGvwvptwE2zKM3XzFm";
//        String accessKeySecret = "nWwEOgd4ei0oRaggwMplRWwCoCcliF";
//        String title = "6 - What If I Want to Move Faster"; //上传之后文件的名称
//        String fileName = "F:\\BaiduNetdiskDownload\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
//        request.setPartSize(2 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//        }
        getPlayAuth();
    }

    public static void getPlayAuth(){
        DefaultAcsClient client = initVodClient("LTAI5tHGvwvptwE2zKM3XzFm", "nWwEOgd4ei0oRaggwMplRWwCoCcliF");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = getVideoPlayAuth(client);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("56363a4a09344ccabe20cebd30ddd8a3");
        return client.getAcsResponse(request);
    }


    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId("视频ID");
        return client.getAcsResponse(request);
    }

    public static void getPlayUrl() throws Exception{
        //1、根据视频id获取视频播放地址
        //创建初始化对象
        DefaultAcsClient client = initVodClient("LTAI5tHGvwvptwE2zKM3XzFm", "nWwEOgd4ei0oRaggwMplRWwCoCcliF");
        //创建获取视频地址request对象和response对象
        GetPlayInfoRequest request=new GetPlayInfoRequest();
        GetPlayInfoResponse response=new GetPlayInfoResponse();
        //向request对象设置视频id值
        request.setVideoId("9a55e0ac522b4dafa26c29d8789f3e60");
        //调用初始化对象里面的方法传递request，获取数据
        response=client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            //PlayInfo.PlayURL = https://outin-5e688ec47b2c11ebb00500163e06123c.oss-cn-shanghai.aliyuncs.com/sv/52a57879-177f1fd5033/52a57879-177f1fd5033.mp4?Expires=1614680252&OSSAccessKeyId=LTAIrkwb21KyGjJl&Signature=FPlVfGbIDVGuvR3W8f2K4QcpATw%3D
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");//VideoBase.Title = 6 - What If I Want to Move Faster.mp4
    }
}
