package address.sendMessage;

import com.sun.javafx.fxml.builder.URLBuilder;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class SendToTelegram {

    String myId = "384947138";

    public void botExample(String message) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
        String uri = String.format("https://api.telegram.org/bot1275980447:AAFuFEvJwuLT_UOpycOTkbA8WRfQxcyGVX4/sendMessage?chat_id=%s&text=%s", myId, URLEncoder.encode(message, StandardCharsets.UTF_8));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response =
                null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            System.out.println(response.statusCode());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
