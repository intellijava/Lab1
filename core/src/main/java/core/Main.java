package core;

import requestutils.*;
import responseutils.*;
import spi.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.nonNull;

public class Main {
    static ExecutorService executorService = Executors.newCachedThreadPool();
    static List< ResponseDecoder > listResponseServiceLoader = new ArrayList<>();
    public static String requestInputString = " ";

    public static void main(String[] args) {


        ServiceLoader< ResponseDecoder > responseServiceLoader = ServiceLoader.load(ResponseDecoder.class);
        responseServiceLoader.forEach(listResponseServiceLoader::add);

        try (ServerSocket serverSocket = new ServerSocket(5050)) {
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Connection from : " + client.getInetAddress());
                executorService.submit(() -> handleConnection(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket client) {
        try {
            var inputFromClient = client.getInputStream();
            var request = readRequest(inputFromClient);

            var outputToClient = client.getOutputStream();
            var response = ResponseUtils.parseHttpResponseType(request);

            //Routing
            response = generateResponse(request, response);

            sendResponse(client, inputFromClient, outputToClient, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Request readRequest(InputStream inputStream) throws IOException {
        int count = 0;
        StringBuilder result = new StringBuilder();
        do {
            result.append((char) inputStream.read());
            count++;
        } while (inputStream.available() > 0);
        var separatorIndex = result.toString().indexOf("\r\n\r\n");
        var request = RequestUtils.parseHttpRequest(result.toString().substring(0, separatorIndex));
        request.setContent(result.toString().substring(separatorIndex + 4).getBytes(StandardCharsets.UTF_8));

        return request;
    }

    private static void sendResponse(Socket client, InputStream inputFromClient, OutputStream outputToClient, Response response) throws IOException {
        outputToClient.write(response.getHeader().getBytes());
        outputToClient.write(response.getContent());
        outputToClient.flush();
        inputFromClient.close();
        outputToClient.close();
        client.close();
    }

    public static Response generateResponse(Request request, Response response) throws IOException {
        for (ResponseDecoder responseDecoder : listResponseServiceLoader) {
            ResponseType annotation = responseDecoder.getClass().getAnnotation(ResponseType.class);
            if (nonNull(annotation) && annotation.value().equals("/" + response.getType())) {
                response = responseDecoder.sendResponse(response, request);
                break;
            }
        }
        return response;
    }
}

