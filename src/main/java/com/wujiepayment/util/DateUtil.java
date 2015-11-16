package com.wujiepayment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式帮助类
 * @author wangkai
 *
 */
public class DateUtil {
	private static String defaultFormatStr = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 按照指定格式转化时间
	 * @param formatStr
	 * @param date
	 * @return
	 */
	public static String getFormatDateStr(String formatStr,Date date){
		if("".equals(formatStr)){
			formatStr = defaultFormatStr;
		}
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(formatStr);  
		String nowData = localSimpleDateFormat.format(date);
		return nowData;
	}
}
