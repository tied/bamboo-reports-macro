/**
 * 
 */
package co.escapeideas.atlassian.bamboo.reports;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wink.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.escapeideas.atlassian.bamboo.reports.parsers.GsonParser;
import co.escapeideas.atlassian.bamboo.reports.services.BambooService;
import co.escapeideas.atlassian.bamboo.reports.services.Build;
import co.escapeideas.atlassian.bamboo.reports.services.HTTPBambooService;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;

/**
 * @author tmullender
 *
 */
public class ArtifactLinks implements Macro {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArtifactLinks.class);
	
	private final BambooService bambooService;
	private final Map<String, Object> velocityContext;

	/**
	 * 
	 */
	public ArtifactLinks() {
		this(new HTTPBambooService(new RestClient(), new GsonParser()), MacroUtils.defaultVelocityContext());
	}
	
	public ArtifactLinks(BambooService service, Map<String, Object> velocityContext) {
		this.bambooService = service;
		this.velocityContext = velocityContext;
	}

	/* (non-Javadoc)
	 * @see com.atlassian.confluence.macro.Macro#execute(java.util.Map, java.lang.String, com.atlassian.confluence.content.render.xhtml.ConversionContext)
	 */
	@Override
	public String execute(Map<String, String> parameters, String body, ConversionContext context) throws MacroExecutionException {
		final ArtifactLinksConfiguration config = new ArtifactLinksConfiguration(parameters);
		List<Build> builds;
		String errorMessage;
		try {
		    builds = getBuilds(config);
		    errorMessage = "";
		} catch (Exception e){
			LOGGER.warn("Error getting build list", e);
			builds = new ArrayList<Build>();
			errorMessage = e.getMessage();
		}
		velocityContext.put("builds", builds);
		velocityContext.put("error", errorMessage);
		return VelocityUtils.getRenderedTemplate("artifact-links.vm", velocityContext);
	}

	/**
	 * @param config
	 * @return
	 */
	private List<Build> getBuilds(final ArtifactLinksConfiguration config) {
		final List<Build> builds;
		final boolean valid = isValid(config);
		LOGGER.debug("Getting builds for {}", config);
		if (valid){
			builds = bambooService.getArtifacts(config.getUrl());
		} else {
			builds = new ArrayList<Build>();
		}
		return builds;
	}

	/**
	 * @param config
	 * @return
	 */
	private boolean isValid(ArtifactLinksConfiguration config) {
		if (config.isValid()){
			try {
				URI.create(config.getUrl());
				return true;
			} catch (Exception e){

			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.atlassian.confluence.macro.Macro#getBodyType()
	 */
	@Override
	public BodyType getBodyType() {
		return BodyType.NONE;
	}

	/* (non-Javadoc)
	 * @see com.atlassian.confluence.macro.Macro#getOutputType()
	 */
	@Override
	public OutputType getOutputType() {
		return OutputType.BLOCK;
	}

}
