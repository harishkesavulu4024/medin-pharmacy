package com.medin.pharmacy.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.Weeks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.medin.pharmacy.enums.Day;

public class DateUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
	private static final String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss a";
	private static final String DATE_FORMAT_FOR_NOTES_TIMESTAMP = "dd MMMMM yyyy";
	private static final String TIME_FORMAT_FOR_NOTES_TIMESTAMP = "HH:mm";
	private static final String DATE_FORMAT_WITH_FULL_MONTH = "dd'th' MMMMM yyyy";
	private static final String DATE_FORMAT_WITH_OUT_TIMESTAMP = "dd/MM/yyyy";
	private static final String LONG_TIME_STAMP_FORMAT = "MMM dd,yyyy hh:mm:ss a";
	

	public static String getFormattedDateInString() {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT);
		final Date today = getTodaysDate();
		return dateFormate.format(today);
	}

	public static final Date getTodaysDate() {
		return getTodaysDateByFormat(DATE_FORMAT);
	}

	public static final Date getTodaysDateWithOutTimeStamp() {
		return getTodaysDateByFormat(DATE_FORMAT_WITH_OUT_TIMESTAMP);
	}

	public static final String getNowAsString() {
		return getDateAsString(new Date());
	}
	
	public static final String getDateAsString(Date date){
		return DateUtil.getDateForDisplay(date, LONG_TIME_STAMP_FORMAT);
	}

	public static Date getTodaysDateByFormat(String dateFormat) {
		final DateFormat dateFormate = new SimpleDateFormat(dateFormat);
		final Calendar calendar = Calendar.getInstance();
		String todaysDateDiff;
		Date date = new Date();
		todaysDateDiff = null;
		date = todaysDateDiff == null ? calendar.getTime()
				: todaysDateDiff.contains("-")
						? DateUtil.subtractDays(date, Integer.valueOf(todaysDateDiff.substring(1)))
						: DateUtil.addDays(date, Integer.valueOf(todaysDateDiff.substring(1)));
		Date todaysDate = null;
		try {
			todaysDate = dateFormate.parse(dateFormate.format(date));
		} catch (ParseException e) {
			LOGGER.error("Parse Error", e);
		}
		return todaysDate;
	}
	public static final Date getDateFromString(final String strDate) {
		return getDateFromString(strDate, "");
	}
	public static final Date getDate(final String strDate,final String format) {
		return getDateFromString(strDate, "Asia/Calcutta",format);
	}

	public static final Date getDateFromString(final String strDate, final String timeZone) {
		final String[] formats = { "MMM dd,yyyy hh:mm a", "MMMM dd,yyyy hh:mm a", "yyyy-MM-dd HH:mm:ss.SSSSSS",
				"MMM dd,yyyy hh:mm:ss a", "MMM dd,yyyy hh:mm:ss", "dd/mm/yyyy", "dd/mm/yy hh:mm", DATE_FORMAT,
				"yyyy-MM-dd", "dd MMM, yyyy", "dd-MMM-yyyy", "dd-MM-YYYY" };
		for (String parse : formats) {
			Date date = getDateFromString(strDate, timeZone, parse);
			if (date != null) {
				return date;
			}

		}
		return null;
	}
	
	public static final Date getDateFromString(final String strDate, final String timeZone,String format) {
	
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				Date date = sdf.parse(strDate);
				if (date != null) {
					// Time parsing based on time zone selection
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date); // assigns calendar to given date
					if (timeZone != null
							&& !timeZone.equals("Asia/Calcutta")
							&& (calendar.get(Calendar.HOUR_OF_DAY) > 0 || calendar.get(Calendar.MINUTE) > 0 || calendar
									.get(Calendar.SECOND) > 0)) {
						sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
						date = sdf.parse(strDate);
						Calendar newCalendar = Calendar.getInstance();
						newCalendar.setTime(date);
						if (newCalendar.get(Calendar.HOUR_OF_DAY) == 0 && newCalendar.get(Calendar.MINUTE) == 0
								&& newCalendar.get(Calendar.SECOND) == 0) {
							newCalendar.set(Calendar.SECOND, 1);
							date = newCalendar.getTime();
						}
					}
					return date;
				}
			} catch (ParseException e) {
			}
		
		return null;
	}	

	public static final Date formatDate(final Date date) {
		Date formattedDate = null;
		if (date != null) {
			final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT);
			try {
				formattedDate = dateFormate.parse(dateFormate.format(date));
			} catch (ParseException e) {
				LOGGER.error("Parse Error", e);
			}
		}
		return formattedDate;
	}

	public static final String getDateForDisplay(final Date date) {
		return getDateForDisplay(date, DATE_FORMAT_WITH_OUT_TIMESTAMP);
	}

	public static final String getDateForDisplay(final Date date, String format) {
		String formattedDate = "";
		if (date != null) {
			final DateFormat dateFormate = new SimpleDateFormat(format);
			formattedDate = dateFormate.format(convertToTimeZone(date, "Asia/calcutta"));
		}
		return formattedDate;
	}
	
	public static final String getDateForDisplayWithOutTimeZone(final Date date, String format) {
		String formattedDate = "";
		if (date != null) {
			final DateFormat dateFormate = new SimpleDateFormat(format);
			formattedDate = dateFormate.format(date);
		}
		return formattedDate;
	}

	public static final Date substractMonths(final int monthsToSubstract) {
		return substractMonths(null, monthsToSubstract);
	}

	public static final Date substractMonths(Date date, int monthsToSubstract) {
		final Calendar calendar = Calendar.getInstance();
		if (date != null)
			calendar.setTime(date);
		calendar.add(Calendar.MONTH, -monthsToSubstract);
		return calendar.getTime();
	}

	public static final Date addMonthsToUserDate(final Date dateFromUser, final int monthsToAdd) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFromUser);
		calendar.add(Calendar.MONTH, monthsToAdd);
		return calendar.getTime();
	}

	public static final Date getDateWithFirstMonth() {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT);
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		calendar.set(Calendar.DATE, 1);
		Date date = null;
		try {
			date = dateFormate.parse(dateFormate.format(calendar.getTime()));
		} catch (ParseException e) {
			LOGGER.error("Parse Error", e);
		}
		return date;
	}

	public static final Date getDateWithLastMonth() {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT);
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		calendar.set(Calendar.DATE, 1);
		Date date = null;
		try {
			date = dateFormate.parse(dateFormate.format(calendar.getTime()));
		} catch (ParseException e) {
			LOGGER.error("Parse Error", e);
		}
		return date;
	}

	public static final Date getDateForPresentMonthStart() {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT);
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		Date date = null;
		try {
			date = dateFormate.parse(dateFormate.format(calendar.getTime()));
		} catch (ParseException e) {
			LOGGER.error("Parse Error", e);
		}
		return date;
	}

	public static void main(String[] args) {
		System.out.println(getCurrentTimeStamp());
		System.out.println(getDateWithEndDate(getTodaysDate(), 1));
		System.out.println(getDateWithInitialDate(getTodaysDate(), 1));
		System.out.println(getDateWithNoTimeStamp(subtractDays(getTodaysDate(), 5)));
	}

	public static final String getNotesWithTimeStamp(String notes, final String loggedInPersonName) {
		if (notes != null) {
			notes = notes + "\n-----\n" + "Edited By " + loggedInPersonName + " on " + getCurrentTimeStamp() + "\n";
		} else {
			notes = "";
		}
		return notes;
	}

	public static final String getCurrentTimeStamp() {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT_FOR_NOTES_TIMESTAMP);
		final DateFormat timeFormate = new SimpleDateFormat(TIME_FORMAT_FOR_NOTES_TIMESTAMP);
		final Calendar calendar = Calendar.getInstance();
		final Date date = calendar.getTime();
		final String strDate = dateFormate.format(date);
		final String strTime = timeFormate.format(date);
		final String timeStamp = strDate + " at " + strTime;
		return timeStamp;
	}

	public static final Date getDateWithInitialDate(final Date dateFromUser, final int monthsToSubstract) {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFromUser);
		calendar.add(Calendar.MONTH, -monthsToSubstract);
		calendar.set(Calendar.DATE, 1);
		Date date = null;
		try {
			date = dateFormate.parse(dateFormate.format(calendar.getTime()));
		} catch (ParseException e) {
			LOGGER.error("Parse Error", e);
		}
		return date;
	}

	public static final Date getDateWithEndDate(final Date dateFromUser, final int monthsToSubstract) {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFromUser);
		calendar.add(Calendar.MONTH, -monthsToSubstract);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date date = null;
		try {
			date = dateFormate.parse(dateFormate.format(calendar.getTime()));
		} catch (ParseException e) {
			LOGGER.error("Parse Error", e);
		}
		return date;
	}

	public static final int getMonthFromDate(final Date date) {
		int presentMonth = 0;
		if (date != null) {
			final Calendar todaysCalendar = Calendar.getInstance();
			todaysCalendar.setTime(date);
			presentMonth = todaysCalendar.get(Calendar.MONTH) + 1;
		}
		return presentMonth;
	}

	public static final int getDayFromDate(final Date date) {
		int presentDay = 0;
		if (date != null) {
			final Calendar todaysCalendar = Calendar.getInstance();
			todaysCalendar.setTime(date);
			presentDay = todaysCalendar.get(Calendar.DAY_OF_MONTH);
		}
		return presentDay;
	}

	public static final int getYearFromDate(final Date date) {
		int presentMonth = 0;
		if (date != null) {
			final Calendar todaysCalendar = Calendar.getInstance();
			todaysCalendar.setTime(date);
			presentMonth = todaysCalendar.get(Calendar.YEAR);
		}
		return presentMonth;
	}

	public static final String getDateForDisplayWithFullMonth(final Date date) {
		String formattedDate = "";
		if (date != null) {
			final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT_WITH_FULL_MONTH);
			formattedDate = dateFormate.format(date);
		}
		return formattedDate;
	}

	public static Date getStartDayOfWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return c.getTime();
	}

	public static Date getEndDayOfWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return c.getTime();
	}

	public static Date getDateWithNoTimeStamp(final Date date) {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT_WITH_OUT_TIMESTAMP);
		Date todaysDate = null;
		try {
			todaysDate = dateFormate.parse(dateFormate.format(date));
		} catch (ParseException e) {
			LOGGER.error("Parse Error", e);
		}
		return todaysDate;
	}

	public static final Date subtractDays(final Date date, final int daysToSubtract) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -daysToSubtract);
		return getDateWithNoTimeStamp(calendar.getTime());
	}

	public static final Date addDays(final Date date, final int noDaysToAdd) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, noDaysToAdd);
		return getDateWithNoTimeStamp(calendar.getTime());
	}

	public static final Date getLastSunday(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -1);
		}
		return getDateWithNoTimeStamp(calendar.getTime());
	}

	public static final Date getLastMonday(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -1);
		}
		return getDateWithNoTimeStamp(calendar.getTime());
	}

	public static final Date getStartDateForPreviousMonth() {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT);
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date date = null;
		try {
			date = dateFormate.parse(dateFormate.format(calendar.getTime()));
		} catch (ParseException e) {
			LOGGER.error("Parse Error", e);
		}
		return date;
	}

	public static final Date getEndDateForPreviousMonth() {
		final DateFormat dateFormate = new SimpleDateFormat(DATE_FORMAT);
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date date = null;
		try {
			date = dateFormate.parse(dateFormate.format(calendar.getTime()));
			Date endDate = subtractDays(date, -days + 1);
			return endDate;
		} catch (ParseException e) {
			LOGGER.error("Parse Error", e);
		}
		return date;
	}

	public static final int getWeeksBetween(final LocalDate startDate, final LocalDate endDate) {
		int weeks = 0;
		if (startDate != null && endDate != null) {
			weeks = Weeks.weeksBetween(startDate, endDate).getWeeks();
		}
		return weeks;
	}

	public static final LocalDate getLocalDate(final Date date) {
		LocalDate localDate = new LocalDate();
		if (date != null) {
			localDate = new LocalDate(date);
		}
		return localDate;
	}

	public static final Date subtractHours(final Date date, final int hoursToSubtract) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -hoursToSubtract);
		return calendar.getTime();
	}

	public static final Date addHours(final Date date, final int hours) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}

	public static Date getLastDateOfGivenMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		Date lastDate = c.getTime();
		return lastDate;
	}

	public static Date getStartDateofGivenMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DATE, c.getMinimum(Calendar.DATE));
		Date startDate = c.getTime();
		return startDate;
	}

	public static Date clearTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static int getNumberOfDays(Date startDate, Date endDate) {
		return getNumberOfDays(startDate, endDate, true);
	}

	public static int getNumberOfDays(Date startDate, Date endDate, boolean withTime) {
		Date d1 = null;
		Date d2 = null;
		final DateFormat dateFormate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		long diffDays = 0;
		try {
			d1 = dateFormate.parse(dateFormate.format(startDate));
			d2 = dateFormate.parse(dateFormate.format(endDate));
			long diff = (withTime ? d2 : clearTime(d2)).getTime() - (withTime ? d1 : clearTime(d1)).getTime();
			diffDays = diff / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
		}
		return (int) diffDays;
	}

	public static final int getMinutesBetween(final Date startDate, final Date endDate) {
		int minutes = 0;
		LocalDateTime localStartDate = new LocalDateTime(startDate);
		LocalDateTime localEndDate = new LocalDateTime(endDate);
		if (startDate != null && endDate != null) {
			minutes = Minutes.minutesBetween(localStartDate, localEndDate).getMinutes();
		}
		return minutes;
	}
	public static final int getSecondsBetweenTwoDates(final Date startDate, final Date endDate) {
		int minutes = 0;
		LocalDateTime localStartDate = new LocalDateTime(startDate);
		LocalDateTime localEndDate = new LocalDateTime(endDate);
		if (startDate != null && endDate != null) {
			minutes = Seconds.secondsBetween(localStartDate, localEndDate).getSeconds();
		}
		return minutes;
	}

	public static final String getAgeByPeriod(Date dateOfBirth, Date periodEndDate) {
		LocalDate dobDate = new LocalDate(dateOfBirth);
		LocalDate todaysDate = new LocalDate(periodEndDate);
		Period period = new Period(dobDate, todaysDate);
		String age = period.getYears() + " Years " + period.getMonths() + " Months";
		return age;
	}

	public static Date getStartOfTheDay(Date date) {
		LocalDateTime ldt = new LocalDateTime(date);
		return ldt.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toDate();
	}

	public static Date getEndOfTheDay(Date date) {
		LocalDateTime ldt = new LocalDateTime(date);
		return ldt.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
	}

//	public static Date getStartOfTheDayConsiderTimeZone(Date date) {
//		Date convertedDate = convertToTimeZone(date, ACEThreadLocals.getCenterTimeZone());
//		Date startDate = DateUtil.getStartOfTheDay(convertedDate);
//		Calendar newCalendar = Calendar.getInstance();
//		newCalendar.setTime(startDate);
//		newCalendar.set(Calendar.SECOND, 1);
//		startDate = newCalendar.getTime();
//		String formattedDate = "";
//		final DateFormat dateFormate = new SimpleDateFormat(LONG_TIME_STAMP_FORMAT);
//		formattedDate = dateFormate.format(startDate);
//		return getDateFromString(formattedDate, ACEThreadLocals.getCenterTimeZone());
//	}
//
//	public static Date getEndOfTheDayConsiderTimeZone(Date date) {
//		Date convertedDate = convertToTimeZone(date, ACEThreadLocals.getCenterTimeZone());
//		Date endDate = DateUtil.getEndOfTheDay(convertedDate);
//		String formattedDate = "";
//		final DateFormat dateFormate = new SimpleDateFormat(LONG_TIME_STAMP_FORMAT);
//		formattedDate = dateFormate.format(endDate);
//		return getDateFromString(formattedDate, ACEThreadLocals.getCenterTimeZone());
//	}

	/**
	 * Method to return the split of months between the passed in Start and End dates
	 * @param startDate Date
	 * @param endDate Date
	 * @return List&lt;Interval>
	 */
	public static final List<Interval> getSplitByMonths(Date startDate, Date endDate) {
		List<Interval> intervals = new ArrayList<Interval>();
		LocalDate localStartDate = DateUtil.getLocalDate(startDate);
		LocalDate localEndDate = DateUtil.getLocalDate(endDate);
		Instant CONSTANT = new Instant(0);
		LocalDate intervalEndDate = null;
		while (localEndDate.isAfter(localStartDate) || localEndDate.isEqual(localStartDate)) {
			intervalEndDate = localEndDate.isBefore(localStartDate.dayOfMonth().withMaximumValue()) ? localEndDate
					: localStartDate.dayOfMonth().withMaximumValue();
			intervals.add(new Interval(localStartDate.toDateTime(CONSTANT), intervalEndDate.toDateTime(CONSTANT)));
			localStartDate = intervalEndDate.plusDays(1);
		}
		return intervals;
	}

	public static final List<Map<String, Object>> getMonthNames(Date date1, Date date2) {
		return getMonthNames(date1, date2, false);
	}

	public static final List<Map<String, Object>> getMonthNames(Date date1, Date date2,
			boolean isForInvoice) {
		List<Map<String, Object>> monthNumbersAndNames = new ArrayList<Map<String, Object>>();
		LocalDate startDate = new LocalDate(date1);
		LocalDate endDate = new LocalDate(date2);
		while (startDate.isBefore(endDate)) {
			Map<String, Object> monthNameAndNum = new HashMap<String, Object>();
			// LocalDate date = startDate;
			monthNameAndNum.put("monthNumber", startDate.getMonthOfYear());
			monthNameAndNum.put("monthName", startDate.toString("MMMM-YYYY"));
			monthNameAndNum.put("actualDate", startDate.toDate());
			// Date previousMonthDate=date1.equals(startDate) && isForInvoice?startDate:
			monthNameAndNum.put("previousMonthDate", startDate.minus(Period.months(1)).toDate());
			// For sending immediate next month details in invoice generation
			startDate = isForInvoice ? startDate.plus(Period.months(1)) : startDate;
			monthNameAndNum.put("date", getStartDateofGivenMonth(startDate.toDate()));
			monthNumbersAndNames.add(monthNameAndNum);
		}
		return monthNumbersAndNames;
	}

	public static final int getDifferenceBetweenTwoDatesInMonths(Date startDate, Date endDate) {
		DateTime d1 = new DateTime(endDate);
		DateTime d2 = new DateTime(startDate);
		return Months.monthsBetween(d1, d2).getMonths();
	}

	public static final int getDifferenceBetweenTwoDatesInDays(Date startDate, Date endDate) {
		DateTime d1 = new DateTime(endDate);
		DateTime d2 = new DateTime(startDate);
		return Days.daysBetween(d2, d1).getDays();
	}

	public static final List<String> sortStringFormatDates(List<String> dates, final String dateFormat) {
		Collections.sort(dates, new Comparator<String>() {
			DateFormat f = new SimpleDateFormat(dateFormat);

			@Override
			public int compare(String o1, String o2) {
				try {
					return f.parse(o1).compareTo(f.parse(o2));
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		});
		return dates;
	}

	public static final boolean isGreater(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("Dates passed cannot be null");
		}
		LocalDateTime localDate1 = new LocalDateTime(date1);
		LocalDateTime localDate2 = new LocalDateTime(date2);
		return localDate1.isAfter(localDate2);
	}

	public static final Date convertToTimeZone(Date date, final String timeZone) {
		if (date == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date); // assigns calendar to given date
		if (timeZone == null
				|| timeZone.equals("Asia/Calcutta")
				|| (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0 && calendar
						.get(Calendar.SECOND) == 0)) {
			return date;
		}
		DateTimeZone localeTimeZone = DateTimeZone.forID(timeZone);
		DateTime dt = new DateTime(date);
		DateTime dtus = dt.withZone(localeTimeZone);
		Date localeDate = dtus.toLocalDateTime().toDate();
		return localeDate;
	}

	public static boolean isWithinRange(Date dateValue, Date startDate, Date endDate) {
		if (dateValue == null || startDate == null || endDate == null) {
			throw new IllegalArgumentException("Dates passed cannot be null");
		}
		return !(dateValue.before(startDate) || dateValue.after(endDate));
	}




	public static Date getMaxDateByMonth(String monthName, Date date) {
		Date enddate = null;
		date = date != null ? date : DateUtil.getTodaysDate();
		try {
			enddate = new SimpleDateFormat("MMM", Locale.getDefault()).parse(monthName);
		} catch (Exception e) {
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(enddate);
		// aydate refers academic year end date
		Calendar aydate = Calendar.getInstance();
		aydate.setTime(date);
		cal.set(Calendar.YEAR, aydate.get(Calendar.YEAR));
		int month = cal.get(Calendar.MONTH);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		int endDay = days;
		cal.set(year, month, endDay);
		Date maxDate = cal.getTime();
		if (maxDate.compareTo(date) >= 0) {
			cal.set(year - 1, month, endDay);
			maxDate = cal.getTime();
		}
		// For handling ay startdate and enddate have same months
		if (cal.get(Calendar.MONTH) == aydate.get(Calendar.MONTH)) {
			Calendar todaysDate = Calendar.getInstance();
			cal.setTime(DateUtil.getTodaysDate());
			if (todaysDate.get(Calendar.YEAR) == aydate.get(Calendar.YEAR))
				maxDate = date;
			// else
			// cal.set(year - 1, month, endDay);
			//maxDate = cal.getTime();
		}
		return maxDate;
	}

	public static int compareDates(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			return date1.compareTo(date2);
		} else if (date1 == null) {
			return date2 == null ? 0 : 1;
		}
		return -1;
	}
	public static List<Date> getOnlyMonthNames(Date startDate, Date endDate) {
		List<Date> totalMonths = new ArrayList<Date>();
		DateFormat formater = new SimpleDateFormat("MMM-yyyy");
		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();
		beginCalendar.setTime(startDate);
		finishCalendar.setTime(endDate);
		while (beginCalendar.compareTo(finishCalendar) < 1) {
			// add one month to date per loop
			Date date = beginCalendar.getTime();
			totalMonths.add(date);
			beginCalendar.add(Calendar.MONTH, 1);
		}
		return totalMonths;
	}

	public static List<Date> getLastThirtyDaysFromCurrentDate(Date todaysDate) {
		List<Date> lastThirtyDays = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		for (int i = 30; i >= 1; i--) {
			cal.setTime(todaysDate);
			cal.add(Calendar.DAY_OF_MONTH, -i);
			lastThirtyDays.add(cal.getTime());
		}
		return lastThirtyDays;
	}

	public static boolean isLastDayOfTheMonth() {
		Date date = getTodaysDate();
		return getDayFromDate(date) == getDayFromDate(getLastDateOfGivenMonth(date));
	}
	
//	public static boolean isIST() {
//        return ACEThreadLocals.getCenterTimeZone()==null || ACEThreadLocals.getCenterTimeZone().equals("Asia/Calcutta"); 
//    }
//	
//	public static final String getTimeZoneDifference() {
//		if (DateUtil.isIST())
//			return "0";
//        Calendar newCalendar = Calendar.getInstance();
//        newCalendar.setTime(new Date());
//        newCalendar.set(1970, 0, 1);
//        Date convertedDate = DateUtil.convertToTimeZone(newCalendar.getTime(), ACEThreadLocals.getCenterTimeZone());
//        long diff = newCalendar.getTimeInMillis() - convertedDate.getTime();
//        long diffMinutes = diff / (60 * 1000) % 60;
//        long diffHours = diff / (60 * 60 * 1000) % 24;
//        if(diffMinutes<0)
//            diffMinutes = -(diffMinutes);
//        return diffHours + ":" + diffMinutes;
//    }
	
	public static final String getDay(final Date date) {
		int day = 0;
		if (date != null) {
			final Calendar todaysCalendar = Calendar.getInstance();
			todaysCalendar.setTime(date);
			day = todaysCalendar.get(Calendar.DAY_OF_WEEK)-2;
			// For having proper day mapping
			day = day == -1 ? 6 : day <= 0 ? 0 : day;
		}
		return Day.getDay(day).getday();
	}
	
	public static final int getMinutes(final Date date) {
		int minute = 0;
		if (date != null) {
			final Calendar todaysCalendar = Calendar.getInstance();
			todaysCalendar.setTime(date);
			minute = todaysCalendar.get(Calendar.MINUTE);
		}
		return minute;
	}

	public static final Date addMinutes(final Date date, int minutes) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}
}



