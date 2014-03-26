package mash;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ParseArtistXmlTest {

	final static String xmlFiles[] = { "kanye", "daftpunk" };
	final MusicBrainz mb = new MusicBrainz();

	@Test
	public void parse() throws ParserConfigurationException, SAXException, IOException {

		for (final String file : xmlFiles) {
			final String fullFile = String.format("xml/%s.xml", file);
			InputStream is = ClassLoader.getSystemResourceAsStream(fullFile);
			String location = mb.parseXml(is);
			System.out.println(location);
		}
	}
}
