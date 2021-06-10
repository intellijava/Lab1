package responseutils;

import org.junit.jupiter.api.Test;
import requestutils.RequestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//class ResponseUtilsTest {
//
//    @Test
//    void testResponseTypeGetFile(){
//         var response= ResponseUtils.parseHttpResponseType(RequestUtils.parseHttpRequest("""
//                GET /index.html HTTP/1.1\r\n \
//                Host: www.example.com\r\n \
//                \r\n \
//                """));
//
//        assertThat(response.getType()).isEqualTo(HTTPResponseType.GETFILE);
//    }
//
//    @Test
//    void testResponseTypeGetJson(){
//         var response= ResponseUtils.parseHttpResponseType(RequestUtils.parseHttpRequest("""
//                GET /courses HTTP/1.1\r\n \
//                Host: www.example.com\r\n \
//                \r\n \
//                """));
//
//        assertThat(response.getType()).isEqualTo(HTTPResponseType.GETJSON);
//    }
//    @Test
//    void testResponseTypePOSTJson(){
//         var response= ResponseUtils.parseHttpResponseType(RequestUtils.parseHttpRequest("""
//                POST /courses HTTP/1.1\r\n \
//                Host: www.example.com\r\n \
//                \r\n \
//                """));
//
//        assertThat(response.getType()).isEqualTo(HTTPResponseType.POSTJSON);
//    }
//    @Test
//    void testResponseTypeHEADJson(){
//         var response= ResponseUtils.parseHttpResponseType(RequestUtils.parseHttpRequest("""
//                HEAD /courses HTTP/1.1\r\n \
//                Host: www.example.com\r\n \
//                \r\n \
//                """));
//
//        assertThat(response.getType()).isEqualTo(HTTPResponseType.HEADJSON);
//    }
//
//}