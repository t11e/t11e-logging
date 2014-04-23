package com.t11e.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

public class Log4jLayout extends Layout {
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss,SSS");

	@Override
	public void activateOptions() {
	}

	@Override
	public boolean ignoresThrowable() {
		return false;
	}

	@Override
	public String format(LoggingEvent event) {
		final StringBuilder builder = new StringBuilder();
		synchronized (dateFormat) {
			final Date date = new Date(event.getTimeStamp());
			builder.append('[');
			builder.append(dateFormat.format(date));
			builder.append("] ");
		}
		builder.append("[");
		{
			final String threadId = Long.toHexString(Thread.currentThread().getId());
			leftPad(builder, threadId, 8, '0');
		}
		builder.append("] ");
		builder.append("[");
		rightPad(builder, event.getLevel().toString(), 5, ' ');
		builder.append("] ");
		final String loggerName = event.getLoggerName();
		builder.append("[");
		builder.append(loggerName);
		builder.append("] ");
		{
			final String message = event.getRenderedMessage();
			final int prefixLength = builder.length();
			int start = 0;
			final int length = message.length();
			while (start < length) {
				int eol = message.indexOf('\n', start);
				if (eol == -1) {
					eol = message.length();
				}
				if (start > 0) {
					builder.append(builder.substring(0, prefixLength));
				}
				builder.append(message.substring(start, eol));
				builder.append('\n');
				start = eol + 1;
			}
		}
		final ThrowableInformation ti = event.getThrowableInformation();
		if (ti != null) {
			Throwable t = ti.getThrowable();
			builder.append(t.getClass().getName());
			builder.append(": ");
			builder.append(getStackTrace(t));
			builder.append('\n');
		}
		return builder.toString();
	}

	private static String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		pw.close();
		return sw.toString();
	}

	private static void leftPad(final StringBuilder builder, final String str,
			final int size, final char padChar) {
		pad(builder, size - str.length(), padChar);
		builder.append(str);
	}

	private static void rightPad(StringBuilder builder, String str, int size,
			char padChar) {
		builder.append(str);
		pad(builder, size - str.length(), padChar);
	}

	private static void pad(StringBuilder builder, int count, char padChar) {
		for (int i = count; i > 0; i--) {
			builder.append(padChar);
		}
	}
}
