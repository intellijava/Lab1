package spi;


import requestutils.Request;
import responseutils.Response;

import java.io.IOException;

public interface ResponseDecoder {
    Response sendResponse(Response response, Request request) throws IOException;
}