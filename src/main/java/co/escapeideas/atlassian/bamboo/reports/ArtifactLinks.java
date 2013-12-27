/**
 * 
 */
package co.escapeideas.atlassian.bamboo.reports;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wink.client.RestClient;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.renderer.v2.components.HtmlEscaper;
import com.google.gson.JsonParser;

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
		this(new HTTPBambooService(new RestClient(), new JsonParser()));
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
		final String url = config.getUrl();
		final List<Build> artifacts;
		if (isValid(url)){
			artifacts = bambooService.getArtifacts(url);
		} else {
			artifacts = new ArrayList<Build>();
		}
		return renderTable(artifacts);
	}

	private boolean isValid(String url) {
		try {
			URI.create(url);
			return true;
		} catch (Exception e){

		}
		return false;
	}

	private String renderTable(List<Build> builds) {
		final StringBuilder builder = new StringBuilder();
		builder.append("<table id=\"artifact-links\" >");
		for (final Build build : builds){
			builder.append(renderRow(build));
		}
		builder.append("</table>");
		return builder.toString();
	}

	private String renderRow(Build build) {
		final StringBuilder builder = new StringBuilder();
		builder.append("<tr><td>");
		builder.append(build.getID());
		builder.append("</td><td>");
		builder.append(build.getStatus());
		builder.append("</td>");
		for (Entry<String, String> artifact : build.getArtifacts().entrySet()){
			builder.append("<td><a href=\"");
			builder.append(HtmlEscaper.escapeAll(artifact.getValue(), false));
			builder.append("\" >");
			builder.append(HtmlEscaper.escapeAll(artifact.getKey(), false));
			builder.append("</a></td>");
		}
		builder.append("</tr>");
		return builder.toString();
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
