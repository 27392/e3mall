package cn.haohaoli.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.InputStream;

/**
 * 腾讯云Cos工具类
 * @author Liwenhao
 * @date 2018/8/6 16:41
 */
public class TxCosClient {

    private final String COS_FILE_URL = "https://%s.cos.%s.myqcloud.com%s";

    private COSClient cosclient;

    public TxCosClient(String secretId, String secretKey, String regionName) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(regionName));
        // 3 生成cos客户端
        cosclient = new COSClient(cred, clientConfig);
    }

    /**
     * 简单文件上传, 最大支持 5 GB, 适用于小文件上传
     * @param inputStream
     * @param bucketName
     * @param metadata
     * @param key
     * @return
     */
    public String upload(InputStream inputStream, String bucketName, String key, ObjectMetadata metadata){
        // 指定要上传到 COS 上对象键
        // 对象键（Key）是对象在存储桶中的唯一标识。例如，在对象的访问域名 `bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg` 中，对象键为 doc1/pic1.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream,metadata);
        PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
        return getUrl(bucketName, key);
    }

    /**
     * 获取地址
     * 例如 bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg
     * @param bucketName
     * @param key
     * @return
     */
    public String getUrl(String bucketName, String key){
        String regionName = cosclient.getClientConfig().getRegion().getRegionName();
        return String.format(COS_FILE_URL, bucketName, regionName, key);
    }

    /**
     * 关闭客户端
     */
    public void close(){
        cosclient.shutdown();
    }
}
