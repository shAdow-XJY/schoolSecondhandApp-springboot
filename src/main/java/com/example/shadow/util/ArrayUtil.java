package com.example.shadow.util;

import java.util.List;

public class ArrayUtil {
    public static Integer[] stringToInter(String[] strings) {
        Integer[] integers = new Integer[strings.length];
        for (int i = 0; i < strings.length; i++) {
            integers[i] = Integer.valueOf(strings[i]);
        }
        return integers;
    }

    public static String joinElements(List<String> plaintiffs) {
        StringBuilder stringBuilder = new StringBuilder();
        if (plaintiffs.size() == 0) return stringBuilder.toString();
        stringBuilder.append(plaintiffs.get(0));
        for (int i = 1; i < plaintiffs.size(); i++) {
            stringBuilder.append("," + plaintiffs.get(i));
        }
        return stringBuilder.toString();
    }
}
