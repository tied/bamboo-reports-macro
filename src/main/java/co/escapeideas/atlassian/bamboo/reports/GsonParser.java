package co.escapeideas.atlassian.bamboo.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonParser implements JSONParser {
	
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
		final JsonElement artifactArray = artifacts.getAsJsonObject().get("artifact");
		for (JsonElement artifact : artifactArray.getAsJsonArray()){
			final JsonObject object = artifact.getAsJsonObject();
			final String name = object.get("name").getAsString();
			final String url = object.get("link").getAsJsonObject().get("href").getAsString();
			artifactMap.put(name, url);
		}
		return artifactMap;
	}


}
