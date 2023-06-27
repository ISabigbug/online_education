import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

/**
 * @Project: online_education
 * @Package: PACKAGE_NAME
 * @Description:
 */
public class testvod {
    //初始化
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    @Test
    //根据视频ID获取视频播放凭证
    public void getPlayAuth() throws com.aliyuncs.exceptions.ClientException {
        DefaultAcsClient client = initVodClient("LTAI5t6VMpCnb414Ht4rMNwA", "COHrmvMp4NtQnlIwbnDh3gUvVlGWuD");
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("d00d3840d75671eda0b40764a0ec0102");
        response = client.getAcsResponse(request);
        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

}
