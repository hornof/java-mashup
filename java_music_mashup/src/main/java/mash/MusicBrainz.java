package mash;


import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MusicBrainz {

	final static String urlTemplate = "http://musicbrainz.org/ws/2/artist/?query=artist:%s";
	final DefaultHttpClient httpClient = new DefaultHttpClient();
	final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	String getArtistHometown(final String artist) throws Exception {
		
		// assemble MusicBrainz URL, get xml from it, parse xml
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.parse(String.format(urlTemplate, artist));
		return parseXml(document);
	}

	// extract artist hometown from artistlist xml
	private String  parseXml(final Document document) {
		
		final NodeList nodeList = document.getDocumentElement().getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			final Node artistList = nodeList.item(i);

			if (artistList instanceof Element) {
				final NodeList artistNodes =  artistList.getChildNodes();

				for (int j = 0; j < 2; j++) {
					final Node artist = artistNodes.item(j);

					if (artist instanceof Element) {	
						final String score = 
								artist.getAttributes().getNamedItem("ext:score").getNodeValue();
						final NodeList artistFields =  artist.getChildNodes();

						for (int k = 0; k < artistFields.getLength(); k++) {
							final Node field = artistFields.item(k);

							if (field.getNodeName().equals("area")) {
								final NodeList nameFields =  field.getChildNodes();

								for (int m = 0; m < nameFields.getLength(); m++) {

									final Node name = nameFields.item(m);
									if (name.getNodeName().equals("name")) {
										return(name.getLastChild().getTextContent());
									}
								}
							}
						}
					}
				}
			}
		}
		return "no artists found";
	}
	
	// allow xml parsing to be unit tested from a local file
	public String parseXml(final InputStream is) 
			throws ParserConfigurationException, SAXException, IOException {

		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.parse(is);
		return parseXml(document);
	}
}
