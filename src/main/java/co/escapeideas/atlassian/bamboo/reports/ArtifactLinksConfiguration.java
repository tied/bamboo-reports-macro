package co.escapeideas.atlassian.bamboo.reports;

import java.util.Map;

public class ArtifactLinksConfiguration {
	
	private final String serverURL;
	private final String project;
	private final String plan;
	private final String count;

	public ArtifactLinksConfiguration(Map<String, String> parameters) {
		serverURL = parameters.get("serverurl");
		project = parameters.get("project");
		plan = parameters.get("plan");
		count = parameters.get("count");
	}

	public String getUrl() {
		return serverURL + "/rest/api/latest/result/" + project + "-" + plan + ".json?expand=results[:" + count + "].result.stages.stage.results.result.artifacts";
	}

	public boolean isValid() {
		return serverURL != null;
	}
	
	@Override
	public String toString() {
		return "ArtifactLinksConfiguration [serverURL=" + serverURL
				+ ", project=" + project + ", plan=" + plan + ", count="
				+ count + "]";
	}

}
