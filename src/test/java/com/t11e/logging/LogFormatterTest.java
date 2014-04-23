package com.t11e.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogFormatterTest {
	@Test
	public void testFormatting() {
		final LogFormatter formatter = new LogFormatter();
		final LogRecord record = new LogRecord(Level.FINE, "Test log message");
		record.setThreadID(0xdeadbeef);
		record.setMillis(0);
		record.setLoggerName("MyLogger");
		assertEquals(
				"[19691231 19:00:00,000] [deadbeef] [FINE   ] [MyLogger] Test log message\n",
				formatter.format(record));

		record.setLevel(Level.FINEST);
		record.setThreadID(0xabc);
		assertEquals(
				"[19691231 19:00:00,000] [00000abc] [FINEST ] [MyLogger] Test log message\n",
				formatter.format(record));
	}
}
