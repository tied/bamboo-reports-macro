package co.escapeideas.atlassian.bamboo.reports.parsers;

import org.junit.Test;

import co.escapeideas.atlassian.bamboo.reports.parsers.JacksonParser;

public class JacksonParserTest {

	@Test
	public void testParseBuilds() {
		new JacksonParser().parseBuilds("json");
	}

}
