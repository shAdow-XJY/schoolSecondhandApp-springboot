package com.example.shadow.util;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CheckUtil {


    public static boolean IDCardNoCheck(String id) {
        if (id == null)
            return false;
        id = id.toUpperCase();
        if (id.length() != 15 && id.length() != 18) {
            return false;
        }
        int y = 0, m = 0, d = 0;
        if (id.length() == 15) {
            y = Integer.parseInt("19" + id.substring(6, 8), 10);
            m = Integer.parseInt(id.substring(8, 10), 10);
            d = Integer.parseInt(id.substring(10, 12), 10);
        } else if (id.length() == 18) {
            if (id.indexOf("X") >= 0 && id.indexOf("X") != 17) {
                return false;
            }
            char verifyBit = 0;
            int sum = (id.charAt(0) - '0') * 7 + (id.charAt(1) - '0') * 9 + (id.charAt(2) - '0') * 10
                    + (id.charAt(3) - '0') * 5 + (id.charAt(4) - '0') * 8 + (id.charAt(5) - '0') * 4
                    + (id.charAt(6) - '0') * 2 + (id.charAt(7) - '0') * 1 + (id.charAt(8) - '0') * 6
                    + (id.charAt(9) - '0') * 3 + (id.charAt(10) - '0') * 7 + (id.charAt(11) - '0') * 9
                    + (id.charAt(12) - '0') * 10 + (id.charAt(13) - '0') * 5 + (id.charAt(14) - '0') * 8
                    + (id.charAt(15) - '0') * 4 + (id.charAt(16) - '0') * 2;
            sum = sum % 11;
            switch (sum) {
                case 0:
                    verifyBit = '1';
                    break;
                case 1:
                    verifyBit = '0';
                    break;
                case 2:
                    verifyBit = 'X';
                    break;
                case 3:
                    verifyBit = '9';
                    break;
                case 4:
                    verifyBit = '8';
                    break;
                case 5:
                    verifyBit = '7';
                    break;
                case 6:
                    verifyBit = '6';
                    break;
                case 7:
                    verifyBit = '5';
                    break;
                case 8:
                    verifyBit = '4';
                    break;
                case 9:
                    verifyBit = '3';
                    break;
                case 10:
                    verifyBit = '2';
                    break;

            }

            if (id.charAt(17) != verifyBit) {
                return false;
            }
            y = Integer.parseInt(id.substring(6, 10), 10);
            m = Integer.parseInt(id.substring(10, 12), 10);
            d = Integer.parseInt(id.substring(12, 14), 10);
        }

        int currentY = Calendar.getInstance().get(Calendar.YEAR);

        /*
         * if(isGecko){ currentY += 1900; }
         */
        if (y > currentY || y < 1870) {
            return false;
        }
        if (m < 1 || m > 12) {
            return false;
        }
        if (d < 1 || d > 31) {
            return false;
        }
        return true;
    }


    /**
     * ^ 匹配输入字符串开始的位置
     * \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
     * $ 匹配输入字符串结尾的位置
     */
    private static final Pattern HK_PATTERN = Pattern.compile("^(5|6|8|9)\\d{7}$");
    private static final Pattern CHINA_PATTERN = Pattern.compile("^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$");
    private static final Pattern NUM_PATTERN = Pattern.compile("[0-9]+");

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 145,147,149
     * 15+除4的任意数(不要写^4，这样的话字母也会被认为是正确的)
     * 166
     * 17+3,5,6,7,8
     * 18+任意数
     * 198,199
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        Matcher m = CHINA_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {

        Matcher m = HK_PATTERN.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否是正整数的方法
     */
    public static boolean isNumeric(String string) {
        return NUM_PATTERN.matcher(string).matches();
    }
}