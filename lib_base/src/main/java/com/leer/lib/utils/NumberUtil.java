package com.leer.lib.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

    private static NumberUtil numberUtil;

    public static NumberUtil getInstance() {
        if (numberUtil == null) {
            numberUtil = new NumberUtil();
        }
        return numberUtil;
    }

    private NumberUtil() {
        numMap.put("零", 0);
        numMap.put("一", 1);
        numMap.put("二", 2);
        numMap.put("三", 3);
        numMap.put("四", 4);
        numMap.put("五", 5);
        numMap.put("六", 6);
        numMap.put("七", 7);
        numMap.put("八", 8);
        numMap.put("九", 9);

        unitNumMap.put("亿", 100000000);
        unitNumMap.put("万", 10000);

        unitMap.put("千", 1000);
        unitMap.put("百", 100);
        unitMap.put("十", 10);

        numRegex = "[";
        for (String s : numMap.keySet()) {
            numRegex += encodeUnicode(s);
        }
        for (String s : unitMap.keySet()) {
            numRegex += encodeUnicode(s);
        }
        for (String s : unitNumMap.keySet()) {
            numRegex += encodeUnicode(s);
        }
        numRegex += "]+";
    }


    private static final String zhZero = "零";
    private static final String zhOne = "一";
    private static final String zhTen = "十";

    private Map<String, Integer> numMap = new HashMap<String, Integer>();

    private Map<String, Integer> unitNumMap = new LinkedHashMap<String, Integer>();

    private Map<String, Integer> unitMap = new LinkedHashMap<String, Integer>();

    private String numRegex;

    public String zhNumToInt(String text) {
        for (int i = 0; i < text.length(); i++) {
            String c = String.valueOf(text.charAt(i));
            switch (c) {
                case "零":
                    text = text.replaceFirst(c, "0");
                    break;
                case "一":
                    if (i + 1 < text.length()) {
                        String s = String.valueOf(text.charAt(i + 1));
                        if (s.equals("点") || s.equals("些")) break;
                    }
                    text = text.replaceFirst(c, "1");
                    break;
                case "二":
                    text = text.replaceFirst(c, "2");
                    break;
                case "三":
                    text = text.replaceFirst(c, "3");
                    break;
                case "四":
                    text = text.replaceFirst(c, "4");
                    break;
                case "五":
                    text = text.replaceFirst(c, "5");
                    break;
                case "六":
                    text = text.replaceFirst(c, "6");
                    break;
                case "七":
                    text = text.replaceFirst(c, "7");
                    break;
                case "八":
                    text = text.replaceFirst(c, "8");
                    break;
                case "九":
                    text = text.replaceFirst(c, "9");
                    break;
            }
        }
        return text;
    }

    public String bulidTextZHToALB(String text) {

        Pattern p = Pattern.compile(numRegex);
        Matcher m = p.matcher(text);

        while (m.find()) {
            String numZH = m.group();
            if (numZH.length() != 1 || numMap.containsKey(numZH) || zhTen.equals(numZH)) {
                String numALB = NumZHToALB(numZH);
                text = text.replaceFirst(numZH, numALB);
            }
        }

        return text;
    }

    private String NumZHToALB(String numZH) {
        int numALB = 0;
        int formIndex = 0;
        for (String unitNum : unitNumMap.keySet()) {
            int index = numZH.indexOf(unitNum);
            if (index != -1) {
                numALB += NumZHToALB(numZH.substring(formIndex, index), unitNumMap.get(unitNum));
                formIndex = index + 1;
            }
        }

        numALB += NumZHToALB(numZH.substring(formIndex), 1);
        return String.valueOf(numALB);
    }

    private int NumZHToALB(String numZH, int unitNum) {
        int length = numZH.length();
        int numALB = 0;
        if (length != 0) {
            int fromIndex = 0;
            for (String unit : unitMap.keySet()) {
                int index = numZH.indexOf(unit, fromIndex);
                if (index != -1) {
                    fromIndex = index + unit.length();
                    String prevChar = zhOne;
                    if (index != 0 && numMap.containsKey(prevChar)) {
                        prevChar = String.valueOf(numZH.charAt(index - 1));
                    }
                    numALB += numMap.get(prevChar) * unitMap.get(unit);
                }
            }

            String lastChar = String.valueOf(numZH.charAt(length - 1));
            if (numMap.containsKey(lastChar)) {
                String pChar = zhTen;
                if (length != 1) {
                    pChar = String.valueOf(numZH.charAt(length - 2));
                    if (zhZero.equals(pChar)) {
                        pChar = zhTen;
                    }
                }
                numALB += numMap.get(lastChar) * unitMap.get(pChar) / 10;
            }
        }

        return numALB * unitNum;
    }

    private String encodeUnicode(String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i : utfBytes) {
            String hexB = Integer.toHexString(i);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    public static boolean isNumberic(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
