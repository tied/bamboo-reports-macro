package co.escapeideas.atlassian.bamboo.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ArtifactLinksConfigurationTest {
	
	private static final String URL = "http://something/somewhere";
	private ArtifactLinksConfiguration config;

	@Before
	public void setUp() throws Exception {
		config = new ArtifactLinksConfiguration(getParameters(URL));
	}

	/**
	 * @param url 
	 * @return
	 */
	public static Map<String, String> getParameters(String url) {
		final Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("url", url);
		return parameters;
	}

	@Test
	public void testHashCode() {
		final ArtifactLinksConfiguration other = new ArtifactLinksConfiguration(getParameters(URL));
		final ArtifactLinksConfiguration different = new ArtifactLinksConfiguration(new HashMap<String, String>());
		assertTrue(different.hashCode() != other.hashCode());
		assertEquals(other.hashCode(), config.hashCode());
	}

	@Test
	public void testArtifactLinksConfiguration() {
		assertNotNull(config);
	}

	@Test
	public void testGetUrl() {
		assertEquals(URL, config.getUrl());
	}

	@Test
	public void testIsValid() {
		assertTrue(config.isValid());
	}

	@Test
	public void testIsNotValid() {
		final ArtifactLinksConfiguration different = new ArtifactLinksConfiguration(new HashMap<String, String>());
		assertFalse(different.isValid());
	}

	@Test
	public void testToString() {
		final String string = config.toString();
		assertTrue(string.contains(URL));
	}

	@Test
	public void testEqualsObject() {
		final ArtifactLinksConfiguration other = new ArtifactLinksConfiguration(getParameters(URL));
		final ArtifactLinksConfiguration different = new ArtifactLinksConfiguration(new HashMap<String, String>());
		assertTrue(other.equals(config));
		assertTrue(config.equals(other));
		assertTrue(other.equals(other));
		assertFalse(other.equals(null));
		assertFalse(other.equals(new String()));
		assertFalse(other.equals(different));
		assertFalse(different.equals(other));
		assertTrue(different.equals(different));
	}

}
