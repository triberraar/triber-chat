package be.tribersoft.triber.chat.common;

import java.util.Date;

public class DateFactory {

	private static Date fixatedDate;

	public static void fixate(Date date) {
		fixatedDate = date;
	}

	public static Date now() {
		return fixatedDate == null ? new Date() : fixatedDate;
	}

	public static void release() {
		fixatedDate = null;
	}

}
