package com.hzu.recruit.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class HashUtil {

    //为密码加盐md5，防止出现明文
    private static final HashFunction FUNCTION = Hashing.md5();

    //防止密码过于简单，把密码加上字符串一起加盐
    private static final String SALT = "huz.com";

    public static String encryPassword(String password) {
        HashCode hashCode = FUNCTION.hashString(password+SALT, Charset.forName("UTF-8") );
        return hashCode.toString();
    }
}
