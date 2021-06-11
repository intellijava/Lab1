package responsetype;

import requestutils.Request;
import responseutils.Response;
import spi.ResponseDecoder;
import spi.ResponseType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@ResponseType("/POSTTEXT")
public class POSTTextResponse implements ResponseDecoder {
    @Override
    public Response sendResponse(Response response, Request request){

        byte[] data = request.getContent();

        if (data.length != 0) {
            response.setContentType("text/plain");
            response.setContentLength((long) data.length);
            response.setHeader("HTTP/1.1 200 OK\r\nContent-Type: "
                    + response.getContentType() + "\r\nContent-length: "
                    + response.getContentLength() + "\r\n\r\n");
            response.setContent(data);
        }
        return response;
    }
}
