/**
 * 
 */
package co.escapeideas.atlassian.bamboo.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

/**
 * @author tmullender
 *
 */
public class HTTPBambooService implements BambooService {
	
	private final RestClient httpClient;
	private final JSONParser parser;

	/**
	 * 
	 */
	public HTTPBambooService(RestClient client, JSONParser parser) {
		this.httpClient = client;
		this.parser = parser;
	}

	/* (non-Javadoc)
	 * @see co.escapeideas.atlassian.bamboo.BambooService#getArtifacts(java.lang.String)
	 */
	@Override
	public List<Build> getArtifacts(String url) {
		final Resource resource = httpClient.resource(url);
		final ClientResponse response = resource.get();
		final List<Build> builds;
		if (isSuccessful(response)){
			builds = parser.parseBuilds(response.getEntity(String.class));
		} else {
			builds = getErrorList(response, url);
		}
		return builds;
	}

	private List<Build> getErrorList(ClientResponse response, String url) {
		final ArrayList<Build> list = new ArrayList<Build>();
		final Map<String, String> artifacts = new HashMap<String, String>();
		artifacts.put("ERROR", url);
		list.add(new Build(response.getMessage(), response.getStatusCode(), artifacts ));
		return list;
	}

	private boolean isSuccessful(ClientResponse response) {
		return response != null && HttpStatus.SC_OK == response.getStatusCode();
	}


}
