package com.yiban.yblaas.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @描述：时间格式化的工具类
 */

public class DateUtil {

	/**
	 * 时间格式 : 年-月-日
	 */
	public static final String FORMAT1 = "yyyy-MM-dd";
	/**
	 * 时间格式 : 年-月-日 时:分      24小时制  HH小写为12小时制
	 */
	public static final String FORMAT2 = "yyyy-MM-dd HH:mm";
	/**
	 * 时间格式 : 年-月-日 时:分:秒   24小时制
	 */
	public static final String FORMAT3 = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 时间格式 : 年月日时分秒   24小时制
	 */
	public static final String FORMAT4 = "yyyyMMddHHmmss";

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getNow(){
		Date date = new Date();
		return date;
	}
	
	/**
	 * 获取当前日期(年月日)
	 * @return
	 */
	public static String getNowDay(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date date = new Date();
		String _time = sdf.format(date);
		return _time;
	}

	/**
	 * 获取当前时间(年-月-日 时:分)
	 * @return
	 */
	public static String getNowDate(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT2);
		Date date = new Date();
		String _time = sdf.format(date);
		return _time;
	}

	/**
	 * 获取当前时间(年-月-日 时:分:秒)
	 * @return
	 */
	public static String getNowTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT3);
		Date date = new Date();
		String _time = sdf.format(date);
		return _time;
	}
	
	/**
	 * 获取当前时间(年月日时分)
	 * @return
	 */
	public static String getNowNumber(){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT4);
		Date date = new Date();
		String _time = sdf.format(date);
		return _time;
	}

	/**
	 * 功能描述:
	 * (时间转字符串)
	 *
	 * @param date 时间
	 * @return : java.lang.String
	 * @author : xiaozhu
	 * @date : 2020/4/25 21:14
	 */
	public static String getTimeString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT3);
		String _time = sdf.format(date);
		return _time;
	}

	/**
	 * 根据给定的日期得到与现在的时间相差的秒数
	 * @param time
	 * @return
	 */
	public static int getDifferSeconds(Date time) {
		long a = new Date().getTime();
		long b = time.getTime();
		int c = (int)((a - b) / 1000);
		return c;
	}

	/**
	 * 根据给定的两个时间相差的秒数
	 * @param timeOne 开始时间
	 * @param timeTwo 结束时间
	 * @return
	 */
	public static Boolean getDifferSecondsTwo(Date timeOne,Date timeTwo) {
		long a = timeTwo.getTime();
		long b = timeOne.getTime();
		int c = (int)((a - b) / 1000);
		if(c>0){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 根据给定的日期得到一天前的日期(年-月-日)
	 * @param time
	 * @return
	 */
	public static String getDayBeginDate(long time){
		Long three = 24 * 60 * 60 * 1000l;
		Long threeDay = time - three;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * 根据给定的日期得到三天前的日期(年-月-日)
	 * @param time
	 * @return
	 */
	public static String getThreeBeginDate(long time){
		Long three = 3 * 24 * 60 * 60 * 1000l;
		Long threeDay = time - three;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * 根据给定的日期得到一个月前的日期(年-月-日)
	 * @param time
	 * @return
	 */
	public static String getMonthBeginDate(long time){
		Long month = 30 * 24 * 60 * 60 * 1000l;
		Long threeDay = time - month;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * 根据给定的时间得到三天前的时间(年-月-日 时:分)
	 * @param time
	 * @return
	 */
	public static String getThreeDayBeginTime(long time){
		Long three = 3 * 24 * 60 * 60 * 1000l;
		Long threeDay = time - three;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT3);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * 根据给定的时间得到十天前的时间(年-月-日 时:分:秒)
	 * @param time
	 * @return
	 */
	public static String getTenBeginTime(long time){
		Long three = 10*24 * 60 * 60 * 1000l;
		Long threeDay = time - three;
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT3);
		Date threeTime = new Date(threeDay);
		String _time = sdf.format(threeTime);
		return _time;
	}

	/**
	 * 根据给定的时间得到10天的时间
	 * @param time
	 * @return
	 */
	public static Date getDateSecond(long time){
		Long month = 10 * 1000l;
		Long threeDay = time - month;
		Date threeTime = new Date(threeDay);
		return threeTime;
	}

	/**
	 * 根据给定的时间得到三天前的时间
	 * @param time
	 * @return
	 */
	public static Date getThreeDayBeginTime(Date time){
		Long three = 3 * 24 * 60 * 60 * 1000l;
		Long threeDay = time.getTime() - three;
		Date threeTime = new Date(threeDay);
		return threeTime;
	}
	/**
	 * 格式化时间，去掉后面的0
	 * @param date
	 * @return
	 */
	public static String formatTimeNew(Date date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 19);
		}
		return checkTime;
	}

	/**
	 * 格式化时间，保留到分钟
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 16);
		}

		return checkTime;
	}

	/**
	 * 格式化时间，得到日期
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 10);
		}
		return checkTime;
	}

	/**
	 * 格式化时间，得到日期
	 * @param date
	 * @return
	 */
	public static String formatDate(String date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 10);
		}
		return checkTime;
	}

	/**
	 * 格式化时间，保留到秒
	 * @param date
	 * @return
	 */
	public static String formatTime(String date){
		if(date == null)
			return null;

		String checkTime = String.valueOf(date);

		if(checkTime != null && !"".equals(checkTime) && checkTime.length()>19){
			checkTime = checkTime.substring(0, 19);
		}
		return checkTime;
	}


	/**
	 * 根据给定的时间得到n天后的时间
	 * @param time
	 * @param lastTime
	 * @return
	 */
	public static String getLastTime(String time,int lastTime){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT1);
		Date date;
		String newTime = time;
		try {
			date = sdf.parse(time);
			Long lastTimeDay = lastTime * 24 * 60 * 60 * 1000l;
			Long lastDay = date.getTime() + lastTimeDay;
			Date newDate = new Date(lastDay);
			newTime = sdf.format(newDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newTime;
	}
	
	/**
	 * 根据String转化为Date
	 * @param time
	 * @return
	 */
	public static Date getTimeDate(String time){
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT3);
		try {
			Date date = sdf.parse(time);
			return date;	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}