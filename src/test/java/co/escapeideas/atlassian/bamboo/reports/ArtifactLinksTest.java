package co.escapeideas.atlassian.bamboo.reports;

import static co.escapeideas.atlassian.bamboo.reports.ArtifactLinksConfigurationTest.getParameters;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import co.escapeideas.atlassian.bamboo.reports.services.BambooService;
import co.escapeideas.atlassian.bamboo.reports.services.Build;

import com.atlassian.confluence.macro.Macro.BodyType;
import com.atlassian.confluence.macro.Macro.OutputType;

public class ArtifactLinksTest {
	
	private BambooService service;
	private ArtifactLinks links;

	@Before
	public void setUp() throws Exception {
		service = mock(BambooService.class);
		links = new ArtifactLinks(service, new HashMap<String, Object>());
	}

	@Test
	public void testArtifactLinksBambooService() {
		assertNotNull(links);
	}

	@Test
	public void testExecute() throws Exception {
		final List<Build> builds = new ArrayList<Build>(1);
		final Map<String, String> artifacts = new HashMap<String, String>(1);
		artifacts.put("description", "http://link");
		builds.add(new Build("status", 1, artifacts ));
		when(service.getArtifacts("url")).thenReturn(builds);
		final String generated = links.execute(getParameters("url"), "", null);
		final String expected = FileUtils.readFileToString(new File("src/test/resources/example.html"));
		assertEquals(expected, generated);
	}

	@Test
	public void testExecuteNoURL() throws Exception {
		final List<Build> builds = new ArrayList<Build>(1);
		final Map<String, String> artifacts = new HashMap<String, String>(1);
		artifacts.put("description", "http://link");
		builds.add(new Build("status", 1, artifacts ));
		when(service.getArtifacts("url")).thenReturn(builds);
		final String generated = links.execute(new HashMap<String, String>(), "", null);
		final String expected = FileUtils.readFileToString(new File("src/test/resources/example.html"));
		assertEquals(expected, generated);
	}
	
	@Test
	public void testExecuteError() throws Exception {
		final List<Build> builds = new ArrayList<Build>(1);
		final Map<String, String> artifacts = new HashMap<String, String>(1);
		artifacts.put("description", "http://link");
		builds.add(new Build("status", 1, artifacts ));
		when(service.getArtifacts("url")).thenThrow(new RuntimeException("ERROR"));
		final String generated = links.execute(getParameters("url"), "", null);
		final String expected = FileUtils.readFileToString(new File("src/test/resources/example.html"));
		assertEquals(expected, generated);
	}

	@Test
	public void testExecuteInvalidURL() throws Exception {
		final List<Build> builds = new ArrayList<Build>(1);
		final Map<String, String> artifacts = new HashMap<String, String>(1);
		artifacts.put("description", "http://link");
		builds.add(new Build("status", 1, artifacts ));
		final String generated = links.execute(getParameters("xyz://url"), "", null);
		final String expected = FileUtils.readFileToString(new File("src/test/resources/example.html"));
		assertEquals(expected, generated);
	}

	@Test
	public void testGetBodyType() {
		assertEquals(BodyType.NONE, links.getBodyType());
	}

	@Test
	public void testGetOutputType() {
		assertEquals(OutputType.BLOCK, links.getOutputType());
	}

}
