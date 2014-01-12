/**
 * 
 */
package co.escapeideas.atlassian.bamboo.reports.parsers;

import java.util.List;

import co.escapeideas.atlassian.bamboo.reports.services.Build;

/**
 * @author tmullender
 *
 */
public interface JSONParser {
	
	List<Build> parseBuilds(String json);

}
