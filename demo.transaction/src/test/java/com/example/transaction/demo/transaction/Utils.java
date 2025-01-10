package com.example.transaction.demo.transaction;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Utils {

  private static final ObjectMapper objectMapper = new ObjectMapper()
      .registerModule(new JavaTimeModule());

  public static <D> List<D> convertToListFromJson(String fileName, Class<D> destinationClass)
      throws IOException {
    InputStream
        inputStream = Utils.class.getClassLoader().getResourceAsStream("./mock/" + fileName);
    return objectMapper.readValue(inputStream, objectMapper.getTypeFactory()
        .constructCollectionType(List.class, destinationClass));
  }
}
