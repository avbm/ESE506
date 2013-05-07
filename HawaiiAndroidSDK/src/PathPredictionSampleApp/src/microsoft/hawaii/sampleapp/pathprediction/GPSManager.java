package microsoft.hawaii.sampleapp.pathprediction;

import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.LatLong;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GPSManager {
	private LocationListener listener;
	private LocationManager locationManager;
	private String bestProvider;

	public GPSManager(Activity activity, LocationListener listener) {
		locationManager = (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);
		if (bestProvider == null) {
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);
			criteria.setPowerRequirement(Criteria.POWER_LOW);
			bestProvider = locationManager.getBestProvider(criteria, true);
		}

		this.listener = listener;
	}

	public void removeListener() {
		if (bestProvider != null && this.listener != null) {
			locationManager.removeUpdates(this.listener);
			this.listener = null;
		}
	}

	public boolean isGPSEnabled() {
		if (bestProvider != null) {
			return locationManager.isProviderEnabled(bestProvider);
		}

		return false;
	}

	public void resumeGPSTracking() {
		if (bestProvider == null) {
			return;
		}

		locationManager.requestLocationUpdates(bestProvider,
				Constants.GPSTimeDelta, Constants.GPSDistanceDelta, listener);
	}

	public void initialize() {
		if (bestProvider == null) {
			return;
		}

		if (bestProvider.equalsIgnoreCase(LocationManager.GPS_PROVIDER)) {
			// GPS is enabled but may not receive data since no connection.
			if (locationManager.getLastKnownLocation(bestProvider) == null) {
				// Try to get location using network.
				if (locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null) {
					// If success, switch bestProvider to be Network
					bestProvider = LocationManager.NETWORK_PROVIDER;
				}
			}
		}

		locationManager.requestLocationUpdates(bestProvider,
				Constants.GPSTimeDelta, Constants.GPSDistanceDelta, listener);
	}

	public LatLong getMyCoordinate() {
		if (bestProvider != null) {
			Location location = locationManager
					.getLastKnownLocation(bestProvider);
			return Utility.convertToLatLong(location);
		}

		return null;
	}

	public Location getMyLocation() {
		if (bestProvider != null) {
			return locationManager.getLastKnownLocation(bestProvider);
		}

		return null;
	}

	private static final int TWO_MINUTES = 1000 * 60 * 2;

	/**
	 * Determines whether one Location reading is better than the current
	 * Location fix
	 * 
	 * @param location
	 *            The new Location that you want to evaluate
	 * @param currentBestLocation
	 *            The current Location fix, to which you want to compare the new
	 *            one
	 */
	public static boolean isBetterLocation(Location location,
			Location currentBestLocation) {
		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
		boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private static boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}
}
