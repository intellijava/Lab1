package client;

import java.io.IOException;
import java.net.URISyntaxException;

public class Client {

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("-------------------------HTTPGet with different Files and formats-----------------------------");
        var fileNameString = new String[]{"test.html", "test.jpg", "test.png", "Noexist.txt", "test.txt", "java.jpg", "java.png"};
        for (String fileName : fileNameString) {
            DoGet.httpGETFile(fileName);
        }
        System.out.println("--------------------------HTTPGet with Json response-------------------------------------------");
        DoGet.httpGETJson();

        System.out.println("--------------------------HTTPGet with Text response-------------------------------------------");
        DoGet.httpGETJson();
        System.out.println("\n\n");

        System.out.println("-------------------------HTTPHead with different Files and formats-----------------------------");
        var fileName = new String[]{"test.html", "test.jpg", "test.png", "Noexist.txt", "test.txt", "java.jpg", "java.png"};
        for (String file : fileName) {
            DoHead.httpHEADFile(file);
        }
        System.out.println("--------------------------HTTPHead with Json response-------------------------------------------");
        DoHead.httpHEADJson();

        System.out.println("--------------------------HTTPHead with Text response-------------------------------------------");
        DoHead.httpHEADJson();
        System.out.println("\n\n");


        System.out.println("--------------------------HTTPPost with Json response-------------------------------------------");
        DoPost.httpPOSTJson();

        System.out.println("--------------------------HTTPPost with Text response-------------------------------------------");
        DoPost.httpPOSTText();
        System.out.println("\n\n");
    }
}