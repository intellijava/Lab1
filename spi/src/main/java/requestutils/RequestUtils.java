package requestutils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestUtils {

    public static Request parseHttpRequest(String input) {

        var request = new Request();
        request.headers = parseHeaders(input);
        request.type = parseHttpRequestType(input);
        request.url = parseUrl(input);
        request.urlParams = parseUrlParams(input);
        request.contentType  = request.headers.entrySet()
                .stream()
                .filter(e -> e.getKey().equalsIgnoreCase("content-type"))
                .map(Map.Entry::getValue).findFirst().orElse("");
        request.contentLength = Long.valueOf(Objects.requireNonNull(request.headers.entrySet()
                .stream()
                .filter(e -> e.getKey().equalsIgnoreCase("content-length"))
                .map(Map.Entry::getValue).findFirst().orElse("0")));

        return request;
    }

    private static HashMap<String,String> parseHeaders(String input){
        HashMap<String, String> headers = new HashMap<>();
        String[] result = (input).split("\r\n");
         for (int i = 1 ; i < result.length; i++) {
             System.out.println(result[i]);
            var header = result[i].split(":", 2);
            if(header.length > 1)
            headers.put(header[0].trim(),header[1].trim());
        }
        return headers;
    }

//    private static Integer parseContentLength(String input) {
//        String[] params = (urlDecoding(input)).split(" ");
//        for (int i = 0; i < params.length - 1; i++) {
//            if (params[i].trim().equalsIgnoreCase("Content-Length:")) {
//                Integer temp = Integer.parseInt((params[i + 1]).trim());
//                return temp;
//            }
//        }
//        return 0;
//    }

    private static String parseUrl(String input) {
        String[] result = input.split(" ");
        String[] params = result[1].split("\\?");

        return urlDecoding(params[0]);
    }

    private static HashMap< String, String > parseUrlParams(String input) {
        HashMap< String, String > request = new HashMap<>();
        String[] result = input.split(" ");
        String[] params = result[1].split("\\?");
        if (params.length > 1) {
            String[] paramsPair = params[1].split("&");
            for (String s : paramsPair) {
                String[] paramsValues = s.split("=");
                request.put(urlDecoding(paramsValues[0]).trim(), urlDecoding(paramsValues[1]).trim());
            }
            return request;
        }
        return request;
    }

    private static String urlDecoding(String input) {
        String decodedUrl = "";
        decodedUrl = URLDecoder.decode(input, StandardCharsets.UTF_8);
        return decodedUrl;
    }

    public static HTTPRequestType parseHttpRequestType(String input) {
        if (input.startsWith("G"))
            return HTTPRequestType.GET;
        else if (input.startsWith("HE"))
            return HTTPRequestType.HEAD;
        else if (input.startsWith("PO"))
            return HTTPRequestType.POST;

        throw new RuntimeException("Invalid type");
    }

    //Example of switch expressions to handle all possible enum cases without needing default case.
    public static String handleRequest(Request request) {
        return switch (request.type) {
            case GET -> "GET";
            case HEAD -> "HEAD";
            case POST -> "POST";
        };
    }
}
