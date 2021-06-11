package responsetype;

import allentity.Course;
import com.google.gson.Gson;
import dao.CourseDAO;
import requestutils.Request;
import responseutils.Response;
import spi.ResponseDecoder;
import spi.ResponseType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@ResponseType("/POSTJSON")
public class POSTJsonResponse implements ResponseDecoder {

    @Override
    public Response sendResponse(Response response, Request request) throws RuntimeException {

        Gson gson = new Gson();
        CourseDAO courseDAO = new CourseDAO();

        String json = new String(request.getContent(), Charset.forName("UTF-8"));
        Course course = courseDAO.addCourse(new Course(gson.fromJson(json, Course.class).getCourseTitle()));
        if (course == null) {
            response.setHeader("HTTP/1.1 403 FORBIDDEN\r\nContent-length: 0\r\n\r\n");
            response.setContent(new byte[0]);
        } else
            processDBRequest(response, json, request, gson, courseDAO);
        return response;
    }

    private void processDBRequest(Response response, String json, Request request, Gson gson, CourseDAO courseDAO) {

        courseDAO.addCourse(new Course(gson.fromJson(json, Course.class).getCourseTitle()));
        Course course = courseDAO.findCourse(gson.fromJson(json, Course.class).getCourseTitle());
        json = gson.toJson(course);
        response.setContentType("application/json");

        byte[] data = json.getBytes(StandardCharsets.UTF_8);
        response.setContentLength((long) data.length);
        if (data.length != 0) {
            response.setHeader("HTTP/1.1 202 Accepted\r\nContent-Type: "
                    + response.getContentType() + "\r\nContent-length: "
                    + response.getContentLength() + "\r\n\r\n");
            response.setContent(data);
        }
    }
}
