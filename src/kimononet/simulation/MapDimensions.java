package kimononet.simulation;

import kimononet.geo.GeoLocation;

public class MapDimensions {

	private GeoLocation upperLeft, lowerRight;

	public MapDimensions(GeoLocation ul, GeoLocation lr) {
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
			throw new MapDimensionsException("Left longitude must be less than right longitude.");
		else if (ul.getLatitude() <= lr.getLatitude())
			throw new MapDimensionsException("Upper latitude must be greater than lower latitude.");
		else {
			upperLeft = ul;
			lowerRight = lr;
		}
	}

}
