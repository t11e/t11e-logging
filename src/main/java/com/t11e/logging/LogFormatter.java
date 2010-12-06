package com.t11e.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter
  extends Formatter
{
  private static final DateFormat s_dateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss,SSS");

  @Override
  public String format(final LogRecord record)
  {
    final StringBuilder builder = new StringBuilder();
    synchronized (s_dateFormat)
    {
      final Date date = new Date(record.getMillis());
      builder.append('[');
      builder.append(s_dateFormat.format(date));
      builder.append("] ");
    }
    builder.append("[");
    {
      final String threadId = Integer.toHexString(record.getThreadID());
      leftPad(builder, threadId, 8, '0');
    }
    builder.append("] ");
    builder.append("[");
    rightPad(builder, record.getLevel().getName(), 7, ' ');
    builder.append("] ");
    final String loggerName = record.getLoggerName();
    builder.append("[");
    builder.append(loggerName);
    builder.append("] ");
    {
      final String message = record.getMessage();
      final int prefixLength = builder.length();
      int start = 0;
      final int length = message.length();
      while (start < length)
      {
        int eol = message.indexOf('\n', start);
        if (eol == -1)
        {
          eol = message.length();
        }
        if (start > 0)
        {
          builder.append(builder.substring(0, prefixLength));
        }
        builder.append(message.substring(start, eol));
        builder.append('\n');
        start = eol + 1;
      }
    }
    final Throwable t = record.getThrown();
    if (t != null)
    {
      builder.append(t.getClass().getName());
      builder.append(": ");
      builder.append(getStackTrace(t));
      builder.append('\n');
    }
    return builder.toString();
  }

  private static String getStackTrace(final Throwable throwable)
  {
    final StringWriter sw = new StringWriter();
    final PrintWriter pw = new PrintWriter(sw);
    throwable.printStackTrace(pw);
    pw.close();
    return sw.toString();
  }

  private static void leftPad(final StringBuilder builder, final String str, final int size, final char padChar)
  {
    pad(builder, size - str.length(), padChar);
    builder.append(str);
  }

  private static void rightPad(StringBuilder builder, String str, int size, char padChar)
  {
    builder.append(str);
    pad(builder, size - str.length(), padChar);
  }

  private static void pad(StringBuilder builder, int count, char padChar)
  {
    for (int i = count; i > 0; i--)
    {
      builder.append(padChar);
    }
  }
}
