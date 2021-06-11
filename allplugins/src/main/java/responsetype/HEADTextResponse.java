package responsetype;

import requestutils.Request;
import responseutils.Response;
import spi.ResponseDecoder;
import spi.ResponseType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ResponseType("/HEADTEXT")
public class HEADTextResponse implements ResponseDecoder {

    @Override
    public Response sendResponse(Response response, Request request){
        byte[] data = ("A simple plain text!").getBytes(StandardCharsets.UTF_8);
        response.setContentType("text/plain");
        response.setContentLength((long) data.length);
        if (data.length != 0) {
            response.setHeader("HTTP/1.1 200 OK\r\nContent-Type: "
                    + response.getContentType() + "\r\nContent-length: "
                    + response.getContentLength() + "\r\n\r\n");
            response.setContent(new byte[0]);
        }
        return response;
    }
}
