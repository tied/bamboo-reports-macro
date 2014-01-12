package co.escapeideas.atlassian.bamboo.reports.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.junit.Before;
import org.junit.Test;

import co.escapeideas.atlassian.bamboo.reports.parsers.JSONParser;
import co.escapeideas.atlassian.bamboo.reports.services.Build;
import co.escapeideas.atlassian.bamboo.reports.services.HTTPBambooService;

public class HTTPBambooServiceTest {
	
	private HTTPBambooService service;
	private RestClient client;
	private JSONParser parser;

	@Before
	public void setUp() throws Exception {
		client = mock(RestClient.class);
		parser = mock(JSONParser.class);
		service = new HTTPBambooService(client, parser);
	}

	@Test
	public void testHTTPBambooService() {
		assertNotNull(service);
	}

	@Test
	public void testGetArtifacts() {
		final List<Build> builds = new ArrayList<Build>();
		final ClientResponse response = mock(ClientResponse.class);
		final Resource resource = mock(Resource.class);
		when(client.resource("URL")).thenReturn(resource);
		when(resource.get()).thenReturn(response);
		when(response.getStatusCode()).thenReturn(200);
		when(response.getEntity(String.class)).thenReturn("JSON");
		when(parser.parseBuilds("JSON")).thenReturn(builds);
		assertEquals(builds, service.getArtifacts("URL"));
	}

	@Test
	public void testGetArtifacts404() {
		final ClientResponse response = mock(ClientResponse.class);
		final Resource resource = mock(Resource.class);
		when(client.resource("URL")).thenReturn(resource);
		when(resource.get()).thenReturn(response);
		when(response.getStatusCode()).thenReturn(404);
		when(response.getMessage()).thenReturn("Not found");
		final List<Build> builds = service.getArtifacts("URL");
		assertEquals(1, builds.size());
		final Build build = builds.get(0);
		assertEquals(404, build.getID());
		assertEquals("Not found", build.getStatus());
	}

}
