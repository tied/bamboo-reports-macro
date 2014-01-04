/**
 * 
 */
package co.escapeideas.atlassian.bamboo.reports;

import java.util.List;

/**
 * @author tmullender
 *
 */
public interface JSONParser {
	
	List<Build> parseBuilds(String json);

}
