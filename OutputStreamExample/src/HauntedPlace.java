import java.util.Date;

public class HauntedPlace {
	private final int numberOfGhosts;
	private final double spookynessScore;
	private final Date timeEstablished;

	public HauntedPlace(int numberOfGhosts, double spookynessScore, Date timeEstablished) {
		super();
		this.numberOfGhosts = numberOfGhosts;
		this.spookynessScore = spookynessScore;
		this.timeEstablished = timeEstablished;
	}

	@Override
	public String toString() {
		return "HauntedPlace [numberOfGhosts=" + numberOfGhosts + ", spookynessScore=" + spookynessScore
				+ ", timeEstablished=" + timeEstablished + "]";
	}
}
