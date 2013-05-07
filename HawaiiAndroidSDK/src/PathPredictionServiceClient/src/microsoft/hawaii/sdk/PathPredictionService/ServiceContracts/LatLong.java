/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.PathPredictionService.ServiceContracts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class represents a latitude and longitude
 */
public class LatLong {

	/**
	 * The average radius of the earth in meters value taken from
	 * http://en.wikipedia.org/wiki/Earth_radius#Mean_radius
	 */
	private final float earthRadiusMeters = 6371009;

	/**
	 * Latitude of location
	 */
	private float latitude;

	/**
	 * Longitude of location
	 */
	private float longitude;

	/**
	 * 
	 * Gets latitude of location
	 * 
	 * @return float the latitude of location
	 */
	@JsonProperty("Lat")
	public float getLatitude() {
		return latitude;
	}

	/**
	 * Sets latitude of location
	 * 
	 * @param latitude
	 *            the latitude of location
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets longitude of location
	 * 
	 * @return float the longitude of location
	 */
	@JsonProperty("Long")
	public float getLongitude() {
		return longitude;
	}

	/**
	 * Sets longitude of location
	 * 
	 * @param longitude
	 *            the longitude of location
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * 
	 * Initializes an instance of the {@link LatLong} class
	 * 
	 * @param latitudeDegrees
	 *            latitude of location
	 * @param longitudeDegrees
	 *            longitude of location
	 */
	public LatLong(float latitudeDegrees, float longitudeDegrees) {
		this();
		this.latitude = latitudeDegrees;
		this.longitude = longitudeDegrees;
	}

	/**
	 * 
	 * Initializes an instance of the {@link LatLong} class
	 */
	public LatLong() {
		this.latitude = 0;
		this.longitude = 0;
	}

	/**
	 * 
	 * Determines if two latlongs refer to the same location
	 * 
	 * @param llA
	 *            the first location
	 * @param llB
	 *            the second location
	 * @return boolean true if the two latlongs are the same, false otherwise
	 */
	public static boolean LatLongEqual(LatLong llA, LatLong llB) {
		if (llA != null && llB != null) {
			return llA.getLatitude() == llB.getLatitude()
					&& llA.getLongitude() == llB.getLongitude();
		}
		return false;
	}

	/**
	 * 
	 * Determines if two latlongs are NOT the same location
	 * 
	 * @param llA
	 *            the first location
	 * @param llB
	 *            the second location
	 * @return boolean true if the two latlongs differ, false otherwise
	 */
	public static boolean LatLongNotEqual(LatLong llA, LatLong llB) {
		if (llA != null && llB != null) {
			return llA.getLatitude() != llB.getLatitude()
					|| llA.getLongitude() != llB.getLongitude();
		}
		return false;
	}

	/**
	 * DistanceMeters
	 * 
	 * Calculates the distance between the current LatLong and the supplied
	 * LatLong. Uses Haversine formula from
	 * http://mathforum.org/library/drmath/view/51879.html
	 * 
	 * @param latLong
	 *            The remote location to calculate the distance to
	 * @return float The distance in meters between the two locations
	 */
	public float DistanceMeters(LatLong latLong) {

		// Convert to radiant
		float latitude1 = (float) (Math.PI * this.getLatitude() / 180.0);
		float longitude1 = (float) (Math.PI * this.getLongitude() / 180.0);
		float latitude2 = (float) (Math.PI * latLong.getLatitude() / 180.0);
		float longitude2 = (float) (Math.PI * latLong.getLongitude() / 180.0);

		// do the formula
		float dlon = longitude2 - longitude1;
		float dlat = latitude2 - latitude1;
		float sinedlatdiv2 = (float) Math.sin(dlat / 2.0);
		float sinedlondiv2 = (float) Math.sin(dlon / 2.0);
		float a = (float) ((sinedlatdiv2 * sinedlatdiv2) + (Math.cos(latitude1)
				* Math.cos(latitude2) * sinedlondiv2 * sinedlondiv2));
		float c = (float) (2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
		float distMeters = c * earthRadiusMeters;
		return distMeters;
	}

}
