package co.escapeideas.atlassian.bamboo.reports.parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.escapeideas.atlassian.bamboo.reports.services.Build;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonParser implements JSONParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GsonParser.class);
	
	private final JsonParser parser = new JsonParser();

	@Override
	public List<Build> parseBuilds(String json) {
		return toList(parser.parse(json));
	}
	
	/**
	 * Converts the response into a list of Builds
	 * @param response
	 * @return
	 */
	private List<Build> toList(JsonElement response) {
		final ArrayList<Build> list = new ArrayList<Build>();
		final JsonElement results = response.getAsJsonObject().get("results");
		final JsonElement resultArray = results.getAsJsonObject().get("result");
		LOGGER.debug("List of results", resultArray);
		for (JsonElement result : resultArray.getAsJsonArray()) {
			final Build build = toBuild(result);
			list.add(build);
		}
		return list;
	}

	/**
	 * Converts the result into a Build
	 * @param result
	 * @return
	 */
	private Build toBuild(JsonElement result) {
		final JsonObject resultObject = result.getAsJsonObject();
		final int id = resultObject.get("buildNumber").getAsInt();
		final String status = resultObject.get("buildState").getAsString();
		final Map<String, String> artifactMap = toArtifactMap(resultObject);
		final Build build = new Build(status, id, artifactMap);
		return build;
	}

	/**
	 * Converts the resultObject into a Map of artifact name to url
	 * @param resultObject
	 * @return
	 */
	private Map<String, String> toArtifactMap(final JsonObject resultObject) {
		final Map<String, String> artifactMap = new HashMap<String, String>();
		final JsonElement artifacts = resultObject.get("artifacts");
		final JsonElement stages = resultObject.get("stages");
		final JsonElement artifactArray = artifacts.getAsJsonObject().get("artifact");
		artifactMap.putAll(toMap(artifactArray));
		LOGGER.debug("Stages found in result", stages);
		if (stages != null){
			final JsonElement stageArray = stages.getAsJsonObject().get("stage");
			artifactMap.putAll(getArtifactsFromStages(stageArray));
		}
		return artifactMap;
	}

	/**
	 * @param artifactMap
	 * @param stageArray
	 * @return 
	 */
	private Map<String, String> getArtifactsFromStages(final JsonElement stageArray) {
		final Map<String, String> artifactMap = new HashMap<String, String>();
		if (stageArray != null){
			LOGGER.debug("List of stages", stageArray);
			for (JsonElement stage : stageArray.getAsJsonArray()){
				final JsonObject object = stage.getAsJsonObject();
				final List<Build> builds = toList(object);
				LOGGER.debug("List of child artifacts", builds);
				for (Build build : builds){
					artifactMap.putAll(build.getArtifactMap());
				}
			}
		}
		return artifactMap;
	}

	/**
	 * @param artifactMap
	 * @param artifactArray
	 * @return 
	 */
	private Map<String, String> toMap(final JsonElement artifactArray) {
		final Map<String, String> artifactMap = new HashMap<String, String>();
		LOGGER.debug("List of artifacts", artifactArray);
		if (artifactArray != null){
			for (JsonElement artifact : artifactArray.getAsJsonArray()){
				final JsonObject object = artifact.getAsJsonObject();
				final String name = object.get("name").getAsString();
				final String url = object.get("link").getAsJsonObject().get("href").getAsString();
				artifactMap.put(name, url);
			}
		}
		return artifactMap;
	}


}
