package com.cdweb.backend.common;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

public class Utils {
    public static String removeAccent(String s) {
        String temp = s.trim().toLowerCase();
        temp = Normalizer.normalize(temp, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = pattern.matcher(temp).replaceAll("");
        return temp.replaceAll("đ", "d");
    }

    public static String transNameToCode(String s) {
        String removeAccent = removeAccent(s);

        return removeAccent.replaceAll(" ", "-");
    }

    public static String formatNumber(Double d) {
        Locale locale  = new Locale("vi" , "VN");
        String pattern = "###.##";

        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(locale);
        decimalFormat.applyPattern(pattern);

        String format = decimalFormat.format(d);
        return format;
    }

    public static String getUniqueStringId(String productVariantName) {
        String[] list = removeAccent(productVariantName).split("-");
        String str1 = "";
        for (String str : list) {
            str1 = str1 + str;
        }
        char[] listChars = str1.toCharArray();
        for (int i = 0; i < listChars.length - 1; i++) {
            for (int j = i + 1; j < listChars.length; j++) {
                if (listChars[i] > listChars[j]) {
                    char temp = listChars[i];
                    listChars[i] = listChars[j];
                    listChars[j] = temp;
                }
            }
        }
        return String.valueOf(listChars);
    }

}
