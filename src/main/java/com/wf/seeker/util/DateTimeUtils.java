package com.wf.seeker.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateTimeUtils {

	// 定义时间日期显示格式
	private final static String DATE_FORMAT = "yyyy-MM-dd";

	private final static String DATE_FORMAT_CN = "yyyy年MM月dd日";

	private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";

	private final static String MONTH_FORMAT = "yyyy-MM";

	private final static String DAY_FORMAT = "yyyyMMdd";

	private final static String TIM_FORMAT = "HHmmss";

	private final static String FULLDATE_FORMAT = "yyyyMMddHHmmss";

	private final static String FULLDATE_FORMAT2 = "yyyyMMddHHmmssSSS";

	/**
	 * 取得当前系统时间，返回java.lang.String类型
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static String getCurFullDate() {
		return getFormatDate(new Date(), FULLDATE_FORMAT);
	}

	/**
	 * 取得当前系统时间，返回java.lang.String类型
	 * 
	 * @return yyyyMMddHHmmssSSS 精确到毫秒
	 */
	public static String getCurFullDate2() {
		return getFormatDate(new Date(), FULLDATE_FORMAT2);
	}

	/************ liubing begin **************/
	/**
	 * 取得当前系统时间，返回java.util.Date类型
	 * 
	 * @see java.util.Date
	 * @return java.util.Date 返回服务器当前系统时间
	 */
	public static java.util.Date getCurrDate() {
		return new java.util.Date();
	}

	/**
	 * 取得当前系统时间戳
	 * 
	 * @see java.sql.Timestamp
	 * @return java.sql.Timestamp 系统时间戳
	 */
	public static java.sql.Timestamp getCurrTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
	 * 
	 * @param currDate 要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
	 */
	public static String getFormatDate(java.util.Date currDate) {
		return getFormatDate(currDate, DATE_FORMAT);
	}

	/**
	 * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
	 * 
	 * @param currDate 要格式化的日期
	 * @see #getFormatDate(java.util.Date)
	 * @return Date 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
	 */
	public static Date getFormatDateToDate(java.util.Date currDate) {
		return getFormatDate(getFormatDate(currDate));
	}

	/**
	 * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
	 * 
	 * @param currDate 要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static String getFormatDate_CN(java.util.Date currDate) {
		return getFormatDate(currDate, DATE_FORMAT_CN);
	}

	/**
	 * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
	 * 
	 * @param currDate 要格式化的日期
	 * @see #getFormatDate_CN(String)
	 * @return Date 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static Date getFormatDateToDate_CN(java.util.Date currDate) {
		return getFormatDate_CN(getFormatDate_CN(currDate));
	}

	/**
	 * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
	 * 
	 * @param currDate 要格式化的日期
	 * @see #getFormatDate(String, String)
	 * @return Date 返回格式化后的日期，默认格式为yyyy-MM-dd，如2006-02-15
	 */
	public static Date getFormatDate(String currDate) {
		return getFormatDate(currDate, DATE_FORMAT);
	}

	/**
	 * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
	 * 
	 * @param currDate 要格式化的日期
	 * @see #getFormatDate(String, String)
	 * @return 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static Date getFormatDate_CN(String currDate) {
		return getFormatDate(currDate, DATE_FORMAT_CN);
	}

	/**
	 * 格式化日期
	 * 
	 * @param currDate
	 * @return
	 */
	public static Date getFormatDate_CN2(String currDate) {
		return getFormatDate(currDate, FULLDATE_FORMAT);
	}

	/**
	 * 根据格式得到格式化后的日期
	 * 
	 * @param currDate 要格式化的日期
	 * @param format 日期格式，如yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String 返回格式化后的日期，格式由参数<code>format</code> 定义，如yyyy-MM-dd，如2006-02-15
	 */
	public static String getFormatDate(java.util.Date currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
			return dtFormatdB.format(currDate);
		}
	}

	/**
	 * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 * 
	 * @param currDate 要格式化的时间
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static String getFormatDateTime(java.util.Date currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT);
	}

	/**
	 * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 * 
	 * @param currDate 要格式环的时间
	 * @see #getFormatDateTime(String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static Date getFormatDateTimeToTime(java.util.Date currDate) {
		return getFormatDateTime(getFormatDateTime(currDate));
	}

	/**
	 * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 * 
	 * @param currDate 要格式化的时间
	 * @see #getFormatDateTime(String, String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static Date getFormatDateTime(String currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT);
	}

	/**
	 * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 * 
	 * @param currDate 要格式化的时间
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static String getFormatDateTime_CN(java.util.Date currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT_CN);
	}

	/**
	 * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 * 
	 * @param currDate 要格式化的时间
	 * @see #getFormatDateTime_CN(String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static Date getFormatDateTimeToTime_CN(java.util.Date currDate) {
		return getFormatDateTime_CN(getFormatDateTime_CN(currDate));
	}

	/**
	 * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 * 
	 * @param currDate 要格式化的时间
	 * @see #getFormatDateTime(String, String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static Date getFormatDateTime_CN(String currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT_CN);
	}

	/**
	 * 根据格式得到格式化后的时间
	 * 
	 * @param currDate 要格式化的时间
	 * @param format 时间格式，如yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormatDateTime(java.util.Date currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
			return dtFormatdB.format(currDate);
		}
	}

	/**
	 * 根据格式得到格式化后的日期
	 * 
	 * @param currDate 要格式化的日期
	 * @param format 日期格式，如yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#parse(java.lang.String)
	 * @return Date 返回格式化后的日期，格式由参数<code>format</code>定义，如yyyy-MM-dd，如2006-02-15
	 */
	public static Date getFormatDate(String currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 根据格式得到格式化后的时间
	 * 
	 * @param currDate 要格式化的时间
	 * @param format 时间格式，如yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#parse(java.lang.String)
	 * @return Date 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd HH:mm:ss
	 */
	public static Date getFormatDateTime(String currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 得到格式化后的当前系统日期，格式为yyyy-MM-dd，如2006-02-15
	 * 
	 * @see #getFormatDate(java.util.Date)
	 * @return String 返回格式化后的当前服务器系统日期，格式为yyyy-MM-dd，如2006-02-15
	 */
	public static String getCurrDateStr() {
		return getFormatDate(getCurrDate());
	}

	/**
	 * 得到格式化后的当前系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 * 
	 * @see #getFormatDateTime(java.util.Date)
	 * @return String 返回格式化后的当前服务器系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static String getCurrDateTimeStr() {
		return getFormatDateTime(getCurrDate());
	}

	/**
	 * 得到格式化后的当前系统日期，格式为yyyy年MM月dd日，如2006年02月15日
	 * 
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回当前服务器系统日期，格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static String getCurrDateStr_CN() {
		return getFormatDate(getCurrDate(), DATE_FORMAT_CN);
	}

	/**
	 * 得到格式化后的当前系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 * 
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的当前服务器系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static String getCurrDateTimeStr_CN() {
		return getFormatDateTime(getCurrDate(), TIME_FORMAT_CN);
	}

	/**
	 * 得到系统当前日期的前或者后几天
	 * 
	 * @param iDate 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回系统当前日期的前或者后几天
	 */
	public static Date getDateBeforeOrAfter(int iDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, iDate);
		return cal.getTime();
	}

	/**
	 * 得到日期的前或者后几天
	 * 
	 * @param iDate 如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回参数<code>curDate</code>定义日期的前或者后几天
	 */
	public static Date getDateBeforeOrAfter(Date curDate, int iDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.DAY_OF_MONTH, iDate);
		return cal.getTime();
	}

	/**
	 * 得到格式化后的月份，格式为yyyy-MM，如2006-02
	 * 
	 * @param currDate 要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的月份，格式为yyyy-MM，如2006-02
	 */
	public static String getFormatMonth(java.util.Date currDate) {
		return getFormatDate(currDate, MONTH_FORMAT);
	}

	/**
	 * 得到格式化后的日，格式为yyyyMMdd，如20060210
	 * 
	 * @param currDate 要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的日，格式为yyyyMMdd，如20060210
	 */
	public static String getFormatDay(java.util.Date currDate) {
		return getFormatDate(currDate, DAY_FORMAT);
	}

	/**
	 * 得到hhmmss
	 * 
	 * @param currDate
	 * @return
	 */
	public static String getFormatTime(Date currDate) {

		return getFormatDate(currDate, TIM_FORMAT);
	}

	/**
	 * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 * 
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 */
	public static String getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 * 
	 * @param currDate 要格式化的日期
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 */
	public static String getFirstDayOfMonth(Date currDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到日期的前或者后几分钟
	 * 
	 * @param iHour 如果要获得前几分钟日期，该参数为负数； 如果要获得后几分钟日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回参数<code>curDate</code>定义日期的前或者后几分钟
	 */
	public static Date getDateBeforeOrAfterMinutes(Date curDate, int iMinute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.MINUTE, iMinute);
		return cal.getTime();
	}

	/**
	 * 得到日期的前或者后几小时
	 * 
	 * @param iHour 如果要获得前几小时日期，该参数为负数； 如果要获得后几小时日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回参数<code>curDate</code>定义日期的前或者后几小时
	 */
	public static Date getDateBeforeOrAfterHours(Date curDate, int iHour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.HOUR_OF_DAY, iHour);
		return cal.getTime();
	}

	/**
	 * 得到日期的前或者后几个月
	 * 
	 * @param iMonth 如果要获得前几个月日期，该参数为负数； 如果要获得后几个月日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回参数<code>curDate</code>定义日期的前或者后几个月
	 */
	public static Date getDateBeforeOrAfterMonths(Date curDate, int iMonth) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.MONTH, iMonth);
		return cal.getTime();
	}

	/**
	 * 获取日期的凌晨Date对象
	 * 
	 * @Desc:
	 * @author: liubing
	 * @param currDate
	 * @return Date
	 */
	public static Date getWeeHourDateTime(java.util.Date currDate) {

		Calendar c = Calendar.getInstance();
		c.setTime(currDate);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取明天的凌晨的时间
	 * 
	 * @Desc:
	 * @author: liubing
	 * @param currDate
	 * @return Date
	 */
	public static Date getDateOfNextDay(java.util.Date currDate) {

		return getWeeHourDateTime(getDateBeforeOrAfter(currDate, 1));
	}

	/**
	 * 得到传入日期与当前系统日期相差几天 (纯日期比较)
	 * 
	 * @Desc: 比如 2月5日 和2月6日比较 不关心几点 就是返回相差1天
	 * 
	 * @author: liubing
	 * @param currDate 需要比较的日期时间
	 * @return long 返回参数 如果比较日期比当前日期小，则返回负数 如果比较日期比当前日期大，则返回正数
	 */
	public static int getIntCompareToCurrDateDay(java.util.Date currDate) {

		currDate = getWeeHourDateTime(currDate);
		Date nowDate = getWeeHourDateTime(getCurrDate());

		long currDayCount = currDate.getTime() / (1000 * 60 * 60 * 24);
		long nowDateCount = nowDate.getTime() / (1000 * 60 * 60 * 24);

		return (int) (currDayCount - nowDateCount);
	}

	/**
	 * 得到传入日期与当前日期相差几天 （24小时为一天，不足24小时为0天）
	 * 
	 * @Desc:
	 * @author: liubing
	 * @param currDate 需要比较的日期时间
	 * @return long 返回参数 如果比较日期比当前日期小，则返回负数 如果比较日期比当前日期大，则返回正数
	 */
	public static int getIntCompareToCurrDateHour(java.util.Date currDate) {

		return (int) ((currDate.getTime() - getCurrDate().getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 根据时间戳返回日期对象
	 * 
	 * @Desc:
	 * @author: liubing
	 * @return Date
	 */
	public static Date getDateFromTimeMillis(java.lang.Long currentTimeMillis) {

		System.out.println("currentTimeMillis=" + currentTimeMillis);
		Calendar cal = Calendar.getInstance();
		System.out.println("cal=" + cal);
		cal.setTimeInMillis(currentTimeMillis);
		System.out.println("setTimeInMillis=");

		return cal.getTime();
	}

	/************ liubing end **************/

	/**
	 * Get the previous time, from how many days to now.
	 * 
	 * @param days How many days.
	 * @return The new previous time.
	 */
	public static Date previous(int days) {
		return new Date(System.currentTimeMillis() - days * 3600000L * 24L);
	}

	public static String formatDateTime(Date d) {
		return new SimpleDateFormat(TIME_FORMAT).format(d);
	}

	public static String formatDateTime(long d) {
		return new SimpleDateFormat(TIME_FORMAT).format(d);
	}

	public static Date parseDate(String d) {
		try {
			return new SimpleDateFormat(DATE_FORMAT).parse(d);
		} catch (Exception e) {
		}
		return null;
	}

	public static Date parseDateTime(String dt) {
		try {
			return new SimpleDateFormat(TIME_FORMAT).parse(dt);
		} catch (Exception e) {
		}
		return null;
	}

	/** 日期 */
	public final static String DEFAILT_DATE_PATTERN = "yyyy-MM-dd";
	/** 日期时间 */
	public final static String DEFAILT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/** 时间 */
	public final static String DEFAULT_TIME_PATTERN = "HH:mm:ss";
	/**
	 * 每天的毫秒数
	 */
	public final static long MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

	/**
	 * 转换日期字符串得到指定格式的日期类型
	 * 
	 * @param formatString 需要转换的格式字符串
	 * @param targetDate 需要转换的时间
	 * @return
	 * @throws ParseException
	 */
	public static final Date convertString2Date(String formatString, String targetDate) throws ParseException {
		if (StringUtils.isBlank(targetDate))
			return null;
		SimpleDateFormat format = null;
		Date result = null;
		format = new SimpleDateFormat(formatString);
		try {
			result = format.parse(targetDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return result;
	}

	public static final Date convertString2Date(String[] formatString, String targetDate) throws ParseException {
		if (StringUtils.isBlank(targetDate)) {
			return null;
		}
		SimpleDateFormat format = null;
		Date result = null;
		String errorMessage = null;
		Integer errorOffset = null;
		for (String dateFormat : formatString) {
			try {
				format = new SimpleDateFormat(dateFormat);
				result = format.parse(targetDate);
			} catch (ParseException pe) {
				result = null;
				errorMessage = pe.getMessage();
				errorOffset = pe.getErrorOffset();
			} finally {
				if (result != null && result.getTime() > 1) {
					break;
				}
			}
		}
		if (result == null) {
			throw new ParseException(errorMessage, errorOffset);
		}
		return result;
	}

	/**
	 * 转换字符串得到默认格式的日期类型
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date convertString2Date(String strDate) throws ParseException {
		Date result = null;
		try {
			result = convertString2Date(DEFAILT_DATE_PATTERN, strDate);
		} catch (ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}
		return result;
	}

	/**
	 * 转换日期得到指定格式的日期字符串
	 * 
	 * @param formatString 需要把目标日期格式化什么样子的格式。例如,yyyy-MM-dd HH:mm:ss
	 * @param targetDate 目标日期
	 * @return
	 */
	public static String convertDate2String(String formatString, Date targetDate) {
		SimpleDateFormat format = null;
		String result = null;
		if (targetDate != null) {
			format = new SimpleDateFormat(formatString);
			result = format.format(targetDate);
		} else {
			return null;
		}
		return result;
	}

	/**
	 * 转换日期,得到默认日期格式字符串
	 * 
	 * @param targetDate
	 * @return
	 */
	public static String convertDate2String(Date targetDate) {
		return convertDate2String(DEFAILT_DATE_PATTERN, targetDate);
	}

	/**
	 * 比较日期大小
	 * 
	 * @param src
	 * @param src
	 * @return int; 1:DATE1>DATE2;
	 */
	public static int compare_date(Date src, Date src1) {

		String date1 = convertDate2String(DEFAILT_DATE_TIME_PATTERN, src);
		String date2 = convertDate2String(DEFAILT_DATE_TIME_PATTERN, src1);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 日期比较
	 * 
	 * 判断时间date1是否在时间date2之前 <br/>
	 * 时间格式 2005-4-21 16:16:34 <br/>
	 * 添加人：胡建国
	 * 
	 * @param targetDate
	 * @return
	 */
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 日期比较
	 * 
	 * 判断当前时间是否在时间date2之前 <br/>
	 * 时间格式 2005-4-21 16:16:34 <br/>
	 * 添加人：胡建国
	 * 
	 * @param targetDate
	 * @return
	 */
	public static boolean isDateBefore(String date2) {
		if (date2 == null) {
			return false;
		}
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 比较当前时间与时间date2的天相等 时间格式 2008-11-25 16:30:10 如:当前时间是2008-11-25 16:30:10与传入时间2008-11-25 15:31:20 相比较,返回true即相等
	 * 
	 * @param date1
	 * @param date2
	 * @return boolean; true:相等
	 * @author zhangjl
	 */
	public static boolean equalDate(String date2) {
		try {
			String date1 = convertDate2String(DEFAILT_DATE_TIME_PATTERN, new Date());
			date1.equals(date2);
			Date d1 = convertString2Date(DEFAILT_DATE_PATTERN, date1);
			Date d2 = convertString2Date(DEFAILT_DATE_PATTERN, date2);
			return d1.equals(d2);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * 比较时间date1与时间date2的天相等 时间格式 2008-11-25 16:30:10
	 * 
	 * @param date1
	 * @param date2
	 * @return boolean; true:相等
	 * @author zhangjl
	 */
	public static boolean equalDate(String date1, String date2) {
		try {

			Date d1 = convertString2Date(DEFAILT_DATE_PATTERN, date1);
			Date d2 = convertString2Date(DEFAILT_DATE_PATTERN, date2);

			return d1.equals(d2);
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 比较时间date1是否在时间date2之前 时间格式 2008-11-25 16:30:10
	 * 
	 * @param date1
	 * @param date2
	 * @return boolean; true:在date2之前
	 * @author 胡建国
	 */
	public static boolean beforeDate(String date1, String date2) {
		try {
			Date d1 = convertString2Date(DEFAILT_DATE_PATTERN, date1);
			Date d2 = convertString2Date(DEFAILT_DATE_PATTERN, date2);
			return d1.before(d2);
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 获取上个月开始时间
	 * 
	 * @param currentDate 当前时间
	 * @return 上个月的第一天
	 */
	public static Date getBoferBeginDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), (currentDate.get(Calendar.MONTH)) - 1,
				result.getActualMinimum(Calendar.DATE), 0, 0, 0);
		return result.getTime();
	}

	/**
	 * 获取指定月份的第一天
	 * 
	 * @param currentDate
	 * @return
	 */
	public static Date getBeginDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), (currentDate.get(Calendar.MONTH)),
				result.getActualMinimum(Calendar.DATE));
		return result.getTime();
	}

	/**
	 * 获取上个月结束时间
	 * 
	 * @param currentDate 当前时间
	 * @return 上个月最后一天
	 */
	public static Date getBoferEndDate(Calendar currentDate) {
		Calendar result = currentDate;
		// result.set(currentDate.get(Calendar.YEAR), currentDate
		// .get(Calendar.MONTH) - 1);
		result.set(Calendar.DATE, 1);
		result.add(Calendar.DATE, -1);
		return result.getTime();
	}

	/**
	 * 获取两个时间的时间间隔
	 * 
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static int getDaysBetween(Calendar beginDate, Calendar endDate) {
		if (beginDate.after(endDate)) {
			Calendar swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int days = endDate.get(Calendar.DAY_OF_YEAR) - beginDate.get(Calendar.DAY_OF_YEAR) + 1;
		int year = endDate.get(Calendar.YEAR);
		if (beginDate.get(Calendar.YEAR) != year) {
			beginDate = (Calendar) beginDate.clone();
			do {
				days += beginDate.getActualMaximum(Calendar.DAY_OF_YEAR);
				beginDate.add(Calendar.YEAR, 1);
			} while (beginDate.get(Calendar.YEAR) != year);
		}
		return days;
	}

	/**
	 * 获取两个时间的时间间隔(月份)
	 * 
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getMonthsBetween(Date beginDate, Date endDate) {
		if (beginDate.after(endDate)) {
			Date swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int months = endDate.getMonth() - beginDate.getMonth();
		int years = endDate.getYear() - beginDate.getYear();

		months += years * 12;

		return months;
	}

	/**
	 * 获取两个时间内的工作日
	 * 
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static int getWorkingDay(Calendar beginDate, Calendar endDate) {
		int result = -1;
		if (beginDate.after(endDate)) {
			Calendar swap = beginDate;
			beginDate = endDate;
			endDate = swap;
		}
		int charge_start_date = 0;
		int charge_end_date = 0;
		int stmp;
		int etmp;
		stmp = 7 - beginDate.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - endDate.get(Calendar.DAY_OF_WEEK);
		if (stmp != 0 && stmp != 6) {
			charge_start_date = stmp - 1;
		}
		if (etmp != 0 && etmp != 6) {
			charge_end_date = etmp - 1;
		}
		result = (getDaysBetween(getNextMonday(beginDate), getNextMonday(endDate)) / 7) * 5 + charge_start_date
				- charge_end_date;
		return result;
	}

	/**
	 * 根据当前给定的日期获取当前天是星期几(中国版的)
	 * 
	 * @param date 任意时间
	 * @return
	 */
	public static String getChineseWeek(Calendar date) {
		final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		return dayNames[dayOfWeek - 1];

	}

	/**
	 * 获得日期的下一个星期一的日期
	 * 
	 * @param date 任意时间
	 * @return
	 */
	public static Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	}

	/**
	 * 获取两个日期之间的休息时间
	 * 
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static int getHolidays(Calendar beginDate, Calendar endDate) {
		return getDaysBetween(beginDate, endDate) - getWorkingDay(beginDate, endDate);

	}

	public static boolean isDateEnable(Date beginDate, Date endDate, Date currentDate) {
		// 开始日期
		long beginDateLong = beginDate.getTime();
		// 结束日期
		long endDateLong = endDate.getTime();
		// 当前日期
		long currentDateLong = currentDate.getTime();
		if (currentDateLong >= beginDateLong && currentDateLong <= endDateLong) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 获取当前月份的第一天
	 * 
	 * @param currtenDate 当前时间
	 * @return
	 */
	public static Date getMinDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
				currentDate.getActualMinimum(Calendar.DATE));
		return result.getTime();
	}

	public static Calendar getDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date);
		return calendar;
	}

	public static Calendar getDate(int year, int month) {
		return getDate(year, month, 0);
	}

	public static Date getCountMinDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, calendar.getActualMinimum(Calendar.DATE));
		return calendar.getTime();
	}

	public static Date getCountMaxDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 0);
		return calendar2.getTime();
	}

	/**
	 * 获取当前月份的第一天
	 * 
	 * @param currtenDate 当前时间
	 * @return
	 */
	public static Date getMinDate() {
		Calendar currentDate = Calendar.getInstance();
		return getMinDate(currentDate);
	}

	/**
	 * 获取当前月分的最大天数
	 * 
	 * @param currentDate 当前时间
	 * @return
	 */
	public static Date getMaxDate(Calendar currentDate) {
		Calendar result = Calendar.getInstance();
		result.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),
				currentDate.getActualMaximum(Calendar.DATE));
		return result.getTime();
	}

	/**
	 * 获取当前月分的最大天数
	 * 
	 * @param currentDate 当前时间
	 * @return
	 */
	public static Date getMaxDate() {
		Calendar currentDate = Calendar.getInstance();
		return getMaxDate(currentDate);
	}

	/**
	 * 获取今天最大的时间
	 * 
	 * @return
	 */
	public static String getMaxDateTimeForToDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		return convertDate2String(DEFAILT_DATE_TIME_PATTERN, calendar.getTime());
	}

	/**
	 * 获取日期最大的时间
	 * 
	 * @return
	 */
	public static Date getMaxDateTimeForToDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		return calendar.getTime();
	}

	/**
	 * 获取今天最小时间
	 * 
	 * @return
	 */
	public static String getMinDateTimeForToDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		return convertDate2String(DEFAILT_DATE_TIME_PATTERN, calendar.getTime());
	}

	/**
	 * 获取 date 最小时间
	 * 
	 * @return
	 */
	public static Date getMinDateTimeForToDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
		return calendar.getTime();
	}

	/**
	 * 获取发生日期的结束时间 根据用户设置的日期天数来判定这这个日期是什么(例如 (getHappenMinDate = 2008-10-1) 的话 那么 (getHappenMaxDate = 2008-11-1) 号)
	 * 
	 * @return
	 */
	public static Date getHappenMaxDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 加减天数
	 * 
	 * @param num
	 * @param Date
	 * @return
	 */
	public static Date addDay(int num, Date Date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Date);
		calendar.add(Calendar.DATE, num);// 把日期往后增加 num 天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期往后推一天的结果
	}

	/**
	 * 加减年,返回yyyy-MM-dd java.util.Date
	 * 
	 * @param num
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date addYear(int num, Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, num);
		return formatDate(calendar.getTime());
	}

	/**
	 * 计算两端时间的小时差
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getHour(Date begin, Date end) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(begin);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(end);
		Long millisecond = c2.getTimeInMillis() - c1.getTimeInMillis();
		Long hour = millisecond / 1000 / 60 / 60;
		Long minute = (millisecond / 1000 / 60) % 60;
		if (minute >= 30) {
			hour++;
		}

		return hour.intValue();
	}

	/**
	 * 格式化日期
	 */
	public static String dateFormat(Date date) {
		if (date == null) {
			return null;
		}
		return DateFormat.getDateInstance().format(date);
	}

	/**
	 * @see 取得指定时间的给定格式()
	 * @return String
	 * @throws ParseException
	 */
	public static String setDateFormat(Date myDate, String strFormat) throws ParseException {
		if (myDate == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String sDate = sdf.format(myDate);
		return sDate;
	}

	public static String setDateFormat(String myDate, String strFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
		String sDate = sdf.format(myDate);

		return sDate;
	}

	/*****************************************
	 * @功能 计算某年某月的结束日期
	 * @return interger
	 * @throws ParseException
	 ****************************************/
	public static String getYearMonthEndDay(int yearNum, int monthNum) throws ParseException {

		// 分别取得当前日期的年、月、日
		String tempYear = Integer.toString(yearNum);
		String tempMonth = Integer.toString(monthNum);
		String tempDay = "31";
		if (tempMonth.equals("1") || tempMonth.equals("3") || tempMonth.equals("5") || tempMonth.equals("7")
				|| tempMonth.equals("8") || tempMonth.equals("10") || tempMonth.equals("12")) {
			tempDay = "31";
		}
		if (tempMonth.equals("4") || tempMonth.equals("6") || tempMonth.equals("9") || tempMonth.equals("11")) {
			tempDay = "30";
		}
		if (tempMonth.equals("2")) {
			if (isLeapYear(yearNum)) {
				tempDay = "29";
			} else {
				tempDay = "28";
			}
		}
		String tempDate = tempYear + "-" + tempMonth + "-" + tempDay;
		return tempDate;// setDateFormat(tempDate,"yyyy-MM-dd");
	}

	/*****************************************
	 * @功能 判断某年是否为闰年
	 * @return boolean
	 * @throws ParseException
	 ****************************************/
	public static boolean isLeapYear(int yearNum) {
		boolean isLeep = false;
		/** 判断是否为闰年，赋值给一标识符flag */
		if ((yearNum % 4 == 0) && (yearNum % 100 != 0)) {
			isLeep = true;
		} else if (yearNum % 400 == 0) {
			isLeep = true;
		} else {
			isLeep = false;
		}
		return isLeep;
	}

	/**
	 * 格式化日期
	 * 
	 * @throws ParseException
	 * 
	 * 例: DateUtils.formatDate("yyyy-MM-dd HH",new Date()) "yyyy-MM-dd HH:00:00"
	 */
	public static Date formatDate(String formatString, Date date) throws ParseException {
		if (date == null) {
			date = new Date();
		}
		if (StringUtils.isBlank(formatString))
			formatString = DateTimeUtils.DEFAILT_DATE_PATTERN;

		date = DateTimeUtils.convertString2Date(formatString, DateTimeUtils.convertDate2String(formatString, date));

		return date;
	}

	/**
	 * 格式化日期 yyyy-MM-dd
	 * 
	 * @throws ParseException 例： DateUtils.formatDate(new Date()) "yyyy-MM-dd 00:00:00"
	 */
	public static Date formatDate(Date date) throws ParseException {
		date = formatDate(DateTimeUtils.DEFAILT_DATE_PATTERN, date);
		return date;
	}

	/**
	 * @throws ParseException 根据日期获得 星期一的日期
	 * 
	 */
	public static Date getMonDay(Date date) throws ParseException {

		Calendar cal = Calendar.getInstance();
		if (date == null)
			date = new Date();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			cal.add(Calendar.WEEK_OF_YEAR, -1);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		date = formatDate(cal.getTime());

		return date;
	}

	/**
	 * @throws ParseException 根据日期获得 星期日 的日期
	 * 
	 */
	public static Date getSunDay(Date date) throws ParseException {

		Calendar cal = Calendar.getInstance();
		if (date == null)
			date = new Date();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
			cal.add(Calendar.WEEK_OF_YEAR, 1);

		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

		date = formatDate(cal.getTime());
		return date;
	}

	/**
	 * 获得 下个月的第一天
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getNextDay(Date date) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		return formatDate(cal.getTime());
	}

	/**
	 * 获取二个日期相差几天
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getOverdueDays(Date startTime, Date endTime) {

		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(startTime);
		endCal.setTime(endTime);
		int overDays = DateTimeUtils.getDaysBetween(startCal, endCal);
		overDays = Math.abs(overDays);
		return overDays;
	}

	/**
	 * 时间戳日期转时间 eg:46513465456 ===> Date
	 * 
	 * @param str
	 * @return Date
	 */
	public static Date String2Date(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = new Long(Integer.parseInt(str));
		String d = format.format(time);
		Date date = null;
		try {
			date = format.parse(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;

	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearLast() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	/**
	 * 比较两个时间是否为同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

		return isSameDate;
	}

}
