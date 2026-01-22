package vyom.dunk.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import vyom.dunk.app.resources.EnemyPayload;

public class JsonUtil {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static EnemyPayload parseEnemyPayload(String json) {
    try {
      return MAPPER.readValue(json, EnemyPayload.class);

    } catch (Exception e) {
      throw new IllegalArgumentException("JSON invalido: " + e.getMessage(), e);
    }
  }
}
