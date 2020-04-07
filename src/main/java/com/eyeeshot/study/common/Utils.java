package com.eyeeshot.study.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Utils {

  // Object를 json String 형태로 변환
  public static String convertObjectToJsonString(Object object) {
    return convertObjectToJsonString(object, false);
  }

  // Object를 json String 형태로 이쁘게 변환
  public static String convertObjectToJsonString(Object object, boolean pretty) {

    Gson gson = null;
    if (pretty) {
      gson = new GsonBuilder().setPrettyPrinting().create();
    } else {
      gson = new GsonBuilder().create();
    }

    return gson.toJson(object);
  }

  // json string to object
  public static <T> T convertJsonStringToObject(String jsonString, Class<T> valueType) {

    T obj = null;
    try {

      if (jsonString == null || jsonString.isEmpty()) {
        return null;
      }
      Gson gson = new GsonBuilder().create();
      obj = gson.fromJson(jsonString, valueType);

    } catch (Exception e) {
      log.debug("", e);
    }

    return (T) obj;
  }

}
