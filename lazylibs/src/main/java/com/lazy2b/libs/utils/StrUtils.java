/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.utils
 * 文件名:StrUtils.java
 * 创  建:2015年10月20日下午5:35:19
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.lazy2b.libs.interfaces.ILazyBase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.DateFormat;

/**
 * 类名: StrUtils <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
@SuppressLint("SimpleDateFormat")
public class StrUtils implements ILazyBase {

	private static String key2 = "Aedse_!#@..";
	private static String key1 = "13245";
	/** 年月日时分秒 */
	public final static String FORMAT_YMDHMS = "yyyy-MM-dd kk:mm:ss";

	/** 获得当前时间 */
	public static CharSequence currentTime(CharSequence inFormat) {
		return DateFormat.format(inFormat, System.currentTimeMillis());
	}

	/** 获得当前时间 */
	public static CharSequence formatTime(CharSequence inFormat, long time) {
		return DateFormat.format(inFormat, time);
	}

	public static String formatSize(float time) {
		return new DecimalFormat("#.00").format(time);
	}

	public static String sortList(List<String> list) {
		if (list != null && list.size() > 0) {
			String[] strArr = list.toArray(new String[0]);
			Arrays.sort(strArr);
			String _res = Arrays.toString(strArr);
			return _res.substring(1, _res.length() - 1).replace(" ", "");
			// .replace(",", "");
		}
		return null;
	}

	public static String setListToString(List<String> list) {
		if (list != null && list.size() > 0) {
			String[] strArr = list.toArray(new String[0]);
			String _res = Arrays.toString(strArr);
			return _res.substring(1, _res.length() - 1).replace(" ", "");
			// .replace(",", "");
		}
		return null;
	}

	public static String getWebCon(String domain) {
		// System.out.println("开始读取内容...("+domain+")");
		StringBuffer sb = new StringBuffer();
		try {
			java.net.URL url = new java.net.URL(domain);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
		} catch (Exception e) { // Report any errors that arise
			sb.append(e.toString());
			System.err.println(e);
			System.err.println("Usage:   java   HttpClient   <URL>   [<filename>]");
		}
		return sb.toString();
	}

	/**
	 * 加密
	 * 
	 * @param password
	 * @return
	 */
	public static String encryptionKey(String password) {
		byte[] keyByte1 = key1.getBytes();
		byte[] keyByte2 = key2.getBytes();
		byte[] pwdByte = password.getBytes();
		for (int i = 0; i < pwdByte.length; i++) {
			pwdByte[i] = (byte) (pwdByte[i] ^ keyByte1[i % keyByte1.length]);
		}
		byte[] countByte = new byte[pwdByte.length + keyByte1.length];
		for (int i = 0; i < countByte.length; i++) {
			if (i < pwdByte.length)
				countByte[i] = pwdByte[i];
			else
				countByte[i] = keyByte1[i - pwdByte.length];
		}
		for (int i = 0; i < countByte.length; i++) {
			countByte[i] = (byte) (countByte[i] ^ keyByte2[i % keyByte2.length]);
		}
		return bytesToHexString(countByte);
	}

	/**
	 * 解密
	 * 
	 * @param password
	 * @return
	 */
	public static String decryptionKey(String password) {
		byte[] keyByte1 = key1.getBytes();
		byte[] keyByte2 = key2.getBytes();
		// password = hexStr2Str(password);
		byte[] pwdByte = hexStr2Bytes(password);

		for (int i = 0; i < pwdByte.length; i++) {
			pwdByte[i] = (byte) (pwdByte[i] ^ keyByte2[i % keyByte2.length]);
		}

		byte[] lastByte = new byte[pwdByte.length - keyByte1.length];
		for (int i = 0; i < lastByte.length; i++) {
			lastByte[i] = pwdByte[i];
		}
		for (int i = 0; i < lastByte.length; i++) {
			lastByte[i] = (byte) (lastByte[i] ^ keyByte1[i % keyByte1.length]);
		}

		return new String(lastByte);
	}

	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 十六进制转换字符串
	 * 
	 * @param String
	 *            str Byte字符串(Byte之间无分隔符 如:[616C6B])
	 * @return String 对应的字符串
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;

		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	/**
	 * bytes字符串转换为Byte值
	 * 
	 * @param String
	 *            src Byte字符串，每个Byte之间没有分隔符
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = Byte.decode("0x" + src.substring(i * 2, m) + src.substring(m, n));
		}
		return ret;
	}

	public static String getBundleStr(Bundle bundle, String key, String defVal) {

		return bundle.containsKey(key) ? bundle.getString(key) : defVal;

	}

	public static String getBundleStr(Bundle bundle, String key) {

		return getBundleStr(bundle, key, "");

	}

	public static List<String> sortNumList1(List<String> list) {

		Collections.sort(list, new Comparator<String>() {
			@Override
			public int compare(String arg0, String arg1) {
				try {
					return Integer.valueOf(arg0).compareTo(Integer.valueOf(arg1));
				} catch (Exception e) {
					return arg0.compareToIgnoreCase(arg1);
				}
			}
		});

		return list;
	}

	public static List<String> sortNumList(List<String> list) {
		int[] iArr = strArr2IntArr(setListToString(list).split(","));
		Arrays.sort(iArr);
		return Arrays.asList(intArr2Str(iArr).split(","));
	}

	public static String sortNumStr(String[] strArr) {
		if (strArr != null && strArr.length > 0) {
			Arrays.sort(strArr);
			String _res = Arrays.toString(strArr);
			return _res.substring(1, _res.length() - 1).replace(" ", "");
		}
		return "";
	}

	public static String sortNumStr(String str) {
		if (!isEmpty(str)) {
			char[] cArr = str.toCharArray();
			Arrays.sort(cArr);
			String _res = Arrays.toString(cArr);
			return _res.substring(1, _res.length() - 1).replace(" ", "").replace(",", "");
		}
		return "";
	}

	/***
	 * 检测字符串是否为空或无内容
	 * 
	 * @param srcString
	 * @return
	 */
	public static boolean isEmpty(String srcString) {
		if (srcString == null || ("").equals(srcString)) {
			return true;
		} else {
			return false;
		}
	}

	public static String intArr2Str(int[] iArr) {
		try {
			String _res = Arrays.toString(iArr);
			return _res.substring(1, _res.length() - 1).replace(" ", "");
		} catch (Exception e) {
		}
		return null;
	}

	public static int[] strArr2IntArr(String[] strArr) {

		if (strArr.length == 0) {
			return null;
		}

		int[] tmpIArr = new int[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			tmpIArr[i] = str2Int(strArr[i]);
		}
		return tmpIArr;
	}

	// public static String[] strArr2StringArr(String[] strArr) {
	//
	// if (strArr.length == 0) {
	// return null;
	// }
	//
	// String[] tmpIArr = new String[strArr.length];
	// for (int i = 0; i < strArr.length; i++) {
	// tmpIArr[i] = str2String(strArr[i]);
	// }
	// return tmpIArr;
	// }

	public static String _str2sArr(String str) {
		String tmpRes = Arrays.toString(str.toCharArray());
		return tmpRes.substring(1, tmpRes.length() - 1).replace(" ", "");
	}

	public static String[] str2sArr(String str) {
		if (isEmpty(str)) {
			return new String[] {};
		}
		return _str2sArr(str).split(",");
	}

	public static double str2Double(Object... ps) {
		try {
			return Double.valueOf(ps[0].toString());
		} catch (Exception ex) {
			if (ps.length != 1 && null != ps[1]) {
				return Double.valueOf(ps[1].toString());
			} else {
				return 0.0;
			}
		}
	}

	public static long str2Long(Object... ps) {
		try {
			return Long.valueOf(ps[0].toString());
		} catch (Exception ex) {
			if (ps.length != 1 && null != ps[1]) {
				return Long.valueOf(ps[1].toString());
			} else {
				return 0L;
			}
		}
	}

	public static float str2Float(Object... ps) {
		try {
			return Float.valueOf(ps[0].toString());
		} catch (Exception ex) {
			if (ps.length != 1 && null != ps[1]) {
				return Float.valueOf(ps[1].toString());
			} else {
				return 0.0f;
			}
		}
	}

	public static int str2Int(Object... ps) {
		try {
			return Integer.valueOf(ps[0].toString());
		} catch (Exception ex) {
			if (ps.length != 1 && null != ps[1]) {
				return Integer.valueOf(ps[1].toString());
			} else {
				return 0;
			}
		}
	}

	// public static String str2String(Object... ps) {
	// try {
	// return ps[0].toString();
	// } catch (Exception ex) {
	// if (ps.length != 1 && null != ps[1]) {
	// return ps[1].toString();
	// } else {
	// return null;
	// }
	// }
	// }
}
