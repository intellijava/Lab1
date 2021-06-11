package responseutils;

import requestutils.Request;

public class ResponseUtils {

    public static Response parseHttpResponseType(Request request) {
        var response = new Response();

        switch (request.getType()) {
            case GET:
                if (request.getUrl().contains("."))
                    response.type = HTTPResponseType.GETFILE;
                else if (request.getUrl().contains("/courses"))
                    response.type = HTTPResponseType.GETJSON;
                else if (request.getUrl().contains("/"))
                    response.type = HTTPResponseType.GETTEXT;
                break;
            case POST:
                if (request.getUrl().contains("/courses")&&request.getContentType().equals("application/json"))
                    response.type = HTTPResponseType.POSTJSON;
                else if (request.getUrl().contentEquals("/"))
                    response.type = HTTPResponseType.POSTTEXT;
                break;
            case HEAD:
                if (request.getUrl().contains("."))
                    response.type = HTTPResponseType.HEADFILE;
                else if (request.getUrl().contains("/courses")&&request.getContentType().equals("application/json"))
                    response.type = HTTPResponseType.HEADJSON;
                else if (request.getUrl().contains("/"))
                    response.type = HTTPResponseType.HEADTEXT;
                break;
        }
    return response;
    }
}
