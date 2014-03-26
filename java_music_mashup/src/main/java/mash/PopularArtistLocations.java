package mash;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

public class PopularArtistLocations {

	// used for testing (not used with mule)
	List<ArtistHometown> lookup(final CityCountry cc) throws Exception {
		
		LastFM fm = new LastFM();
		MusicBrainz mb = new MusicBrainz();

		final List<ArtistHometown> artistHometowns = new ArrayList<ArtistHometown>();
		List<String> artists = fm.getArtists(cc, 5);
		for (final String artist : artists) {
			final String hometown = mb.getArtistHometown(URLEncoder.encode(artist,"UTF-8"));
			artistHometowns.add(new ArtistHometown(artist, hometown));
		}
		return artistHometowns;
	}

}
