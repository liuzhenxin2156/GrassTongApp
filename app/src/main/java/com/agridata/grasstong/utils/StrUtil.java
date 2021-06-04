package com.agridata.grasstong.utils;

import android.content.Context;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import android.util.TypedValue;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2014/8/29
 *
 * @NewY. W
 */
public class StrUtil {


    public static Spanned highStr(String str, String needHighStr){
        if (str.contains(needHighStr)) {
            String[] split = str.split(needHighStr);
            String highCircleName = "";
            if (split.length == 0) {
                highCircleName = "<font color=\"#01A95C\">" + needHighStr + "</font>";
            } else
                for (int i = 0; i < split.length; i++) {
                    highCircleName += split[i];
                    if (i < split.length - 1)
                        highCircleName += "<font color=\"#01A95C\">" + needHighStr + "</font>";
                }
            return Html.fromHtml(highCircleName);
        }
        return new SpannedString(str);
    }

    // 邮箱的匹配
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    // 2种日期的匹配
    private final static SimpleDateFormat dateFormater = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static final String TAG = "StrUtil";

    /**
     * 判断字符串是否是null, 或者是"", 或者是包含空字符的字符串
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equalsIgnoreCase(input)) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty( Object obj) {
        if (obj == null) {
            return true;
        }
        //见以下各种判断
        return true;
    }

    /**
     * 判断字符串是否是匹配 email格式
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        return emailer.matcher(email).matches();
    }

    /**
     * 手机号验证   * @author ：shijing   * 2016年12月5日下午4:34:46   * @param  str   * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


    /**
     * 将一个手机号的中间4位隐藏
     *
     * @param phoneNum
     * @return 如果不是手机号, 直接返回原有的字符串
     */
    public static String hiddenMobile(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            return "";
        }
        if (isMobileNumber(phoneNum)) {
            char[] chars = phoneNum.toCharArray();
            for (int i = 3; i <= 6; i++) {
                chars[i] = '*';
            }
            return new String(chars);
        }
        return phoneNum;
    }


    public static String hiddenStr(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }

        char[] chars = str.toCharArray();

        for (int i = 2; i <= str.length() - 3; i++) {
            chars[i] = '*';
        }
        return new String(chars);

    }

    /**
     * 130、131、132、133、134、135、136、137、138、139、
     * 145、147、
     * 150、151、152、153、155、156、157、158、159、
     * 170,176、177、178、
     * 180、181、182、183、184、185、186、187、188、189 、
     * 增加号码段三个,  WZC-11916
     * 166
     * 199,198
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNumber(String mobiles) {
        if (StrUtil.isEmpty(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^1[3456789]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 如果content的字符长度超过length, 就保留length长度的内容,后面的内容删掉,用 "..."替代
     *
     * @param content
     * @param length
     * @return
     */
    public static String endStringWithEllip(String content, int length) {
        if (content.length() > length) {
            StringBuilder sb = new StringBuilder(content.substring(0, length));
            sb.append("...");
            return sb.toString();
        }
        return content;
    }


    public static String appendStrBylength(String source, String appendStr, int length) {

        return source.length() <= length ? source : new StringBuffer(source.substring(0, length - 1)).append(appendStr).toString();

    }

    /**
     * @param value 一个可以按照split分割的字符串
     * @param split
     * @return List<String> 分割之后的字符串集合
     */
    public static List<String> split2List(String value, String split) {
        if (value != null && value.length() > 0) {
            List<String> retVal = new ArrayList<String>();
            String[] strArr = value.split(split);
            if (strArr.length > 1) {
                for (String str : strArr) {
                    retVal.add(str);
                }
            } else {
                retVal.add(value);
            }
            return retVal;
        }
        return null;
    }

    /**
     * @param strs  需要被整合成一个字符串的字符串集合
     * @param split 各个字符串中间用split作为分隔符
     * @return 得到一个按照spit间隔的字符串
     */

    public static String integrate2Str(List<String> strs, String split) {
        if (strs != null && strs.size() > 0) {
            StringBuilder retVal = new StringBuilder();
            for (String str : strs) {
                retVal.append(split + str);
            }
            return retVal.substring(1).toString();
        }
        return null;
    }

    /**
     * @param stringMap
     * @param split
     * @return 将一个map中的key和value用= 拼接, 不同的entity用 split参数拼接, 成一个字符串
     */
    public static String integrate2Str(Map<String, String> stringMap, String split) {
        if (stringMap != null && stringMap.size() > 0) {
            StringBuilder retVal = new StringBuilder();
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                retVal.append(split + entry.getKey() + "=" + entry.getValue());
            }
            return retVal.substring(1).toString();
        }
        return null;
    }

    /**
     * 全角转半角
     *
     * @param input
     * @return
     */
    // 半角空格32,全角空格12288
    // 其他字符半角33~126,其他字符全角65281~65374,相差65248
    public static String SBCToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    // 半角空格32,全角空格12288
    // 其他字符半角33~126,其他字符全角65281~65374,相差65248
    public static String DBCToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] > 33 && c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 校验身份证
     *
     * @param str
     * @return
     */
    public static boolean identity(String str) {
        if (StrUtil.isEmpty(str)) {
            return false;
        }
        String regx = "([0-9]{17}([0-9]|X))|([0-9]{15})";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    /**
     * 校验密码, 长度大于八位,小于16位, 数字 和字母的组合
     *
     * @param str
     * @return
     */
    public static boolean isPwd(String str) {
        if (StrUtil.isEmpty(str)) {
            return false;
        }

        String regx = "^[0-9a-zA-Z@~:\\-\\*^%&',;=?$\\.\\x22]{6,16}$";

        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    /**
     * 校验密码, 长度大于6位,小于20位, 数字 和字母的组合
     *
     * @param str
     * @return
     */
    public static boolean isPasswordLegal(String str) {
        if (StrUtil.isEmpty(str)) {
            return false;
        }

        String regx = "^(?!^[0-9]+$)(?!^[a-zA-Z]+$)(?!^[^a-zA-Z0-9]+$)^.{6,20}$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    /**
     * 校验密码, 长度大于八位,小于16位, 数字 和字母的组合
     *
     * @param str
     * @return
     */
    public static boolean isNewPwd(String str) {
        if (StrUtil.isEmpty(str)) {
            return false;
        }

//        String regx = "^[0-9a-zA-Z@~:\\-\\*^%&',;=?$\\.\\x22]{8,16}$";
//
//        Pattern pattern = Pattern.compile(regx);
//        if(!pattern.matcher(str).matches()){
//            return false;
//        }

//        boolean hasLetter = false;
//        boolean hasNumber = false;
//        for (int i = 0; i < str.length(); i++) {
//            char c = str.charAt(i);
//            if (c >= '0' && c <= '9') {
//                hasNumber = true;
//                if (hasLetter) {
//                    break;
//                }
//            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
//                hasLetter = true;
//                if (hasNumber) {
//                    break;
//                }
//            }
//        }
//        if (hasLetter && hasNumber) {
//            return true;
//        } else {
//            return false;
//        }

        String regx = "^(?=.*[0-9])(?=.*[a-zA-Z])([@~:\\-\\*^%&',;=?$\\.\\x22]*)[0-9a-zA-Z@~:\\-\\*^%&',;=?$\\.\\x22]{8,16}$";
        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否包含中文标点符号
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

            return true;

        }

        return false;

    }


    /**
     * 校验密码, 数字 和字母的组合
     *
     * @param str
     * @return
     */
    public static boolean isLetters(String str) {

        String regx = "^[0-9a-zA-Z]+$";

        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    /**
     * 校验密码, 长度大于6
     *
     * @param str
     * @return 大于等于6位数, 则返回true, 否则false
     */
    public static boolean mixLength(String str) {
        if (str.length() < 6) {
            return false;
        }
        return true;
    }

    /**
     * 校验密码, 长度小于20
     *
     * @param str
     * @return 大于16位则返回false, 否则返回true
     */
    public static boolean maxLength(String str) {
        if (str.length() > 16) {
            return false;
        }
        return true;
    }

    /**
     * 验证验证输入汉字
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean IsChinese(String str) {
        if (StrUtil.isEmpty(str)) {
            return false;
        }
        String regex = "^[\u4e00-\u9fa5],{0,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    public static void clearStringBuilder(StringBuilder sb) {
        if (sb != null) {
            sb.delete(0, sb.length());
        }
    }

    /**
     * 判断包含中文
     *
     * @param str
     * @return
     */
    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 验证输入是否是特殊字符
     *
     * @param str 待验证的字符串
     * @return 如果是符合格式的字符串, 返回 <b>true </b>,否则为 <b>false </b>
     */
    public static boolean isSpecialCharacter(String str) {
        if (isEmpty(str)) {
            return false;
        }
        String regex = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    /**
     * 验证车牌号
     * @param str
     * @return
     */
    public static boolean isCarNumberPlate(String str) {
        if (isEmpty(str)) {
            return false;
        }
        String regex ="^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    /**
     * 提取 一个字符串 中的 数字,
     *
     * @param input
     * @return
     */
    public static Double getNumber(String input) {
        if (StrUtil.isEmpty(input)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if ((c >= '0' && c <= '9') || c == '.' || c == '-') {
                sb.append(c);
            }
        }

        String numberStr = sb.toString();

        if (StrUtil.isEmpty(numberStr)) {
            return null;
        }

        try {
            return Double.valueOf(numberStr);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 提取 一个字符串 中的 数字,只提取整数
     *
     * @param input
     * @return
     */
    public static Integer getInteger(String input) {
        Double tmp = getNumber(input);
        if (tmp != null) {
            return tmp.intValue();
        }
        return null;
    }


    public static boolean isValidCode(String str) {
        if (StrUtil.isEmpty(str)) {
            return false;
        }

        String regx = "^[0-9]{6}$";

        Pattern pattern = Pattern.compile(regx);
        return pattern.matcher(str).matches();
    }

    /**
     * 获得2位小数的字符串
     *
     * @param txt
     * @return
     */
    public static String getTwodecimal(String txt) {
        Double number = getNumber(txt);
        if (number == null) {
            return null;
        }
        return getTwodecimal(number);
    }

    /**
     * 获得2位小数的字符串
     *
     * @param number
     * @return
     */
    public static String getTwodecimal(Double number) {

        return String.format("%.2f", number);
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 判断给定的字符串是否是表示16进制的字符串
     *
     * @param content
     * @return
     */
    public static boolean isHexNumber(String content) {
        if (StrUtil.isEmpty(content)) {
            return false;
        }
        int length = content.length();
        if (length > 50) {
            content = content.substring(0, 30);
        }
        length = content.length();
        String regex = "^[0-9a-fA-F]{" + length + "}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(content).matches();
    }


    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * @param str
     * @return 替换字符串中的百分号
     */
    public static String replaceChar(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("%", "％");
    }

    /**
     * @return emoji表情屏蔽，length是edittext的长度
     */
    public static InputFilter[] emojiFilters(int length) {
        InputFilter emojiFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    int type = Character.getType(source.charAt(i));
                    if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                        return "";
                    }
                }
                return null;
            }
        };
        InputFilter[] emojiFilters = {emojiFilter, new InputFilter.LengthFilter(length)};
        return emojiFilters;
    }

    public static String getTelephone(String input) {
        if (StrUtil.isEmpty(input)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= '0' && c <= '9') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 字符串形式的金额
     * 如果为null,或者为0,则返回true
     * 可以通过转换成金额,切不为0, 返回false
     *
     * @param fee 金额的字符串
     * @return 为空, 或者小于0, 返回false, 否则true
     */
    public static boolean isNullOrZero(String fee) {
        Double number = StrUtil.getNumber(fee);
        return (number == null || number <= 0);
    }

    public static boolean isUrl(String url) {
        if (isEmpty(url)) {
            return false;
        }
        String regex = "^http://|https://[0-9a-z]{0,}\\.[0-9a-z]{0,}\\.[0-9a-z]{0,}$";
        String regex2 = "^http://[0-9]{1,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\:[0-9]{2,4}/[0-9a-z]{0,}$";
        Pattern pattern = Pattern.compile(regex);
        boolean matches1 = pattern.matcher(url).matches();
        if (!matches1) {
            pattern = Pattern.compile(regex2);
            matches1 = pattern.matcher(url).matches();
        }
        return matches1;
    }

    /**
     * 判断字符串是不是日期格式
     *
     * @param strDate
     * @return
     */
    public static boolean isDate(String strDate) {
        if (isEmpty(strDate)) {
            return false;
        }

        String regex = "^\\d{4}-\\d{1,2}-\\d{1,2}$";
        Pattern compile = Pattern.compile(regex);
        return compile.matcher(strDate).matches();
    }


    /**
     * unicode 转字符串
     */
    public static String unicodeToUtf8(String unicode) {
        if (isEmpty(unicode)) {
            return "";
        }

        StringBuffer string = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }
    /**
     * @param context
     * @param textView
     * @param mPx
     */
    public static  void SJ(Context context, TextView textView, String str, int mPx) {
        //1.先创建SpannableString对象
        SpannableString spannableString = new SpannableString(str);
        //2.设置文本缩进的样式，参数arg0，首行缩进的像素，arg1，剩余行缩进的像素,这里我将像素px转换成了手机独立像素dp
        LeadingMarginSpan.Standard what = new LeadingMarginSpan.Standard(dp2px(context, mPx), 0);
        //3.进行样式的设置了,其中参数what是具体样式的实现对象,start则是该样式开始的位置，end对应的是样式结束的位置，参数flags，定义在Spannable中的常量
        spannableString.setSpan(what, 0, spannableString.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(spannableString);
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
    //将字节数转化为MB
    public static  String byteToMB(long size){
        long kb = 1024;
        long mb = kb*1024;
        long gb = mb*1024;
        if (size >= gb){
            return String.format("%.1f GB",(float)size/gb);
        }else if (size >= mb){
            float f = (float) size/mb;
            return String.format(f > 100 ?"%.0f MB":"%.1f MB",f);
        }else if (size > kb){
            float f = (float) size / kb;
            return String.format(f>100?"%.0f KB":"%.1f KB",f);
        }else {
            return String.format("%d B",size);
        }
    }
}
