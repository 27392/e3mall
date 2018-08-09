package cn.haohaoli.controller;

import cn.haohaoli.pojo.PicUploadResult;
import cn.haohaoli.utils.TxCosClient;
import com.qcloud.cos.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 图片控制器
 * @author Liwenhao
 * @date 2018/8/6 16:33
 */
@RestController
@RequestMapping("/pic")
public class PictureController {

    @Value("${cos.secretId}")
    private String secretId;
    @Value("${cos.secretKey}")
    private String secretKey;
    @Value("${cos.regionName}")
    private String regionName;
    @Value("${cos.bucketName}")
    private String bucketName;
    @Value("${cos.pic.upload}")
    private String uploadPath;

    @PostMapping("/upload")
    public PicUploadResult uploadFile(MultipartFile uploadFile){
        if (uploadFile == null) {
            return PicUploadResult.error("文件不存在");
        }
        TxCosClient txCosClient = null;
        try {
            // 对象键（Key）是对象在存储桶中的唯一标识。
            // 例如，在对象的访问域名 `bucket1-1250000000.cos.ap-guangzhou.myqcloud.com/doc1/pic1.jpg` 中，
            // 对象键为 doc1/pic1.jpg, 详情参考 [对象键](https://cloud.tencent.com/document/product/436/13324)
            String key = uploadPath + uploadFile.getOriginalFilename();
            //初始化客户端 cosClient
            txCosClient = new TxCosClient(secretId,secretKey,regionName);
            //构建ObjectMetadata对象
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(uploadFile.getContentType());
            objectMetadata.setContentLength(uploadFile.getSize());
            //开始上传文件
            String url = txCosClient.upload(uploadFile.getInputStream(), bucketName, key, objectMetadata);
            return PicUploadResult.success(url);
        } catch (IOException e) {
            e.printStackTrace();
            return PicUploadResult.error("上传失败！");
        } finally {
            if (txCosClient != null) {
                txCosClient.close();
            }
        }
    }
}
