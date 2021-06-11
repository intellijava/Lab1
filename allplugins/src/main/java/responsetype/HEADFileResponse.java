package responsetype;

import requestutils.Request;
import responseutils.Response;
import spi.ResponseDecoder;
import spi.ResponseType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ResponseType("/HEADFILE")
public class HEADFileResponse implements ResponseDecoder {

    public Response sendResponse(Response response, Request request){

//        File file = Path.of("core", "target", "web", request.getUrl().trim()).toFile();
        File file = Path.of("app", "web", request.getUrl().trim()).toFile();
        if (!(file.exists() && !file.isDirectory())) {
            response.setHeader("HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n");
            response.setContent(new byte[0]);
        } else {
            getFileResponse(response, file);
        }
        return response;
    }

    private void getFileResponse(Response response, File file) {
        byte[] data;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            data = new byte[(int) file.length()];
            fileInputStream.read(data);
            response.setContentType(Files.probeContentType(file.toPath()));
            response.setContentLength((long) data.length);
            response.setHeader("HTTP/1.1 200 OK\r\nContent-Type: "
                    + response.getContentType() + "\r\nContent-length: "
                    + response.getContentLength() + "\r\n\r\n");
            response.setContent(new byte[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}