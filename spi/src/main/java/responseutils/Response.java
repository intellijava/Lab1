package responseutils;

public class Response {
    String header;
    Integer contentLength;
    String contentType;
    byte[] content;

    public HTTPResponseType getType() {
        return type;
    }

    public void setType(HTTPResponseType type) {
        this.type = type;
    }

    HTTPResponseType type;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
