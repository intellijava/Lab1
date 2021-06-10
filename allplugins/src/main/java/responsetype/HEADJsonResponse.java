package responsetype;

import requestutils.Request;
import responseutils.Response;
import spi.ResponseDecoder;
import spi.ResponseType;

import java.io.IOException;
@ResponseType("/HEADJSON")
public class HEADJsonResponse implements ResponseDecoder {
    @Override
    public Response sendResponse(Response response, Request request) throws IOException {
        System.out.println("===========================================");
        System.out.println("===========================================");
        System.out.println("Its not implemented yet");
        System.out.println("===========================================");
        System.out.println("===========================================");
        return null;
    }
}
