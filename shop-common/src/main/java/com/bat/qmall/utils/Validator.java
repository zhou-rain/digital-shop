package com.bat.qmall.utils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * @创建人： huangjie
 * @时间：      2013-3-14 下午4:37:49
 * @作用：      验证类
 */
public class Validator {
	public static String NOPARAMETER = "系统未发现传入的参数";
	/**
     * 验证对象是否为空(Object/Integer/String/List/[]/Map ...)
     * 
     * @param value 待验证对象
     * @return 如果对象为空（""/" "/"null"/null/number=0/[].length=0/collection.size()=0/map.size()=0均认为空）,
     *         返回true,否则返回false
     */
    public static boolean isEmpty(Object value) {

		if (   value == null
				|| "".equalsIgnoreCase(value.toString().replaceAll(" ", ""))
				|| "null".equalsIgnoreCase(value.toString().replaceAll(" ", ""))) {// Object/String
			return true;
		}

		if (value instanceof Number) {// BigDecimal/BigInteger/Byte/Double/Float/Long/Integer/Short
            Number number = (Number) value;
            if ("0".equalsIgnoreCase(number.toString().replaceAll(" ", "")) 
            	|| number.toString().replaceAll(" ", "").matches("[0]*")
                || number.toString().replaceAll(" ", "").matches("[0]+[.]?[0]+")) {
                return true;
            }
        }

        if (value instanceof Object[]) {// 数组
            Object[] objs = (Object[]) value;
            if (objs.length == 0) {
                return true;
            }
        }

        if (value instanceof Collection) {// 集合
            Collection<?> coll = (Collection<?>) value;
            if (coll.size() == 0) {
                return true;
            }
        }

        if (value instanceof Map) {// Map
            Map<?,?> map = (Map<?,?>) value;
            if (map.isEmpty() || map.size() == 0) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }
	
	/**
     * 验证两个字符串是否值相等
     *
     * @param s1 相比较的字符串1
     * @param s2 相比较的字符串2
     * @return 如果两个字符串值相等，返回true；否则返回false
     */
    public static boolean equals(String s1, String s2) {
        if ((s1 == null) && (s2 == null)) {
            return true;
        } else if ((s1 == null) || (s2 == null)) {
            return false;
        } else {
            return s1.equals(s2);
        }
    }


    /**
     * 判断给定字符是否英文字母
     *
     * @param c 要验证的字符
     * @return 如果是字母，则返回true；否则返回false
     */
    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    /**
     * 判断给定字符串是否都由字母或者汉字构成
     *
     * @param s 要验证的字符串
     * @return 若都由英文字母或者汉字构成，则返回true；否则返回false
     */
    public static boolean isLetter(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isLetter(c[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断给定字符是否数字
     *
     * @param c 要验证的字符
     * @return 如果是数字，则返回true；否则返回false
     */
    public static boolean isDigit(char c) {
        int x = (int) c;

        if ((x >= 48) && (x <= 57)) {
            return true;
        }

        return false;
    }

    /**
     * 判断给定字符串是否都由数字构成
     *
     * @param s 要验证的字符串
     * @return 若都由数字构成，则返回true；否则返回false
     */
   /* public static boolean isDigit(String s) {
        if (isNull(s)) {
            return false;
        }

        char[] c = s.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }

        return true;
    }*/

    /*
    public static boolean isHex(String s) {
		if (isNull(s)) {
			return false;
		}

		return true;
	}
    */

    /**
     * 判断给定字符串是否是完整的html代码段
     * （这里完整的html代码段是指带有<html></html>的代码，大小写五关）
     *
     * @param s 要验证的字符串
     * @return 若为html代码段，则返回true，否则返回false
     */
    public static boolean isHTML(String s) {
        if (isNull(s)) {
            return false;
        }

        if (((s.indexOf("<html>") != -1) || (s.indexOf("<HTML>") != -1)) &&
                ((s.indexOf("</html>") != -1) || (s.indexOf("</HTML>") != -1))) {

            return true;
        }

        return false;
    }

    /*
    public static boolean isLUHN(String number) {
		if (number == null) {
			return false;
		}

		number = StringUtil.reverse(number);

		int total = 0;

		for (int i = 0; i < number.length(); i++) {
			int x = 0;

			if (((i + 1) % 2) == 0) {
				x = Integer.parseInt(number.substring(i, i + 1)) * 2;

				if (x >= 10) {
					String s = Integer.toString(x);

					x = Integer.parseInt(s.substring(0, 1)) +
						Integer.parseInt(s.substring(1, 2));
				}
			}
			else {
				x = Integer.parseInt(number.substring(i, i + 1));
			}

			total = total + x;
		}

		if ((total % 10) == 0) {
			return true;
		}
		else {
			return false;
		}
	}
    */

    /**
     * 判断给定字符串是否是一个合法的email地址
     *
     * @param email 要进行验证的字符串
     * @return 若是合法的email地址，则返回true；否则返回false
     */
    public static boolean isEmailAddress(String email) {
        if (isNull(email)) {
            return false;
        }

        int eaLength = email.length();

        if (eaLength < 6) {

            // j@j.c

            return false;
        }

        email = email.toLowerCase();

        int at = email.indexOf("@");

        // Unix based email addresses cannot be longer than 24 characters.
        // However, many Windows based email addresses can be longer than 24,
        // so we will set the maximum at 48.

        //int maxEmailLength = 24;
        int maxEmailLength = 48;

        if ((at > maxEmailLength) || (at == -1) || (at == 0) ||
                ((at <= eaLength) && (at > eaLength - 5))) {

            // 123456789012345678901234@joe.com
            // joe.com
            // @joe.com
            // joe@joe
            // joe@jo
            // joe@j

            return false;
        }

        int dot = email.lastIndexOf('.');

        if ((dot == -1) || (dot < at) || (dot > eaLength - 3)) {

            // joe@joecom
            // joe.@joecom
            // joe@joe.c

            return false;
        }

        if (email.indexOf("..") != -1) {

            // joe@joe..com

            return false;
        }

        char[] name = email.substring(0, at).toCharArray();

        for (int i = 0; i < name.length; i++) {
            if ((!isLetter(name[i])) &&
                    (!isDigit(name[i])) &&
                    (name[i] != '.') &&
                    (name[i] != '-') &&
                    (name[i] != '_')) {

                return false;
            }
        }

        if ((name[0] == '.') || (name[name.length - 1] == '.') ||
                (name[0] == '-') || (name[name.length - 1] == '-') ||
                (name[0] == '_')) { // || (name[name.length - 1] == '_')) {

            // .joe.@joe.com
            // -joe-@joe.com
            // _joe_@joe.com

            return false;
        }

        char[] host = email.substring(at + 1, email.length()).toCharArray();

        for (int i = 0; i < host.length; i++) {
            if ((!isLetter(host[i])) &&
                    (!isDigit(host[i])) &&
                    (host[i] != '.') &&
                    (host[i] != '-')) {

                return false;
            }
        }

        if ((host[0] == '.') || (host[host.length - 1] == '.') ||
                (host[0] == '-') || (host[host.length - 1] == '-')) {

            // joe@.joe.com.
            // joe@-joe.com-

            return false;
        }

        // postmaster@joe.com

        if (email.startsWith("postmaster@")) {
            return false;
        }

        // root@.com

        if (email.startsWith("root@")) {
            return false;
        }

        return true;
    }

    /*
     public static boolean isName(String name) {
         if (isNull(name)) {
             return false;
         }

         char[] c = name.trim().toCharArray();

         for (int i = 0; i < c.length; i++) {
             if (((!isLetter(c[i])) &&
                 (!Character.isWhitespace(c[i]))) ||
                     (c[i] == ',')) {

                 return false;
             }
         }

         return true;
     }
     */

    /**
     * 判断给定字符串是否数字
     *
     * @param number 要验证的字符串
     * @return 若为数字，则返回true；否则返回false
     */
    public static boolean isNumber(String number) {
        if (isNull(number)) {
            return false;
        }

        char[] c = number.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if (!isDigit(c[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断给定数组是否空数组（这里空数组指null或者长度为0的数组）
     *
     * @param o 要验证的数组
     * @return 若为空数组，则返回null；否则返回false
     */
    public static boolean isNull(Object[] o) {
        if (o == null || o.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断给定字符串是否空串 （这里空串是指null或者“”或者由若干个空格组成的串）
     *
     * @param s 要验证的字符串
     * @return 若为空串，则返回true；否则返回false
     */
    public static boolean isNull(String s) {
        if (s == null) {
            return true;
        }

        s = s.trim();

        if (("null".equals(s)) || ("".equals(s))) {
            return true;
        }

        return false;
    }

    /**
     * 判断给定字符串是否非空串 （这里空串是指null或者“”或者由若干个空格组成的串）
     *
     * @param s 要验证的字符串
     * @return 若为非空串，则返回true；否则返回false
     */
    public static boolean isNotNull(String s) {
        return !isNull(s);
    }

    /**
     * 判断给定字符串是否合法密码
     * （这里合法密码是指长度大等于4且由数字或者中英文字组成的字符串）
     *
     * @param password 要验证的字符串
     * @return 若为合法密码，则返回true；否则返回false
     */
    public static boolean isPassword(String password) {
        if (isNull(password)) {
            return false;
        }

        if (password.length() < 4) {
            return false;
        }

        char[] c = password.toCharArray();

        for (int i = 0; i < c.length; i++) {
            if ((!isLetter(c[i])) &&
                    (!isDigit(c[i]))) {

                return false;
            }
        }

        return true;
    }

    private static final String regex = "[1-9]\\d*\\.?\\d*|0\\.\\d*|-[1-9]\\d*\\.?\\d*|-0\\.\\d*|0";

    /**
     * 判断字符串是否数字（允许带有负号以及小数点）
     * @param string 给定字符串
     * @return 若为合法数字，则返回true；否则返回false
     */
    public static boolean isDigital(String string) {
        return string.matches(regex);
    }

    /*
     public static boolean isPhoneNumber(String phoneNumber) {
         return isNumber(StringUtil.extractDigits(phoneNumber));
     }
     */


    /**
     * 验证日期字符串是否为一个有效的日期
     * 
     * @param pattern
     *            日期模式 如 yyyy-MM-dd或yyyyMMdd
     * @param value
     *            日期指定模式的字符串 如 2012-02-29
     * @return 是否一个有效的日期类型；true:表示是，false:表示不是（说明：不支持日期进位 如：2012-02-30
     *         不能表示2012-03-01）
     * @throws Exception 参数不匹配异常
     */
    public static boolean validateDate(String pattern, String value) throws Exception {
        if (Validator.isEmpty(pattern)) {
            throw new RuntimeException("日期模式不能为空！");
        }

        if (Validator.isEmpty(value)) {
            throw new RuntimeException("日期字符串不能为空！");
        }
        boolean flag = false;
        SimpleDateFormat format = null;
        try {
            format = new SimpleDateFormat(pattern);
            Date date = format.parse(value);
            if (format.format(date).equalsIgnoreCase(value)) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            //logger.error("日期格式不正确！", e);
            return flag;
           // throw new RuntimeException("日期格式不正确！");
        }
        return flag;
    }

    /**
     * 判断对象是否为Integer类型或是否可以转换为Integer类型
     * 
     * @param value
     * @return 可以为true，否则为false
     */
    public static boolean validateInteger(Object value)  {
        if (value == null) {
            return false;
        }
        if (value instanceof Integer) {
            return true;
        } else {
            value = value.toString();
        }
        String str = (String) value;
        if (str.matches("\\d+")) {
            return true;
        }
        return false;
    }
}
