package converters.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.AppException;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Optional;

public abstract class JsonConverter<T> {

  private final String jsonFilename;
  private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  public JsonConverter(String jsonFilename) {
    this.jsonFilename = jsonFilename;
  }

  public Optional<T> fromJson() {
    try (FileReader fileReader = new FileReader(jsonFilename)) {
      return Optional.of(gson.fromJson(fileReader, type));
    } catch (Exception e) {
      System.err.println(Arrays.toString(e.getStackTrace()));
      e.printStackTrace();
      System.out.println(JsonConverter.class.getResource(""));
      throw new AppException("FROM JSON CONVERSION EXCEPTION");
    }
  }

  public String getJsonFilename() {
    return jsonFilename;
  }
}
