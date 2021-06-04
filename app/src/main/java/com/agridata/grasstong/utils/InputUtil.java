package com.agridata.grasstong.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputUtil {


    /**
     * 判断是否是合法的账号（手机号或邮箱）
     *
     * @param account
     *
     * @return
     */
    public static boolean isAccountLegal(String account) {
        return !StrUtil.isEmpty(account) && account.matches(AppConstants.Validator.REGEX_ACCOUNT);
    }

    /**
     * 判断是否是合法的邮箱
     *
     * @param email
     *
     * @return
     */
    public static boolean isEmailLegal(String email) {
        return !StrUtil.isEmpty(email) && email.matches(AppConstants.Validator.REGEX_EMAIL);
    }

    /**
     * 判断是否是合法的手机号
     *
     * @param mobile
     *
     * @return
     */
    public static boolean isMobileLegal(String mobile) {
        return !StrUtil.isEmpty(mobile) && mobile.matches(AppConstants.Validator.REGEX_MOBILE);
    }

    /**
     * 判断是否是合法的昵称
     *
     * @param nickName
     *
     * @return
     */
    public static boolean isNickNameLegal(String nickName) {
        return !StrUtil.isEmpty(nickName) && nickName.matches(AppConstants.Validator.REGEX_NICKNAME);
    }

    /**
     * 判断是否是合法的汉字、字母、数字
     *
     * @param CharNumber
     *
     * @return
     */
    public static boolean isCharNumberLegal(String CharNumber) {
        return !StrUtil.isEmpty(CharNumber) && CharNumber.matches(AppConstants.Validator.REGEX_CHAR_NUMBER);
    }

    /**
     * 判断是否是合法的校验码
     *
     * @param checkCode
     *
     * @return
     */
    public static boolean isCheckCodeLegal(String checkCode) {
        return !StrUtil.isEmpty(checkCode) && checkCode.matches(AppConstants.Validator.REGEX_CHECKCODE);
    }

    /**
     * 判断是否是合法的密码：规则6-20数字和字母组成
     *
     * @param password
     *
     * @return
     */
    public static boolean isPasswordLegal(String password) {
        return !StrUtil.isEmpty(password) && password.matches(AppConstants.Validator.REGEX_PASSWORD);
    }

    /**
     * 隐藏邮箱@前面的4位字符
     *
     * @param email
     *
     * @return
     */
    public static String hideEmailText(String email) {
        return email.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4");
    }

    /**
     * 隐藏手机号中间的4位数字
     *
     * @param mobile
     *
     * @return
     */
    public static String hideMobileText(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 判断html是否包含转义字符
     *
     * @param htmlContent
     *
     * @return
     */
    public static boolean isHtmlTransformLegal(String htmlContent) {
        return !StrUtil.isEmpty(htmlContent) && htmlContent.matches(AppConstants.Validator.REGEX_HTML_SPECIAL);
    }

    /**
     * 判断群卡片电话格式
     * @param phone
     * @return
     */
    public static boolean isCardPhone(String phone) {
        return !StrUtil.isEmpty(phone) && phone.matches(AppConstants.Validator.REGEX_CARD_PHONE);
    }

    /**
     * 判断包含中文
     *
     * @param str
     *
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
     * 是否包含字母
     *
     * @param str
     *
     * @return
     */
    public static boolean isLetter(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     *
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 判断是否是纯数字
     *
     * @param str
     *
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    public static String convert(String str)
    {
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++)
        {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>>8); //取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); //取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);

        }
        return (new String(sb));
    }

    /**
     * 判断是否包含中文标点符号
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

}
