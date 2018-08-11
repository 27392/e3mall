package cn.haohaoli.common.pojo;

/**
 * 文件上传对象
 * @author Liwenhao
 * @date 2018/8/6 19:15
 */
public class PicUploadResult {

    private int error;
    private String url;
    private String message;

    public static PicUploadResult success(String url) {
        PicUploadResult picUploadResult = new PicUploadResult();
        picUploadResult.setUrl(url);
        return picUploadResult;
    }

    public static PicUploadResult error(String message) {
        PicUploadResult picUploadResult = new PicUploadResult();
        picUploadResult.setError(1);
        picUploadResult.setMessage(message);
        return picUploadResult;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
