/**
 * 
 */
package co.escapeideas.atlassian.bamboo.reports;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.wink.client.RestClient;

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
	
	private final BambooService bambooService;

	/**
	 * 
	 */
	public ArtifactLinks() {
		this(new HTTPBambooService(new RestClient(), new GsonParser()));
	}
	
	public ArtifactLinks(BambooService service) {
		this.bambooService = service;
	}

	/* (non-Javadoc)
	 * @see com.atlassian.confluence.macro.Macro#execute(java.util.Map, java.lang.String, com.atlassian.confluence.content.render.xhtml.ConversionContext)
	 */
	@Override
	public String execute(Map<String, String> parameters, String body, ConversionContext context) throws MacroExecutionException {
		final ArtifactLinksConfiguration config = new ArtifactLinksConfiguration(parameters);
		final VelocityContext velocityContext = new VelocityContext(MacroUtils.defaultVelocityContext());
		final List<Build> builds;
		if (isValid(config)){
			builds = bambooService.getArtifacts(config.getUrl());
		} else {
			builds = new ArrayList<Build>();
		}
		velocityContext.put("builds", builds);
		return VelocityUtils.getRenderedTemplate("artifact-links.vm", velocityContext);
	}

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
