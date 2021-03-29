package com.soft.usercenter.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @Description
 * @Author 涛涛
 * @Date 2020/5/24 9:53
 * @Version 1.0
 **/
public class Md5Util {
    /**
     * @param pwd     需要加密的字符串
     * @param isUpper 字母大小写(false为默认小写，true为大写)
     * @param bit     加密的位数（16,32,64）
     * @return String
     */
    public static String getMd5(String pwd, boolean isUpper, Integer bit) {
        String md5 = "";
        try {
            // 创建加密对象
            MessageDigest md = MessageDigest.getInstance("md5");
            if (bit == 64) {
                Base64.Encoder encoder = Base64.getEncoder();
                md5 = encoder.encodeToString(md.digest(pwd.getBytes(StandardCharsets.UTF_8)));
            } else {
                // 计算MD5函数
                md.update(pwd.getBytes());
                byte b[] = md.digest();
                int i;
                StringBuilder sb = new StringBuilder();
                for (byte value : b) {
                    i = value;
                    if (i < 0) {
                        i += 256;
                    }
                    if (i < 16) {
                        sb.append("0");
                    }
                    sb.append(Integer.toHexString(i));
                }
                md5 = sb.toString();
                if (bit == 16) {
                    //截取32位md5为16位
                    md5 = md5.substring(8, 24).toString();
                    if (isUpper) {
                        md5 = md5.toUpperCase();
                    }
                    return md5;
                }
            }
            //转换成大写
            if (isUpper) {
                md5 = md5.toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("md5加密抛出异常！");
        }

        return md5;
    }

    public static void main(String[] args) {
        String a = "123456";
        //大写  32位
        String md5a = getMd5(a, true, 32);
        System.out.println(md5a);
        System.out.println(md5a.length());
    }
}