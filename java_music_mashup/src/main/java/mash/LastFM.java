package mash;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

public class LastFM {

	final ObjectMapper mapper = new ObjectMapper();
	final JsonFactory factory = mapper.getJsonFactory();

	List<String> getArtists(final CityCountry cc, final int limit) throws JsonParseException, MalformedURLException, IOException {

		// assemble lastFM URL (uses audioscrobbler)
		final String method = "method=geo.getmetroartistchart";
		final String country = String.format("country=%s", cc.getCountry());
		final String metro = String.format("metro=%s", cc.getCity());
		final String apiKey = "api_key=a7b5d6873ba983e70be73d5106a61ebc";
		final String lim = String.format("limit=%s", limit);
		final String format = "format=json";
		final String url = String.format("http://ws.audioscrobbler.com/2.0/?%s&%s&%s&%s&%s&%s",
				method, country, metro, lim, apiKey, format);

		// get json from URL, parse it
		final JsonNode node = mapper.readTree(factory.createJsonParser(new URL(url)));
		return parseJson(node);
	}

	// extract artist names from topartists json
	private List<String> parseJson (final JsonNode node) {
		JsonNode topartists = node.findValue("topartists");
		JsonNode artist = topartists.findValue("artist");
		return artist.findValuesAsText("name");	
	}

	// allow json parsing to be unit tested from a local file
	public List<String> parseJson(final String fullFile) throws JsonParseException, IOException {
		final InputStream is = ClassLoader.getSystemResourceAsStream(fullFile);
		final JsonParser jp = factory.createJsonParser(is);
		final JsonNode node = mapper.readTree(jp);

		return parseJson(node);
	}
}
