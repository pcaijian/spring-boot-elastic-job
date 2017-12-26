package com.elastic.util;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.http.util.TextUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;


/**
 * 通用工具类 
 */

@SuppressWarnings("unchecked")
public class CommonUtil {

	// 默认日期格式
	private static final String DATE_FMT = "yyyy-MM-dd"; // 日期

	private static final String TIME_FMT = "HH:mm:ss"; // 时间

	private static final String DATE_TIME_FMT = "yyyy-MM-dd HH:mm:ss"; // 日期时间
	
	private static final String DATE_TIME_FMT_MM = "yyyy-MM-dd HH:mm"; // 日期时间
	
	private static final String DATE_FMT_SHARE = "MM-dd HH:mm";//分享日期显示
	private static final String DATE_SHARE_TIME = "HH:mm";//分享日期显示


	// 验证的正则表达式
	private static final String REG_ALPHA = "^[a-zA-Z]+$";

	private static final String REG_ALPHANUM = "^[a-zA-Z0-9]+$";

	private static final String REG_NUMBER = "^\\d+$";
	
	private static final String REG_PAYPWD="^\\d{6}$";

	private static final String REG_INTEGER = "^[-+]?[1-9]\\d*$|^0$/";

	private static final String REG_FLOAT = "[-\\+]?\\d+(\\.\\d+)?$";

	private static final String REG_PHONE = "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$";

	private static final String REG_MOBILE = "^((\\+86)|(86))?(1)\\d{10}$";

	private static final String REG_QQ = "^[1-9]\\d{4,10}$";

	private static final String REG_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

	private static final String REG_ZIP = "^[1-9]\\d{5}$";

	private static final String REG_IP = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

	private static final String REG_URL = "^(http|https|ftp):\\/\\/(([A-Z0-9][A-Z0-9_-]*)(\\.[A-Z0-9][A-Z0-9_-]*)+)(:(\\d+))?\\/?/i";

	private static final String REG_CHINESE = "^[\\u0391-\\uFFE5]+$";

	private static final String REG_MONEY = "[\\-\\+]?\\d+(\\.\\d+)?$";
	
	private static final String REG_PWD = "^[a-zA-Z0-9]{6,20}$";

    private static final String RANDSTRING = "&*()_+0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^";//随机产生的字符串
    
	/** 通过正则表达验证 */
	public static boolean matchesByRegex(String regex, String value) {
		if (isNull(regex, value)) {
			return false;
		}
		return Pattern.matches(regex, value);
	}

	/** 可以用于判断Object,String,Map,Collection,String,Array是否为空 */
	@SuppressWarnings("rawtypes")
    public static boolean isNull(Object value) {
		if (value == null) {
			return true;
		} else if (value instanceof String) {
			if (((String) value).trim().replaceAll("\\s", "").equals("")) {
				return true;
			}
		} else if (value instanceof Collection) {
			if (((Collection) value).isEmpty()) {
				return true;
			}
		} else if (value.getClass().isArray()) {
			if (Array.getLength(value) == 0) {
				return true;
			}
		} else if (value instanceof Map) {
			if (((Map) value).isEmpty()) {
				return true;
			}
		} else {
			return false;
		}
		return false;

	}

	public static boolean isNull(Object value, Object... items) {
		if (isNull(value) || isNull(items)) {
			return true;
		}
		for (Object item : items) {
			if (isNull(item)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNotNull(Object value) {

		return !isNull(value);
	}

	public static boolean isNotNull(Object value, Object... items) {

		return !isNull(value, items);
	}

	/**
	 * 将数组转换成List<Map<String,Object>>对象
	 * 
	 * @param array
	 *            要转换的数组
	 * @return List<Map<String,Objct>>
	 */
	public static List<Map<String, Object>> arrayToList(String[] array) {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < array.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("value", i);
			item.put("text", array[i]);
			items.add(item);
		}
		return items;
	}

	/**
	 * linwenming Mar 30, 2012
	 * 
	 * @return Map<String,Object>
	 */
	public static Map<String, Object> arrayToMap(String[] array) {
		Map<String, Object> maps = new HashMap<String, Object>();
		for (int i = 0; i < array.length; i++) {
			maps.put(String.valueOf(i), array[i]);
		}
		return maps;
	}

	public static Map<String, Object> arrToMap(String[] array) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < array.length; i++) {
			map.put(array[i], array[i]);
		}
		return map;
	}

	public static boolean isAlpha(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_ALPHA, value);
	}

	public static boolean isAlphanum(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_ALPHANUM, value);
	}

	public static boolean isNumber(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_NUMBER, value);
	}
	
	public static boolean isPayPwd(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_PAYPWD, value);
	}

	public static boolean isInteger(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_INTEGER, value);
	}

	public static boolean isFloat(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_FLOAT, value);
	}

	public static boolean isMoney(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_MONEY, value);
	}

	public static boolean isPhone(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_PHONE, value);
	}

	public static boolean isMobile(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_MOBILE, value);
	}

	public static boolean isEmail(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_EMAIL, value);
	}
	
	public static boolean isPwd(String value) {
		if (isNull(value))
			return false;
		return Pattern.matches(REG_PWD, value);
	}

	public static boolean isQQ(String value) {

		return Pattern.matches(REG_QQ, value);
	}

	public static boolean isZip(String value) {

		return Pattern.matches(REG_ZIP, value);
	}

	public static boolean isIP(String value) {

		return Pattern.matches(REG_IP, value);
	}

	public static boolean isURL(String value) {

		return Pattern.matches(REG_URL, value);
	}

	public static boolean isChinese(String value) {

		return Pattern.matches(REG_CHINESE, value);
	}

	/** 验证是否为合法身份证 */
	public static boolean isIdcard(String value) {
		value = value.toUpperCase();
		if (!(Pattern.matches("^\\d{17}(\\d|X)$", value) || Pattern.matches(
				"\\d{15}$", value))) {
			return false;
		}
		int provinceCode = Integer.parseInt(value.substring(0, 2));
		if (provinceCode < 11 || provinceCode > 91) {
			return false;
		}
		return true;
	}

	public static boolean isDate(String value) {
		if (value == null || value.isEmpty())
			return false;
		try {
			new SimpleDateFormat().parse(value);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** 获取日期 */
	public static Date getCurrentDateTime() {

		return getCurrentDateTime(DATE_TIME_FMT);
	}

	public static Date getCurrentDate() {

		return getCurrentDate(DATE_FMT);
	}

	public static Date getCurrentTime() {

		return getCurrentTime(TIME_FMT);
	}

	public static Date getCurrentDateTime(String fmt) {

		return dateStrToDate(fmt, getCurrentDateTimeStr(fmt));
	}

	public static Date getCurrentDate(String fmt) {

		return dateStrToDate(fmt, getCurrentDateStr(fmt));
	}

	public static Date getCurrentTime(String fmt) {

		return dateStrToDate(fmt, getCurrentTimeStr(fmt));
	}

	public static String getCurrentDateTimeStr() {

		return getCurrentDateTimeStr(DATE_TIME_FMT);
	}

	public static String getCurrentTimeStr() {

		return getCurrentTimeStr(TIME_FMT);
	}

	public static String getCurrentDateStr() {

		return getCurrentDateStr(DATE_FMT);
	}

	public static String getCurrentDateTimeStr(String fmt) {

		String temp = new SimpleDateFormat(fmt).format(new Date());

		return temp;
	}

	public static String getCurrentTimeStr(String fmt) {

		String temp = new SimpleDateFormat(fmt).format(new Date());

		return temp;
	}

	public static String getCurrentDateStr(String fmt) {

		String temp = new SimpleDateFormat(fmt).format(new Date());

		return temp;
	}

	public static String dateToDateStr(Date date) {

		String temp = new SimpleDateFormat(DATE_TIME_FMT).format(date);

		return temp;
	}

	public static String dateToDateStr(String fmt, Date date) {

		String temp = new SimpleDateFormat(fmt).format(date);

		return temp;
	}
	
	/**日期字符串为是否为前一天
	 * @throws ParseException */
	public static String shareDateFmt(String str) throws ParseException{
		
		String res = str;
		
		boolean flag = false;
		SimpleDateFormat dft = new SimpleDateFormat(DATE_FMT);
		
		SimpleDateFormat hms = new SimpleDateFormat(DATE_SHARE_TIME);
		
		SimpleDateFormat shareFmt = new SimpleDateFormat(DATE_FMT_SHARE);
		
		Date beginDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(beginDate);
		c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
		String dateStr = dft.format(c.getTime());
		
		Calendar cld = Calendar.getInstance();
		cld.setTime(beginDate);
		cld.set(Calendar.DATE, cld.get(Calendar.DATE) - 2);
		String cldStr = dft.format(cld.getTime());
		
		String obj = dft.format(dateStrToDate(str));
		if(dft.format(beginDate).equals(dft.format(dateStrToDate(str)))){
			res = hms.format(dateStrToDatetime(str));
			flag = true;
		}
		if(dateStr.equals(obj)){
			res = "昨天  "+hms.format(dateStrToDatetime(str));
			flag = true;
		}
		if(cldStr.equals(obj)){
			res = "前天  "+hms.format(dateStrToDatetime(str));
			flag = true;
		}
		if(!flag){
			res = shareFmt.format(dateStrToDatetime(str));
		}
		return res ;
	}

	/** 转换为日期对象 */
	public static Date dateStrToDate(String date) {
		Date temp = null;
		try {
			temp = new SimpleDateFormat(DATE_FMT).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/** 字符串转换为日期时间格式对象 */
	public static Date dateStrToDatetime(String date) {
		if (date == null) {
			return null;
		}
		Date temp = null;
		try {
			temp = new SimpleDateFormat(DATE_TIME_FMT).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return temp;
	}

	
	/** 字符串转换为日期时间格式对象 */
	public static Date dateStrToTime(String date) {
		if (date == null) {
			return null;
		}
		Date temp = null;
		try {
			temp = new SimpleDateFormat(DATE_TIME_FMT_MM).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return temp;
	}
	public static Date dateStrToDate(String fmt, String date) {
		Date temp = null;
		try {
			temp = new SimpleDateFormat(fmt).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/** 格式化日期 */
	public static String formatDateTime(Date date) {

		return formatDateTime(DATE_TIME_FMT, date);
	}

	public static String formatDateTime(String fmt, Date date) {
		if (isNull(fmt) || isNull(date)) {
			return null;
		}
		String temp = new SimpleDateFormat(fmt).format(date);

		return temp;
	}

	public static String formatTime(Date date) {
		return formatTime(TIME_FMT, date);
	}

	public static String formatTime(String fmt, Date date) {
		if (isNull(fmt) || isNull(date)) {
			return null;
		}
		String temp = new SimpleDateFormat(fmt).format(date);

		return temp;
	}

	public static String formatDate(Date date) {
		return formatDate(DATE_FMT, date);
	}

	public static String formatDate(String fmt, Date date) {
		if (isNull(fmt) || isNull(date)) {
			return null;
		}
		String temp = new SimpleDateFormat(fmt).format(date);

		return temp;
	}

	public static String formatNumber(String fmt, Object value) {
		if (isNull(fmt) || isNull(value)) {
			return null;
		}
		String temp = new DecimalFormat(fmt).format(value);

		return temp;
	}

	/** 交换两个日期 */
	public static void changeDate(Date date1, Date date2) {
		if (isNull(date1, date2)) {
			return;
		}
		// 判断两个时间的大小
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		if (c1.after(c2)) {
			date1 = c2.getTime();
			date2 = c1.getTime();
		}
	}
	
	
	/** 比较两个日期的大小 */
	public static Boolean date1ToDate2(Date date1, Date date2) {
		if (isNull(date1, date2)) {
			return false;
		}
		// 判断两个时间的大小
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		if (c1.after(c2)) {
			return true;
		}else {
			return false;
		}
	}

	/** 比较两个日期相差的年数 */
	public static int compareYear(Date date1, Date date2) {
		if (isNull(date1) || isNull(date2)) {
			return 0;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);

		if (c1.equals(c2)) {
			return 0;
		}

		if (c1.after(c2)) {
			Calendar temp = c1;
			c1 = c2;
			c2 = temp;
		}
		// 计算差值
		int result = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		return result;
	}

	/** 比较两个日期相差的天数 */
	public static int compareDay(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;

		Calendar d1 = Calendar.getInstance();
		d1.setTime(date1);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(date2);
		if (d1.after(d2)) {
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		/*
		 * 经过上面的处理，保证d2在d1之后
		 * 下面这个days可能小于0，因为d2和d1可能不在同一年里，这样的话虽然d1的年份小，但其在一年中的"第几天"却可能比d2大。
		 */
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {// 如果不在同一年
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				/*
				 * 给定此 Calendar 的时间值，返回指定日历字段可能拥有的最大值。 例如，在某些年份中，MONTH 字段的实际最大值是
				 * 12，而在希伯来日历系统的其他年份中，该字段的实际最大值是 13。 DAY_OF_YEAR：闰年366？
				 */
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;

	}

	/** 比较两个日期相差的周数 */
	public static int compareWeek(Date date1, Date date2) {
		if (isNull(date1) || isNull(date2)) {
			return 0;
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);

		if (c1.equals(c2)) {
			return 0;
		}

		if (c1.after(c2)) {
			Calendar temp = c1;
			c1 = c2;
			c2 = temp;
		}
		// 计算差值
		int result = c2.get(Calendar.WEEK_OF_MONTH)
				- c1.get(Calendar.WEEK_OF_MONTH);
		return result;
	}

	/** 比较两个日期相差的月数 */
	public static int compareMonth(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;

		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);

			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);

			if (objCalendarDate2.equals(objCalendarDate1))
				return 0;
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}

			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
					.get(Calendar.YEAR))
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
						.get(Calendar.YEAR))
						* 12
						+ objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			else
				iMonth = objCalendarDate2.get(Calendar.MONTH)
						- objCalendarDate1.get(Calendar.MONTH) - flag;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/** 比较两个日期相差的秒数 */
	public static long compareTime(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;

		Calendar c = Calendar.getInstance();

		c.setTime(date1);
		long l1 = c.getTimeInMillis();

		c.setTime(date2);
		long l2 = c.getTimeInMillis();

		return (l2 - l1) / 1000;
	}
	
	/** 比较两个日期相差的分钟数*/
	public static long compareTime(Long l1, Long l2) {
		if (l1 == null || l2 == null)
			return 0;
		return (l2 - l1) / 1000/60;
	}
	
	
	
	/**
	 * 计算两个日期的天数差
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getIntervalDays(Date oDate, Date cDate) {
		if (null == oDate || null == cDate) {
			return -1;
		}
		long intervalMilli = cDate.getTime() - oDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}
	
	/**
	 * 获取某个时间的年份
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int getYearOfDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * 获取某个时间的年份
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static Date dateAddition(Date date,int dayInterval) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH) + dayInterval);
		return calendar.getTime();
	}


	// 设置时间
	/**
	 * 计算日期 1:添加年 2：添加月 3:添加日 4：添加时 5：添加分
	 * 
	 * @param date
	 * @param type
	 * @param num
	 * @return
	 */
	private static Date addDateTime(Date date, int type, int num) {
		if (date == null) {
			return null;
		}
		// 初始化日历对象
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// 根据类型添加
		switch (type) {
		case 1: // 添加年
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + num);
			break;
		case 2: // 添加月
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + num);
			break;
		case 3: // 添加日
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + num);
			break;
		case 4: // 添加时
			cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + num);
			break;
		case 5: // 添加分
			cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + num);
			break;
		case 6: // 添加秒
			cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) + num);
			break;
		}

		// 返回操作结果
		return cal.getTime();
	}

	// 设置日期时间
	private static Date setDateTime(Date date, int type, int num) {
		if (date == null) {
			return null;
		}
		// 初始化日历对象
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// 根据类型添加
		switch (type) {
		case 1: // 添加年
			cal.set(Calendar.YEAR, num);
			break;
		case 2: // 添加月
			cal.set(Calendar.MONTH, num);
			break;
		case 3: // 添加日
			cal.set(Calendar.DATE, num);
			break;
		case 4: // 添加时
			cal.set(Calendar.HOUR_OF_DAY, num);
			break;
		case 5: // 添加分
			cal.set(Calendar.MINUTE, num);
			break;
		case 6: // 添加秒
			cal.set(Calendar.SECOND, num);
			break;
		}

		// 返回操作结果
		return cal.getTime();
	}

	/** 设置年、月、日 */
	public static Date setYMD(Date date, int year, int month, int day) {

		return setYear(setMonth(setDate(date, day), month), year);
	}

	public static Date setYear(Date date, int num) {
		return addDateTime(date, 1, num);
	}

	public static Date setMonth(Date date, int num) {
		return addDateTime(date, 2, num);
	}

	public static Date setDate(Date date, int num) {
		return addDateTime(date, 3, num);
	}

	/** 设置时、分、秒 */
	public static Date setHMS(Date date, int hour, int minute, int second) {

		return setHour(setMinute(setSecond(date, second), minute), hour);
	}

	public static Date setHour(Date date, int num) {
		return setDateTime(date, 4, num);
	}

	public static Date setMinute(Date date, int num) {
		return setDateTime(date, 5, num);
	}

	public static Date setSecond(Date date, int num) {
		return setDateTime(date, 6, num);
	}

	/** 添加年、月、日、时、分、秒 */
	public static Date addYear(Date date, int num) {
		return addDateTime(date, 1, num);
	}

	public static Date addMonth(Date date, int num) {
		return addDateTime(date, 2, num);
	}

	public static Date addDate(Date date, int num) {
		return addDateTime(date, 3, num);
	}

	/** 添加年、月、日 */
	public static Date addYMD(Date date, int year, int month, int day) {

		return addYear(addMonth(addDate(date, day), month), year);
	}

	public static Date addHour(Date date, int num) {
		return addDateTime(date, 4, num);
	}

	public static Date addMinute(Date date, int num) {
		return addDateTime(date, 5, num);
	}

	public static Date addSecond(Date date, int num) {
		return addDateTime(date, 6, num);
	}

	/** 添加时、分、秒 */
	public static Date addHMS(Date date, int hour, int minute, int second) {

		return addHour(addMinute(addSecond(date, second), minute), hour);
	}

	/** MD5加密 */
	public static String md5(String value) {
		StringBuilder result = new StringBuilder();

		try {
			// 实例化MD5加载类
			MessageDigest md5 = MessageDigest.getInstance("MD5");

			// 得到字节数据
			byte[] data = md5.digest(value.getBytes("UTF-8"));

			result.append(byte2hex(data));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回结果
		return result.toString().toUpperCase();
	}
	
	public static String emd5(String str) {
	    char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	            'a', 'b', 'c', 'd', 'e', 'f' };
	    MessageDigest md5;
	    try {
	        md5 = MessageDigest.getInstance("MD5");
	        md5.update(str.getBytes("UTF-8"));
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException(e.getMessage());
	    }
	    byte[] encodedValue = md5.digest();
	    int j = encodedValue.length;
	    char finalValue[] = new char[j * 2];
	    int k = 0;
	    for (byte encoded : encodedValue) {
	        finalValue[k++] = hexDigits[encoded >> 4 & 0xf];
	        finalValue[k++] = hexDigits[encoded & 0xf];
	    }

	    return new String(finalValue);
	}

	public static String byte2hex(byte[] data) {

		StringBuilder result = new StringBuilder();

		for (byte b : data) {
			// 将二进制转换成字符串
			String temp = Integer.toHexString(b & 0XFF);
			// 追加加密后的内容
			if (temp.length() == 1) { // 判断字符长度
				result.append("0").append(temp);
			} else {
				result.append(temp);
			}
		}

		return result.toString();
	}

	public static String substring(String str, int len) {

		return substring(str, len, null);
	}

	public static String substring(String str, int len, String replaceChar) {

		return substring(str, 0, len, replaceChar);
	}

	public static String substring(String str, int startIndex, int len,
			String replaceChar) {
		String temp = str;

		if (!isNull(str) && str.length() > len) {
			temp = str.substring(startIndex, len + startIndex)
					+ (isNull(replaceChar) ? "" : replaceChar);
		}

		return temp;
	}

	public static String htmlEncode(String value) {
		String result = "";
		if (!isNull(value)) {
			result = value.replaceAll("&", "&amp;").replaceAll(">", "&gt;")
					.replaceAll("<", "&lt;").replaceAll("\"", "&quot;")
					.replaceAll(" ", "&nbsp;").replaceAll("\r?\n", "<br/>");
		}
		return result;
	}

	public static String htmlDecode(String value) {
		String result = "";
		if (!isNull(value)) {
			result = value.replaceAll("&amp;", "&").replaceAll("&gt;", ">")
					.replaceAll("&lt;", "<").replaceAll("&quot;", "\"")
					.replace("&nbsp;", " ");
		}
		return result;
	}

	/** 字符串编码(默认使用UTF-8) */
	public static String stringEncode(String value) {
		return stringEncode(value, "UTF-8");
	}

	public static String stringEncode(String value, String encoding) {
		String result = null;
		if (!isNull(value)) {
			try {
				if (isNull(encoding)) {
					encoding = "UTF-8";
				}
				result = new String(value.getBytes("ISO-8859-1"), encoding);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 格式式化字符串 允许使用{0}{1}...作为为占位符
	 * 
	 * @param value
	 *            要格式化的字符串
	 * @param args
	 *            点位符的值
	 * @return 格式化后的字符串
	 */
	public static String stringFormat(String value, Object... args) {
		// 判断是否为空
		if (isNull(value) || isNull(args)) {
			return value;
		}
		String result = value;
		Pattern p = Pattern.compile("\\{(\\d+)\\}");
		Matcher m = p.matcher(value);
		while (m.find()) {
			// 获取{}里面的数字作为匹配组的下标取值
			int index = Integer.parseInt(m.group(1));
			// 这里得考虑数组越界问题，{1000}也能取到值么？？
			if (index < args.length) {
				// 替换，以{}数字为下标，在参数数组中取值
				result = result.replace(m.group(), args[index].toString());
			}
		}
		return result;
	}

	public static String leftPad(String value, int len, char c) {
		if (isNull(value, len, c)) {
			return value;
		}
		int v = len - value.length();
		for (int i = 0; i < v; i++)
			value = c + value;
		return value;
	}

	public static String rightPad(String value, int len, char c) {
		if (isNull(value, len, c)) {
			return value;
		}
		int v = len - value.length();
		for (int i = 0; i < v; i++)
			value += c;
		return value;
	}

	/** 处理对象的String类型的属性值进行html编码 */
	@SuppressWarnings("rawtypes")
    public static void objectHtmlEncode(Object object) {
		if (!isNull(object)) {
			Method[] mList = object.getClass().getMethods();
			for (Method method : mList) {
				// 方法名
				String mName = method.getName();
				// 得到方法的方法值类型
				String mRetrunType = method.getReturnType().getSimpleName();
				// 得到方法的参数个数
				int mParamSize = method.getParameterTypes().length;
				// 判断方法值是否是String并参数个数为0
				if (mRetrunType.equals("String") && mParamSize == 0) {
					// 判断方法是否是以get开头
					if (mName.startsWith("get")) {
						// 得到相对应的set方法
						Method setMethod = null;
						String setMethodName = "set" + mName.substring(3);
						// 只有一个String类型的参数
						Class[] paramClass = { String.class };
						try {
							setMethod = object.getClass().getMethod(
									setMethodName, paramClass);
							// 判断set方法的返回值是否为空
							if (!setMethod.getReturnType().getSimpleName()
									.equals("void")) {
								continue; // 查看下一个方法
							}
						} catch (SecurityException e) {
							continue; // 查看下一个方法
						} catch (NoSuchMethodException e) {
							continue; // 查看下一个方法
						}
						Object[] params = null;
						try {
							// 得到方法的值
							String mValue = method.invoke(object, params)
									.toString();
							// 对值进行html编码
							mValue = htmlEncode(mValue);
							// 编码后重新赋值
							params = new Object[] { mValue };
							setMethod.invoke(object, params);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/** 根据属性名得到属性值(entity中必需存在get,set相应方法) */
	public static Object getPropValue(Object entity, String propName) {
		Object result = null;
		// 判断对象和属性名是否为空
		if (isNull(entity) || isNull(propName)) {
			return result;
		} else {
			try {
				// 调用方法得到get方法值
				Method getMethod = entity.getClass().getMethod(propName.trim());
				result = getMethod.invoke(entity);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	

	/** 生成随机UUID */
	public static String genUUID() {
		UUID uuid = UUID.randomUUID();
		String temp = uuid.toString();
		return temp.replaceAll("-", "").toUpperCase();
	}

	/** 随机数字 */
	public static String getRandNum(int len) {
		String result = "";
		String temp = genUUID().replaceAll("[A-Z]+", "");

		int tempLen = temp.length();
		if (len > 32) {
			len = 32;
		}
		if (tempLen < len) {
			result = rightPad(temp, len, '0');
		} else {
			for (int i = 0; i < len; i++) {
				int rnd = new Random().nextInt(tempLen);
				result += temp.charAt(rnd);
			}
		}

		return result;
	}
	
	/**
	 * 随机生成5~15位数的盐
	 * @param len
	 * @return
	 */
	public static String randomSalt() {
		Random random = new Random();
		int len = random.nextInt(10) + 5;
        String salt = "";
        for(int i=0;i<len;i++){
        	random = new Random();
        	salt += String.valueOf(String.valueOf(CommonUtil.RANDSTRING.charAt(random.nextInt(CommonUtil.RANDSTRING.length()))));
        }
        return salt;
	}
	

	/**
	 * 用途：将父类型进行拆箱，转成具体类型
	 */
	public static <T> T convert(Object value, Class<T> clazz) {
		if (value == null)
			return null;
		Object result = null;
		try {
			result = ConvertUtils.convert(value, clazz);
		} catch (ClassCastException ex) {
			throw new ClassCastException("类型转换失败,不能将" + value.getClass()
					+ "转换成" + clazz);
		}
		return result == null ? null : (T) result;
	}

	/** 保存文件 */
	public static void saveToFile(File target, String distPath)
			throws IOException {
		if (isNull(target, distPath)) {
			return;
		}
		File distFile = new File(distPath);
		// 确保文件所在的文件夹都存在
		distFile.getParentFile().mkdirs();

		// 输入流
		InputStream is = new BufferedInputStream(new FileInputStream(target));
		// 输出流
		OutputStream os = new BufferedOutputStream(new FileOutputStream(
				distFile));
		// 每次读取的大小
		byte[] size = new byte[1024];
		// 流长度
		int len = 0;
		// 循环读取
		while ((len = is.read(size)) != -1) {
			os.write(size, 0, len);
		}
		os.flush();
		os.close();
		is.close();
	}

	public static void saveToFile(InputStream target, String distPath)
			throws IOException {
		if (isNull(target, distPath)) {
			return;
		}
		File distFile = new File(distPath);
		// 确保文件所在的文件夹都存在
		distFile.getParentFile().mkdirs();

		// 输入流
		InputStream is = new BufferedInputStream(target);
		// 输出流
		OutputStream os = new BufferedOutputStream(new FileOutputStream(
				distFile));
		// 每次读取的大小
		byte[] size = new byte[1024];
		// 流长度
		int len = 0;
		// 循环读取
		while ((len = is.read(size)) != -1) {
			os.write(size, 0, len);
		}
		os.flush();
		os.close();
		is.close();
	}

	/** 得到Cookie */
	public static Cookie getCookie(String name, HttpServletRequest request) {
		Cookie[] cks = request.getCookies();
		if (cks != null) {
			for (Cookie cookie : cks) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}

		return null;
	}

	/** 创建Cookie */
	public static void setCookie(String name, String value, Integer maxAge,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		if (!isNull(maxAge)) {
			cookie.setPath("/");
			cookie.setMaxAge(maxAge.intValue());
		}
		response.addCookie(cookie);
	}

	/** 创建Cookie */
	public static void setCookie(String name, String value,
			HttpServletResponse response) {
		setCookie(name, value, null, response);
	}

	/** 删除Cookie */
	public static void delCookie(String name, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}




	/** 使用正则表达查询字符串 */
	public static List<String> findStr(Object target, String regex) {
		if (isNull(target, regex)) {
			return null;
		}
		Pattern pattern = Pattern.compile(regex); // 正则表达式
		Matcher matcher = pattern.matcher(target.toString()); // 操作的字符串
		List<String> tmp = new ArrayList<String>();
		while (matcher.find()) {
			tmp.add(matcher.group());
		}
		return tmp;
	}


	
	

	/**
	 * 直接删除非空目录
	 * 
	 * @param dir
	 *            File对象
	 */
	public static void deleteDirectory(File dir) {
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return; // 检查参数
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); // 删除所有文件
			else if (file.isDirectory())
				deleteDirectory(file); // 递规的方式删除文件夹
		}
		dir.delete();// 删除目录本身
	}

	/**
	 * 直接删除非空目录
	 * 
	 * @param dirPath
	 *            要删除的目录的绝对路径
	 */
	public static void deleteDirectory(String dirPath) {
		File dir = new File(dirPath);
		deleteDirectory(dir);
	}

	/**
	 * 使用Spring上传文件
	 * 
	 * @param request
	 *            包含上传文件的request对象
	 * @param fileField
	 *            文件域的名字(默认：file)
	 * @param fileDir
	 *            上传文件储存的目录(默认：upload)
	 * @param types
	 *            允许上传的文件类型(默认为所有)
	 * @return 上传后生成的文件名
	 */
	public static String uploadFile(DefaultMultipartHttpServletRequest request,
			String fileField, String fileDir, String[] types) {
		String file_name = "";
		// 文件上传
		CommonsMultipartFile file = (CommonsMultipartFile) request
				.getFile(isNull(fileField) ? "file" : fileField);
		if (file != null && !file.isEmpty()) {
			// 对文件类型的判断
			String suffix = file.getOriginalFilename()
					.substring(file.getOriginalFilename().lastIndexOf(".") + 1)
					.toLowerCase();
			if (isNotNull(types)) {
				boolean flag = false;
				for (String type : types) {
					if (type.toLowerCase().equals(suffix)) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					return null;
				}
			}
			// 判断结束

			file_name = System.currentTimeMillis() + "." + suffix;
			// 文件名
			String file_dir = request.getSession().getServletContext()
					.getRealPath(isNull(fileDir) ? "upload" : fileDir)
					+ File.separator;
			// 保存到本地
			File fFile = new File(file_dir); // 文件的目录
			File uFile = new File(fFile, file_name); // 上传的文件
			try {
				fFile.mkdirs();
				FileCopyUtils.copy(file.getBytes(), uFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 返回结果
		return file_name;
	}

	/**
	 * 用途：改变图片尺寸
	 * 
	 * @param oldUrl
	 *            原图片文件绝对路径（含文件名）
	 * @param newUrl
	 *            目标图片文件绝对路径（含文件名）
	 * @param width
	 *            目标图片文件宽度
	 * @param height
	 *            目标图片文件高度
	 * @param proportion
	 *            是否等比例缩放
	 * @return
	 * @throws Exception
	 * 
	 *             此方法支持的图片文件格式有：jpg、gif、png 不支持的图片文件格式有：bmp 其它图片文件格式未测试
	 */
	@SuppressWarnings("resource")
    public static boolean resizeImg(String oldUrl, String newUrl, int width,
			int height, boolean proportion) throws Exception {
		File fileIn = new File(oldUrl);
		File fileOut = new File(newUrl);
		FileOutputStream tempout = null;
		try {
			tempout = new FileOutputStream(fileOut);
		} catch (Exception ex) {
			throw ex;
		}
		Image img = null;
		Applet app = new Applet();
		MediaTracker mt = new MediaTracker(app);
		try {
			img = javax.imageio.ImageIO.read(fileIn);
			mt.addImage(img, 0);
			mt.waitForID(0);
		} catch (Exception e) {
			throw e;
		}

		int old_w = img.getWidth(null);
		int old_h = img.getHeight(null);
		if (old_w == -1) {
			return false;
		} else if (height <= 0 && width <= 0) {
			return false;
		} else {
			int new_w;
			int new_h;
			if (height <= 0) {
				new_w = width;
				new_h = (int) Math.round(1.0 * new_w * old_h / old_w);
			} else if (width <= 0) {
				new_h = height;
				new_w = (int) Math.round(1.0 * new_h * old_w / old_h);
			} else if (proportion == true) {// 判断是否等比例缩放
				// 计算比率
				double rate1 = 1.0 * old_w / width;
				double rate2 = 1.0 * old_h / height;
				if (rate1 > rate2) {
					new_w = width;
					new_h = (int) Math.round(1.0 * new_w * old_h / old_w);
				} else {
					new_h = height;
					new_w = (int) Math.round(1.0 * new_h * old_w / old_h);
				}
			} else {
				new_w = width;
				new_h = height;
			}

			BufferedImage buffImg = new BufferedImage(new_w, new_h,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = buffImg.createGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, new_w, new_h);
			g.drawImage(img, 0, 0, new_w, new_h, null);
			g.dispose();
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(tempout);
			try {
//				encoder.encode(buffImg);
				tempout.close();
			} catch (IOException ex) {
				throw ex;
			}
		}
		return true;
	}

	/** 隐藏部分验证码数据 */
	public static String hideCode(String isHide, String target) {
		// 0.不隐藏 1.隐藏
		if (!"0".equals(isHide)) {
			return target;
		}

		int code_len = 0;
		if (target != null) {
			target = target.replaceAll("\\s", "");
			code_len = target.length();
		} else {
			return "";
		}

		if (code_len > 8) {
			String s1 = target.substring(0, 4);
			String s2 = target.substring(4, code_len - 4)
					.replaceAll("\\w", "*");
			String s3 = target.substring(code_len - 4, code_len);
			if (s2.length() > 6) {
				s2 = s2.substring(0, 6);
			}
			String code = s1 + s2 + s3;

			return code;
		} else {
			return target.replaceAll("\\w", "*");
		}
	}

	/** 隐藏部分验证码数据 */
	public static String hideCode(String target) {
		return hideCode("0", target);
	}

	/** 月的第一天 */
	public static Date getMonthFirstDay(int i) {
		// i = 0 是本月 1 上一月
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -i);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/** 月的最后一天 */
	public static Date getMonthLastDay(int i) {

		// i = 0 是本月 1 上一月
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -i);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.roll(Calendar.DAY_OF_MONTH, -1);

		return c.getTime();
	}

	/** 连接到登录接口并获取数据 */
	public static String getLoginInfo(String urlStr) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			// connection.setConnectTimeout(15000);
			// connection.setReadTimeout(15000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();
			// DataOutputStream out = new
			// DataOutputStream(connection.getOutputStream());
			// out.write(content.getBytes("utf-8")); //用utf-8的编码方式传递参数，否则中文会出现乱码
			// out.flush();
			// out.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return null;
	}

	


	/** 通过文件相对路径得到Web项目下的绝对路径 */
	public static String getPath(String path) {
		URL url = CommonUtil.class.getResource(path);
		if (url != null) {
			String abs_path = CommonUtil.class.getResource(path).toString();
			abs_path = abs_path.substring(abs_path.indexOf("/") + 1);
			return abs_path;
		}
		return null;
	}

	/**
	 * 获取当前时间字符串 如:201212061456123
	 * 
	 * @return
	 */
	public static String get14CurrentDateTimeStr() {
		Calendar currTime = Calendar.getInstance();
		String year = String.valueOf(currTime.get(Calendar.YEAR));
		String month = String.valueOf(currTime.get(Calendar.MONTH) + 1);
		String day = String.valueOf(currTime.get(Calendar.DATE));
		String hour = String.valueOf(currTime.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(currTime.get(Calendar.MINUTE));
		String second = String.valueOf(currTime.get(Calendar.SECOND));
		StringBuffer temp = new StringBuffer();

		temp.append(year);
		if (month.length() == 1)
			temp.append("0");
		temp.append(month);
		if (day.length() == 1) {
			temp.append("0");
		}
		temp.append(day);
		if (hour.length() == 1) {
			temp.append("0");
		}
		temp.append(hour);
		if (minute.length() == 1) {
			temp.append("0");
		}
		temp.append(minute);
		if (second.length() == 1) {
			temp.append("0");
		}
		temp.append(second);

		return temp.toString();
	}

	/**
	 * 获取项目物理路径
	 */
	public static String getProjectPath() {
		String path = CommonUtil.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("/WEB-INF"));
		}
		if (!path.endsWith("/")) {
			path = path + "/";
		}
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		return path;
	}

	/** 将字符串转换为整型 */
	public static int getInteger(String str) {
		int val = 0;
		if (str != null && !str.equals("")) {
			val = Integer.valueOf(str);
		}
		return val;
	}

	// 获取当前日期的最后时间 如2012-12-31 23:59:59
	public static long getCurrentDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		return calendar.getTime().getTime();
	}

	public static long getDateStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		return calendar.getTime().getTime();
	}

	/**
	 * 根据request获取IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}

	/**
	 * 获取传递时间所在月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getMonthEndByDate(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}

	/**
	 * 获取传递时间所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}

	/**
	 * 获取传递时间所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		return c.getTime();
	}
	
	/**
	 * 当前时间前几个月
	 * @param x
	 * @return
	 */
	public static String getMonthOfDate(int x){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -x);
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FMT);
		return sdf.format(calendar.getTime());
	}
	
	@SuppressWarnings("static-access")
    public static Icon getFixedBoundIcon(String filePath, int height, int width) throws Exception{ 
        double Ratio=0.0; //缩放比例 
        File F = new File(filePath); 
        if (!F.isFile()) 
                throw new Exception(F+" is not image file error in getFixedBoundIcon!"); 
        Icon ret = new ImageIcon(filePath); 
        BufferedImage Bi = ImageIO.read(F); 
        if ((Bi.getHeight()>height) || (Bi.getWidth()>width)){ 
            if (Bi.getHeight()>Bi.getWidth()){ 
                    Ratio = (new Integer(height)).doubleValue() /Bi.getHeight(); 
            } else { 
                    Ratio = (new Integer(width)).doubleValue()/Bi.getWidth(); 
            } File ThF = new File(filePath); 
            Image Itemp = Bi.getScaledInstance (width,height,Bi.SCALE_SMOOTH); 
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(Ratio, Ratio), null); 
            Itemp = op.filter(Bi, null); 
            try { 
                    ImageIO.write((BufferedImage)Itemp, "jpg", ThF); 
                    ret = new ImageIcon(ThF.getPath()); 
            }catch (Exception ex) {
                    
            } 
        }
        return ret; 
	}
	
	public static String getIconV(String sql){
		String str="";
		str ="select s.*,c.status from ("+sql+")s left join t_certificates c on c.mobile = s.mobile ";
		return str;
	}
	
	public static String getIndustry(){
		String[] INDUSTRY = new String[]{"互联网", "计算机", "通信", "商业", "服务业", "个体经营", "生产", "工艺",
		        "制造", "金融", "投资", "银行", "证券", "保险", "教育", "培训", "广告",
		        "传媒", "文化", "艺术", "表演", "娱乐", "务农", "养殖", "医疗", "制药",
		        "律师", "法务", "公务员", "事业单位", "学生", "其他"};
		return INDUSTRY[(int) (21*Math.random())];
	}

	public static String toNotice(String content, Map<String, Object> data) {
		StringBuilder builder = new StringBuilder();
		builder.append("有一条投诉消息,").append(content);
		builder.append(":[");
		builder.append("投诉者(").append(data.get("comName")).append(",").append(data.get("comMobile")).append("),");
		builder.append("服务者(").append(data.get("serName")).append(",").append(data.get("serMobile")).append("),");
		builder.append("内容:").append(data.get("content")).append(",");
		builder.append("投诉时间:").append(data.get("createTime")).append("");
		builder.append("]");
		return builder.toString();
	}
	
	public static String decode(String unicodeStr) {  
	    if (unicodeStr == null) {  
	        return null;  
	    }  
	    StringBuffer retBuf = new StringBuffer();  
	    int maxLoop = unicodeStr.length();  
	    for (int i = 0; i < maxLoop; i++) {  
	        if (unicodeStr.charAt(i) == '\\') {  
	            if ((i < maxLoop - 5)  
	                    && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr  
	                            .charAt(i + 1) == 'U')))  
	                try {  
	                    retBuf.append((char) Integer.parseInt(  
	                            unicodeStr.substring(i + 2, i + 6), 16));  
	                    i += 5;  
	                } catch (NumberFormatException localNumberFormatException) {  
	                    retBuf.append(unicodeStr.charAt(i));  
	                }  
	            else  
	                retBuf.append(unicodeStr.charAt(i));  
	        } else {  
	            retBuf.append(unicodeStr.charAt(i));  
	        }  
	    }  
	    return retBuf.toString();  
	}  

//	/**
//	 * md5加密密码
//	 * 方式：md5(md5+salt)，将盐随机插入一次md5后的密码中再md5加密
//	 * @param str
//	 * @return
//	 */
//	public static String md5Pwd(String str,String salt) {
//		String fMd5pwd = CommonUtil.md5(str);//第一次使用md5加密原始密码
//		System.out.println("第一次加密后的字符串："+fMd5pwd+";长度为:"+fMd5pwd.length());
//		Random random = new Random();
//		List<String> pwdList = new LinkedList<String>(Arrays.asList(fMd5pwd.split("")));
//		pwdList.remove(0);//删除第一个多余的符号
//		for (int i = 0; i < salt.length(); i++) {//将盐随机插入第一次md5加密后的密码中
//			pwdList.add(random.nextInt(fMd5pwd.length()), String.valueOf(salt.charAt(i)));
//		}
//		StringBuffer sbf = new StringBuffer();
//		//将混淆后的元素组装成字符串
//		for (int i = 0; i < pwdList.size(); i++) {
//			sbf.append(pwdList.get(i));
//		}
//		System.out.println("混合后字符串："+sbf.toString()+";长度为:"+sbf.toString().length());
//		return CommonUtil.md5(sbf.toString());
//	}
	
	/**
	 * md5加密密码
	 * 方式：md5(md5+salt)，将盐随机插入一次md5后的密码中再md5加密
	 * @param str
	 * @return
	 */
	public static String md5Pwd(String str,String salt) {
		return CommonUtil.md5(CommonUtil.md5(str) + salt);
	}
	
	/**
	 * 隐藏电话号码
	 * @param target
	 * @return
	 */
	public static String hideMobile(String mobile) {
		return new StringBuffer(mobile.substring(0,3)).append("****").append(mobile.substring(7,11)).toString();
	}
	
	/**
	 * 隐藏邮箱
	 * @param target
	 * @return
	 */
	public static String hideEmail(String email) {
		return new StringBuffer(email.substring(0,2)).append("****").append(email.substring(email.lastIndexOf("@")-2,email.length())).toString();
	}
	
	/**
	 * 生成短信验证码
	 * @return
	 */
	public static String gen6ValidateCode() {
		return String.valueOf((int)((Math.random()*9+1)*100000));
	}
	
	// 测试
/*	public static void main(String[] args) throws Exception {
//		String salt = randomSalt();
		String salt = "Q_ET7IK";
		String pwd = "123456";
		System.out.println("盐："+salt+";长度"+salt.length());
		System.out.println(md5Pwd(pwd,salt));
		//System.out.println(getMonthOfDate(2));
	}*/
	
	
	 /**
	  * 转换为UTC格式
	  * 
	  * @param datetime
	  * @return
	  */
    	 public static String conversionUTC(String datetime) {
	     DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date l_datetime = null;
	     String l_utc_date = null;
	     try {
	         l_datetime = format.parse(datetime);
	         DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	         TimeZone l_timezone = TimeZone.getTimeZone("GMT-0");
	         formatter.setTimeZone(l_timezone);
	         l_utc_date = formatter.format(l_datetime);
	         System.out.println(l_utc_date);
	     } catch (ParseException e) {
	         e.printStackTrace();
	     }
	     return l_utc_date;
    	 }
    	 
    	 
    	 /**
    	     * Java将Unix时间戳转换成指定格式日期字符串
    	     * @param timestampString 时间戳 如："1473048265";
    	     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
    	     *
    	     * @return 返回结果 如："2016-09-05 16:06:42";
    	     */
    	    public static String timeStamp2Date(String timestampString, String formats) {
    	        if (TextUtils.isEmpty(formats))
    	            formats = "yyyy-MM-dd HH:mm:ss";
    	        Long timestamp = Long.parseLong(timestampString) * 1000;
    	        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
    	        return date;
    	    }
    	 
    	 
    	 
    	/**
    	 * 获取两个时间差分钟 
    	 * @param date1 
    	 * @param date2
    	 * @return
    	 */
    	public static int getMinute(Date date1,Date date2){
            Calendar dateOne=Calendar.getInstance(),dateTwo=Calendar.getInstance();
            dateOne.setTime(date1);   
            dateTwo.setTime(date2);                
            long timeOne=dateOne.getTimeInMillis();
            long timeTwo=dateTwo.getTimeInMillis();
            long minute=(timeOne-timeTwo)/(1000*60);//转化minute
            return (int)minute;
    	}
    	
    	
    	public static void main(String[] args){
            System.out.println(CommonUtil.getMinute(new Date(),new Date()));
        }

}
