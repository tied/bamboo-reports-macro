package co.escapeideas.atlassian.bamboo.reports;

import java.util.Map;

public class ArtifactLinksConfiguration {
	
	private final String serverURL;
	private final String project;
	private final String plan;
	private final String job;
	private final String count;

	public ArtifactLinksConfiguration(Map<String, String> parameters) {
		serverURL = parameters.get("ServerURL");
		project = parameters.get("Project");
		plan = parameters.get("Plan");
		job = parameters.get("Job");
		count = parameters.get("Count");
	}

	public String getUrl() {
		return serverURL + "/rest/api/latest/result/" + project + "-" + plan + "-" + job + ".json?expand=results[" + count + "].result.artifacts";
	}

}
