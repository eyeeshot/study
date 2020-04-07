package com.eyeeshot.study.common;

public class StringUtils {
  public static boolean isEmpty(String str) {
    if (str == null || str.isEmpty()) {
      return true;
    }
    return false;
  }
}