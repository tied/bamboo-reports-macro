package co.escapeideas.atlassian.bamboo.reports.parsers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import co.escapeideas.atlassian.bamboo.reports.parsers.GsonParser;
import co.escapeideas.atlassian.bamboo.reports.services.Build;

public class GsonParserTest {
	
	private GsonParser parser;

	@Before
	public void setUp() throws Exception {
		parser = new GsonParser();
	}

	@Test
	public void testParseBuilds() throws IOException {
		final String contents = FileUtils.readFileToString(new File("src/test/resources/example.json"));
		final List<Build> builds = parser.parseBuilds(contents);
		assertEquals(1, builds.size());
		final Build build = builds.get(0);
		assertEquals(11, build.getID());
		assertEquals("Successful", build.getStatus());
		final Map<String, String> artifacts = build.getArtifactMap();
		assertEquals(2, artifacts.size());
		
	}

	@Test
	public void testParseBuildsWithStages() throws IOException {
		final String contents = FileUtils.readFileToString(new File("src/test/resources/example2.json"));
		final List<Build> builds = parser.parseBuilds(contents);
		assertEquals(1, builds.size());
		final Build build = builds.get(0);
		assertEquals(11, build.getID());
		assertEquals("Successful", build.getStatus());
		final Map<String, String> artifacts = build.getArtifactMap();
		assertEquals(2, artifacts.size());
		
	}

}
