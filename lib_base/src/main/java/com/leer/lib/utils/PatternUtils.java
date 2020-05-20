package com.leer.lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    public static boolean checkPhone(String phone) {
        String regexStr = "(^1((3[0-9]|4[5-8]|5[0-35-9]|6[67]|7[015-8]|8[0-9]|9[189])\\d{8}$))|" +
                "(^1(349|4[14]0|740)\\d{7}$)";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(phone);

        return matcher.matches();
    }
}
