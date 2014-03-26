package mash;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MuleMasher {

	public String mash(final String payload) throws Exception {

		// TODO: parse request params in mule
		final Matcher city = Pattern.compile("city=([^&]*)").matcher(payload);
		city.find();
		final String cityStr = city.group(1);
		final Matcher country = Pattern.compile("country=([^&]*)").matcher(payload);
		country.find();
		final String countryStr = country.group(1);
		
		// mash
		LastFM fm = new LastFM();
		MusicBrainz mb = new MusicBrainz();

		final List<ArtistHometown> artistHometowns = new ArrayList<ArtistHometown>();
		List<String> artists = fm.getArtists(new CityCountry(cityStr, countryStr), 5);
		for (final String artist : artists) {
			final String hometown = mb.getArtistHometown(URLEncoder.encode(artist,"UTF-8"));
			artistHometowns.add(new ArtistHometown(artist, hometown));
		}
		
		// return string (TODO: return JSON to browser)
		final StringBuilder sb = new StringBuilder();
		for (final ArtistHometown ah : artistHometowns) {
			sb.append(String.format("%s, %s\n", ah.getBand(), ah.getLocation()));
		}
		return sb.toString();
	}
}
