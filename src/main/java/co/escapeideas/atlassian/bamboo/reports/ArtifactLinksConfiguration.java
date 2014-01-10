package co.escapeideas.atlassian.bamboo.reports;

import java.util.Map;

public class ArtifactLinksConfiguration {

	private final String serverURL;
	
	public ArtifactLinksConfiguration(Map<String, String> parameters) {
		this.serverURL = parameters.get("url");
	}

	public String getUrl() {
		return serverURL;
	}

	public boolean isValid() {
		return serverURL != null;
	}

	@Override
	public String toString() {
		return "ArtifactLinksConfiguration [serverURL=" + serverURL + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((serverURL == null) ? 0 : serverURL.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArtifactLinksConfiguration other = (ArtifactLinksConfiguration) obj;
		if (serverURL == null) {
			if (other.serverURL != null)
				return false;
		} else if (!serverURL.equals(other.serverURL))
			return false;
		return true;
	}


}
