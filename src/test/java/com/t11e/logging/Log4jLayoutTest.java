package com.t11e.logging;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Log4jLayoutTest {
	@Test
	public void testFormatting() {
		Logger logger = Logger.getLogger("MyLogger");
		final Log4jLayout layout = new Log4jLayout();
		assertEquals(
				"[19691231 19:00:00,000] [00000001] [INFO ] [MyLogger] Test log message\n",
				layout.format(new LoggingEvent("FQN", logger, 0, Level.INFO, "Test log message", "THREAD", null, null, null, null)));

		assertEquals(
				"[19691231 19:00:00,000] [00000001] [ERROR] [MyLogger] Test log message\n",
				layout.format(new LoggingEvent("FQN", logger, 0, Level.ERROR, "Test log message", "THREAD", null, null, null, null)));
	}
}
