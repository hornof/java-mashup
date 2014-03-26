package mash;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.junit.Test;

public class InternetTests {

	CityCountry[] ccs = {
			new CityCountry("New York", "United States"),
			new CityCountry("Austin", "United States"),
			new CityCountry("London", "United Kingdom"),
			new CityCountry("Paris", "France"),
			new CityCountry("Tokyo", "Japan"),
	};


//	@Test
	public void testLastFM() throws JsonParseException, IOException {
		LastFM fm = new LastFM();

		for (final CityCountry cc : ccs) {
			System.out.println(fm.getArtists(cc, 5));
		}
	}

//	@Test
	public void testMusicBrainz() throws Exception {

		MusicBrainz mb = new MusicBrainz();

		System.out.println(mb.getArtistHometown("kanye%20west"));
		System.out.println(mb.getArtistHometown("daft+punk"));
	}

	@Test
	public void testMashup() throws Exception {
		PopularArtistLocations pal = new PopularArtistLocations();

		for (final CityCountry cc : ccs) {
			System.out.format("%s\n  ", cc.getCity());
			List<ArtistHometown> ahs = pal.lookup(cc);
			for (final ArtistHometown ah : ahs) {
				System.out.format("%s (%s)  ", ah.getBand(), ah.getLocation());
			}
			System.out.println();
		}
	}

}
