package co.escapeideas.atlassian.bamboo.reports;

import java.util.Map;

public class ArtifactLinksConfiguration {
	
	private final String url;

	public ArtifactLinksConfiguration(Map<String, String> parameters) {
		url = parameters.get("url");
	}

	public String getUrl() {
		return url;
	}

}
