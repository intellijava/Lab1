package requestutils;

import java.util.HashMap;
import java.util.Map;

public class Request {

    Map<String,String> headers = new HashMap<>();
    HTTPRequestType type;
    String url;
    Map<String,String> urlParams = new HashMap<>();

    public String getContentType() {
        return contentType;
    }

    String contentType;
    Long contentLength;
    byte[] content;

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

