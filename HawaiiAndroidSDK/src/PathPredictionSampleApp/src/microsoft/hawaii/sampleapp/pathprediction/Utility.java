/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.pathprediction;

import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.LatLong;
import android.location.Location;

import com.google.android.maps.GeoPoint;

/**
 *
 */
public final class Utility {
	public final static int MicroDegreeTimes = 1000000;

	public static GeoPoint convertToGeoPoint(Location location) {
		if (location == null) {
			return null;
		}

		return new GeoPoint((int) (location.getLatitude() * MicroDegreeTimes),
				(int) (location.getLongitude() * MicroDegreeTimes));
	}

	public static GeoPoint convertToGeoPoint(LatLong location) {
		if (location == null) {
			return null;
		}

		return new GeoPoint((int) (location.getLatitude() * MicroDegreeTimes),
				(int) (location.getLongitude() * MicroDegreeTimes));
	}

	public static LatLong convertToLatLong(Location location) {
		if (location == null) {
			return null;
		}

		return new LatLong((float) location.getLatitude(),
				(float) location.getLongitude());
	}
}
