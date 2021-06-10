package requestutils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RequestUtilsTest {
    @Test
    void requestTypeGetWithRootUrl() {
        var request = RequestUtils.parseHttpRequest("""
                GET / HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """);
        assertThat(request.url).isEqualTo("/");
    }

    @Test
    void requestWithFilePath() {
        var request = RequestUtils.parseHttpRequest("""
                GET /index.html HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """);
        assertThat(request.url).isEqualTo("/index.html");
        assertThat(request.type).isEqualTo(HTTPRequestType.GET);
    }

    @Test
    void requestTypeHead() {
        Request request = RequestUtils.parseHttpRequest("""
                HEAD /index.html HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """);
        assertThat(request.type).isEqualTo(HTTPRequestType.HEAD);
    }

    @Test
    void requestTypeGetWithOneUrlParameter() {
        Request request = RequestUtils.parseHttpRequest("""
                GET /products?id=23 HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """);
        assertThat(request.type).isEqualTo(HTTPRequestType.GET);
        assertThat(request.url).isEqualTo("/products");
        assertThat(request.urlParams).containsEntry("id", "23");
    }

    @Test
    void requestTypePOSTWithTwoUrlParameters() {
        Request request = RequestUtils.parseHttpRequest("""
                POST /products?id=23&order=ascend HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """);
        assertThat(request.type).isEqualTo(HTTPRequestType.POST);
        assertThat(request.url).isEqualTo("/products");
        assertThat(request.urlParams).containsEntry("id", "23").containsEntry("order", "ascend");
    }

    @Test
    void requestTypeGETWithUrlParameterIncludingSpace() {
        Request request = RequestUtils.parseHttpRequest("""
                GET /products?text=Hello+there HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """);
        assertThat(request.type).isEqualTo(HTTPRequestType.GET);
        assertThat(request.url).isEqualTo("/products");
        assertThat(request.urlParams).containsEntry("text", "Hello there");
    }

    @Test
    void requestTypeGETWithUrlParameterIncludingSpaceUsingUrlEncoding() {
        Request request = RequestUtils.parseHttpRequest("""
                GET /products?t%20e%20x%20t=M%C3%A5ste%20fixa HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """);
        assertThat(request.type).isEqualTo(HTTPRequestType.GET);
        assertThat(request.url).isEqualTo("/products");
        //assertThat(request.urlParams).containsEntry("t e x t", "Måste fixa");
    }

    @Test
    void requestUrlWithSpaces() {
        Request request = RequestUtils.parseHttpRequest("""
                GET /a%20folder/first%20document.pdf HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """);
        assertThat(request.type).isEqualTo(HTTPRequestType.GET);
        assertThat(request.url).isEqualTo("/a folder/first document.pdf");
        assertThat(request.urlParams).isEmpty();
    }

    //This test needs new fields on our RequestUtils.Request object to compile.
    //Also observe that the content in the body normaly can't be read as a string for two reasons.
    //1. It's not always of type text/string. Might be binary data when uploading a image file.
    //2. There is no lineending. Instead we must use the Content-Length: value
    //   as a guide how many bytes we need to read from InputStream.
    //Maybe we should store body content as a byte[] in our request objekt.
    //https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers
    @Test
    void requestPostWithJsonInBody() {
        Request request = RequestUtils.parseHttpRequest("""
                POST /upload HTTP/1.1\r\n \
                Host: localhost:5050\r\n \
                User-Agent: insomnia/2021.3.0\r\n \
                Cookie: JSESSIONID=490B71AEC6F7B91E31BBDB1037F53B26\r\n \
                Content-Type: application/json\r\n \
                Accept: */*\r\n \
                Content-Length: 25\r\n \
                \r\n \
                {"name":"hej","title":12}\
                """);
        assertThat(request.type).isEqualTo(HTTPRequestType.POST);
        assertThat(request.url).isEqualTo("/upload");
        assertThat(request.urlParams).isEmpty();
        assertThat(request.contentType).isEqualTo("application/json");
        assertThat(request.contentLength).isEqualTo(25);
        //assertThat(request.content).isEqualTo("{\"name\":\"hej\",\"title\":12}");
    }

    //In this test we have an Authorization header in the request.
    //Normaly used for giving the server some information from which we can be authorized to access an url.
    @Test
    void requestGetWithAuthorization() {
        Request request = RequestUtils.parseHttpRequest("""
                GET /secret HTTP/1.1\r\n \
                Host: localhost:5050\r\n \
                Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\r\n \
                \r\n \
                """);
        assertThat(request.type).isEqualTo(HTTPRequestType.GET);
        assertThat(request.url).isEqualTo("/secret");
        assertThat(request.headers).containsEntry("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
    }
}