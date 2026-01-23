package vyom.dunk.app.clients;

import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import vyom.dunk.app.resources.EnemyPayload;

public class EnemyMicroserviceClient {
  private final HttpClient http;
  private final String baseUrl;

  ObjectMapper mapper = new ObjectMapper();

  public EnemyMicroserviceClient(String baseUrl) {
    this.baseUrl = baseUrl;
    this.http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
  }

  public String generateEnemyJson(String userText) {
    try {
      if (userText == null || userText.trim().isEmpty()) {
        throw new IllegalArgumentException("enemy_input vacío");
      }

      Map<String, Object> body = Map.of(
          "prompt_user", userText,
          "min_damage", 10,
          "max_damage", 25);
      String payload = mapper.writeValueAsString(body);

      HttpRequest req = HttpRequest.newBuilder()
          .uri(URI.create(baseUrl + "/generate-enemy")) // sin slash, coincide con tu decorator
          .version(HttpClient.Version.HTTP_1_1)
          .timeout(Duration.ofSeconds(20))
          .header("Content-Type", "application/json; charset=UTF-8")
          .header("Accept", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
          .build();

      // System.out.println("URL: " + req.uri());
      // System.out.println("Payload: " + payload);
      HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
      // System.out.println("STATUS: " + res.statusCode());
      // System.out.println("HEADERS: " + res.headers().map());
      // System.out.println("BODY: " + res.body());

      if (res.statusCode() >= 300) {
        throw new RuntimeException("Microservicio respondio " + res.statusCode() + ": " + res.body());
      }

      return res.body();

    } catch (Exception e) {
      throw new RuntimeException("No se pudo generar enemigo (microservicio): " + e.getMessage(), e);
    }
  }

}
