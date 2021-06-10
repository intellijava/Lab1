package responsetype;

import allentity.Course;
import com.google.gson.Gson;
import dao.CourseDAO;
import responseutils.Response;
import spi.ResponseDecoder;
import requestutils.Request;
import spi.ResponseType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@ResponseType("/GETJSON")
public class GETJsonResponse implements ResponseDecoder {

    public Response sendResponse(Response response, Request request) throws IOException {
        Gson gson = new Gson();
        String json = "";
        CourseDAO courseDAO = new CourseDAO();
        List< Course > courseList = null;
        if (request.getUrlParams().isEmpty()) {
            courseList = courseDAO.showAllCourses();
            json = gson.toJson(courseList);
        } else if (request.getUrlParams().size() == 1) {
            Map.Entry< String, String > entry = request.getUrlParams().entrySet().iterator().next();
            Course course = courseDAO.findCourse(entry.getValue());
            if (course == null)
                response.setHeader("HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n");
            else
                json = gson.toJson(course);
        } else {
            response.setHeader("HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n");
        }
        byte[] data = json.getBytes(StandardCharsets.UTF_8);
        response.setContentType("application/json");
        response.setContentLength(data.length);
        if (data.length != 0) {
            response.setHeader("HTTP/1.1 200 OK\r\nContent-Type: "
                    + response.getContentType() + "\r\nContent-length: "
                    + response.getContentLength() + "\r\n\r\n");
            response.setContent(data);
        }
        return response;
    }
}
