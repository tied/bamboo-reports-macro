package co.escapeideas.atlassian.bamboo.reports.services;

import java.util.List;

public interface BambooService {
	
	List<Build> getArtifacts(String url);

}
