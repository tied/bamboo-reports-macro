package co.escapeideas.atlassian.bamboo.reports;

import java.util.Map;

public class ArtifactLinksConfiguration {

	private final String serverURL;
	private final String project;
	private final String plan;
	private final String count;

	public ArtifactLinksConfiguration(Map<String, String> parameters) {
		this.serverURL = parameters.get("serverurl");
		this.project = parameters.get("project");
		this.plan = parameters.get("plan");
		this.count = getCount(parameters.get("count"));
	}

	private String getCount(String value) {
		if (value == null){
			return "0";
		}
		try {
			final Integer count = Integer.valueOf(value);
			if (count > 0){
				return String.valueOf(count-1);
			}
		} catch (NumberFormatException e){}
		return "0";
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
