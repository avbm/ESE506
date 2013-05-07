package microsoft.hawaii.sampleapp.pathprediction;

import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.LatLong;

public class Constants {

	public static final int DefaultGPSZoomLevel = 17;

	// Minimum distance a user must move in meters before GPS location updates
	// on map
	public static final int GPSDistanceDelta = 5;

	// Minimum time that must past in ms before GPS will update users location
	public static final int GPSTimeDelta = 1 * 1000;

	// a fixed location which is located near Microsoft Building 92 in Bellevue
	public final static LatLong FixedLocation = new LatLong((float) 47.641121,
			(float) -122.139854);
}
