package mash;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.junit.Test;

public class ParseHometownJsonTest {

	final static String jsonFiles[] = { "newyork", "austin", "london", "paris", "tokyo" };
	final LastFM lfm = new LastFM();

	@Test
	public void parse() throws JsonParseException, IOException {

		for (final String file : jsonFiles) {
			final String fullFile = String.format("json/%s.json", file);
			List<String> bands = lfm.parseJson(fullFile);
			System.out.println(bands);
		}
	}
}
