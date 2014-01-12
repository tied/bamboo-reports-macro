package co.escapeideas.atlassian.bamboo.reports.services;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import co.escapeideas.atlassian.bamboo.reports.services.Build;

public class BuildTest {
	
	private Build build;

    @Before
	public void setUp() {
		final Map<String, String> artifacts = new HashMap<String, String>(1);
		artifacts.put("key", "value");
		build = new Build("Status", 23, artifacts);
	}

	@Test
	public void testBuild() {
		setUp();
		assertNotNull(build);
	}
	
	@Test
	public void testGetArtifactMap() {
		final Map<String, String> artifactMap = build.getArtifactMap();
		assertEquals(1, artifactMap.size());
		assertEquals("value", artifactMap.get("key"));
	}

	@Test
	public void testGetStatus() {
		assertEquals("Status", build.getStatus());
	}

	@Test
	public void testGetID() {
		assertEquals(23, build.getID());
	}

	@Test
	public void testGetArtifacts() {
		assertEquals(1, build.getArtifacts().size());
	}

	@Test
	public void testToString() {
		final String string = build.toString();
		assertTrue(string.contains("Status"));
		assertTrue(string.contains("23"));
		assertTrue(string.contains("value"));
	}

}
