package requestutils;

import java.util.HashMap;
import java.util.Map;

public class Request {

    Map<String,String> headers = new HashMap<>();
    HTTPRequestType type;
    String url;
    Map<String,String> urlParams = new HashMap<>();

    String contentType;
    Long contentLength;
    byte[] content;

    public Map< String, String > getHeaders() {
        return headers;
    }

    public void setHeaders(Map< String, String > headers) {
        this.headers = headers;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlParams(Map< String, String > urlParams) {
        this.urlParams = urlParams;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public HTTPRequestType getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public Map< String, String > getUrlParams() {
        return urlParams;
    }

    public byte[] getContent() {
        return content;
    }
    public void setType(HTTPRequestType type) {
        this.type = type;
    }

}

