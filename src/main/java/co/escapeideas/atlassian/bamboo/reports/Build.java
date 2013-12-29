/**
 * 
 */
package co.escapeideas.atlassian.bamboo.reports;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author tmullender
 *
 */
public class Build {
	
	private final Map<String, String> artifacts;
	private final String status;
	private final int id;

	/**
	 * 
	 */
	public Build(final String status, final int id, final Map<String, String> artifacts) {
		this.status = status;
		this.id = id;
		this.artifacts = artifacts;
	}

	public Set<Entry<String,String>> getArtifacts() {
		return artifacts.entrySet();
	}

	public String getStatus() {
		return status;
	}

	public int getID() {
		return id;
	}

}
