package com.zzu.util;

import server.smsService;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/3/2.
 */
public class StringUtil {
	public static final String STR = "0123456789ABCDEFGHIJKLMNabcdefghijklmn";

	//判断字符串是否为空
	public static boolean isEmpty(String s) {
		return s == null || s.trim().equals("");
	}

	//MD5加密
	public static String toMd5(String str) {
		//确定计算方法
		MessageDigest md5 = null;
		String newStr = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BASE64Encoder base64en = new BASE64Encoder();
		//加密后的字符串
		try {
			newStr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newStr;
	}

	//产生指定长度的字符串
	public static String randomString(int length) {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();

		for(int i=0;i<length;i++) {
			int index = r.nextInt(STR.length() - 1);
			sb.append(STR.charAt(index));
		}
		return sb.toString();
	}

	//测试发送短信
	public static void sendMsg() {
		// 发送短信
		String userid = "a672399171";   //你的用户名
		String pass = "a1703628649";    //你的密码
		String mobiles = "15617536860"; //对方接收的手机号
		String msg = "JAVA测试短信通过2008-11-13,验证码123456789";  //内容
		String time = "";

		smsService service = new smsService();
		String result = service.sendSms(userid, pass, mobiles, msg, time);
		System.out.println("结果：" + result);
	}

	//判断是否为数字
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	//判断是否是空余时间字符串
	public static boolean isSpareTimeString(String s) {
		Pattern pattern = Pattern.compile("[0,1]{7}");
		Matcher matcher = pattern.matcher(s);
		return matcher.find();
	}

	//提取字符串中的数字
	public static List<String> getNumberList(String str) {
		List<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile("(\\d+)");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			list.add(matcher.group(1));
		}
		return list;
	}

	public static void main(String[] args) {
		//System.out.println(toMd5("admin"));
		System.out.println(getNumberList("/Date(1458536322941)/"));
		//showAllCitys();
		//sendMsg();
	}
}