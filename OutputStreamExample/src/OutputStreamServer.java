import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Often we want to send complex data types between hosts. A regular stream only sends bytes. While you could certainly convert the data types into bytes
 * yourself, Java has a nice built in way to do it. In the example below, we create a Map from names to famous haunted places. Any client that connects gets a copy of this
 * object without having defined it in their code.
 *
 */
public class OutputStreamServer {
	private static Map<String, HauntedPlace> getHauntedPlaces() {
		Map<String, HauntedPlace> map = new HashMap<>();
		map.put("Winchester Mansion", new HauntedPlace(5, 0.3, new Date(1884, 1, 1)));
		map.put("Beechworth Lunatic Asylum", new HauntedPlace(9000, 0.8, new Date(1867,1,1)));
		map.put("Disney's Haunted Mansion", new HauntedPlace(999, 0.1, new Date(1969, 1, 1)));
		return map;
	}
	
	
}
