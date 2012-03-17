package kimononet.geo;


public class GeoMap {

	private GeoLocation upperLeft, lowerRight;

	public GeoMap(GeoLocation ul, GeoLocation lr) {
		setDimensions(ul, lr);
	}

	public GeoLocation getUpperLeft() {
		return upperLeft;
	}

	public GeoLocation getLowerRight() {
		return lowerRight;
	}

	public void setDimensions(GeoLocation ul, GeoLocation lr) {
		if (ul.getLongitude() >= lr.getLongitude())
			throw new GeoMapException("Left longitude must be less than right longitude.");
		else if (ul.getLatitude() <= lr.getLatitude())
			throw new GeoMapException("Upper latitude must be greater than lower latitude.");
		else {
			upperLeft = ul;
			lowerRight = lr;
		}
	}

}
