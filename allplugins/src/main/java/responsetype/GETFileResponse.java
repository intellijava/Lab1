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

@ResponseType("/GETFILE")
public class GETFileResponse implements ResponseDecoder {

    public Response sendResponse(Response response, Request request) throws IOException {
        byte[] data = new byte[0];
        File file = Path.of("core", "target", "web", request.getUrl().trim()).toFile();
        //File file = Path.of("app", "web", fileName).toFile();
        if (!(file.exists() && !file.isDirectory())) {
            response.setHeader("HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n");
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
            response.setContentLength(data.length);
            response.setHeader("HTTP/1.1 200 OK\r\nContent-Type: "
                    + response.getContentType() + "\r\nContent-length: "
                    + response.getContentLength() + "\r\n\r\n");
            response.setContent(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}