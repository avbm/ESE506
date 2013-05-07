package microsoft.hawaii.sampleapp.pathprediction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.sampleappbase.HawaiiBaseApplication;
import microsoft.hawaii.sdk.PathPredictionService.PathPredictionService;
import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.LatLong;
import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.PossibleDestination;
import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.PredictLocationRequest;
import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.PredictLocationResult;
import android.app.AlertDialog;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MainActivity extends BaseMapActivity implements LocationListener {

	private final static int MaxCrumbs = 5;
	private final static int MaxDestinations = 10;

	private Button predictButton;
	private TextView gpsStatusTextView;
	private ProgressBar progressBar;
	private MapView mapView;
	private GPSManager gpsManager;
	private MapController mapController;

	private CustomItemizedOverlay overLayPath;
	private CustomItemizedOverlay overLayPrediction;

	private LatLong targetLocation;
	private LatLong currentLocation;
	private Queue<LatLong> tripCrumbs;
	private boolean predictionRequestInProgress = false;

	private AsyncTask<Void, Integer, AlertDialog.Builder> pathPredictionTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

		this.gpsStatusTextView = (TextView) this
				.findViewById(R.id.gps_status_textView);
		this.mapView = (MapView) findViewById(R.id.mapview);
		this.mapView.setBuiltInZoomControls(true);
		this.mapController = this.mapView.getController();
		this.mapController.setZoom(Constants.DefaultGPSZoomLevel);

		this.overLayPath = new CustomItemizedOverlay(this.getResources()
				.getDrawable(R.drawable.pin_gps), this);
		this.overLayPrediction = new CustomItemizedOverlay(this.getResources()
				.getDrawable(R.drawable.pin_red_flag), this);
		List<Overlay> overlayList = this.mapView.getOverlays();
		overlayList.add(overLayPath);
		overlayList.add(overLayPrediction);

		this.tripCrumbs = new LinkedList<LatLong>();

		gpsManager = new GPSManager(this, this);
		// Check if GPS is enabled
		// if not, prompt user with error message
		if (!gpsManager.isGPSEnabled()) {
			this.showErrorMessage(
					"GPS is disabled. Enable GPS in Settings and restart app!",
					null);
		}

		predictButton = (Button) this.findViewById(R.id.run_prediction_button);
		predictButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				predictButtonClick(v);
			}
		});

		ImageButton goToMyLocation = (ImageButton) this
				.findViewById(R.id.currentLocation_imageButton);
		goToMyLocation.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LatLong myLocation = gpsManager.getMyCoordinate();
				if (myLocation != null) {
					displayLocation(myLocation, false);
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Fail to navigate to your location! Check your GPS settings!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		// display current location
		gpsManager.initialize();
		LatLong myLocation = gpsManager.getMyCoordinate();
		if (myLocation != null) {
			this.currentLocation = myLocation;
		} else {
			this.currentLocation = Constants.FixedLocation;
		}

		displayLocation(this.currentLocation, false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();

		if (this.currentLocation != null) {
			GeoPoint point = Utility.convertToGeoPoint(this.currentLocation);
			this.mapController.animateTo(point);
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		this.gpsManager.removeListener();
		if (this.pathPredictionTask != null) {
			this.pathPredictionTask = null;
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		this.gpsManager.resumeGPSTracking();
	}

	private void predictButtonClick(View v) {
		v.setEnabled(false);
		executePathPrediction();
	}

	private void executePathPrediction() {
		final MainActivity thisActivity = this;
		class PathPredictionTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private HawaiiBaseApplication application = thisActivity
					.getBaseApplication();
			private PredictLocationResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				thisActivity.predictionRequestInProgress = true;
				PredictLocationRequest request = new PredictLocationRequest();
				request.setPath(tripCrumbs.toArray(new LatLong[tripCrumbs
						.size()]));
				request.setMaxDestinations(MaxDestinations);

				try {
					PathPredictionService.predictLocation(
							application.getClientIdentity(), request,
							new OnCompleteListener<PredictLocationResult>() {
								public void done(PredictLocationResult result) {
									serviceResult = result;
								}
							}, null);
				} catch (Exception exception) {
					return dialogToShow(
							"Couldn't execute the path prediction operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow("Error when trying to predict path",
							serviceResult.getException());
				}

				return null;
			}

			protected void onPreExecute() {
				progressBar.setVisibility(View.VISIBLE);
			}

			protected void onPostExecute(AlertDialog.Builder dialogBuilder) {
				thisActivity.resetPredictionState();
				if (dialogBuilder != null) {
					showErrorMessage(dialogBuilder);
				} else {
					thisActivity.showMessage("prediction succeeded");
					displayDestinationList(this.serviceResult
							.getPossibleDestinations());
				}

				thisActivity.predictionRequestInProgress = false;
				progressBar.setVisibility(View.GONE);
				pathPredictionTask = null;
			}
		}

		pathPredictionTask = new PathPredictionTask();
		pathPredictionTask.execute();
	}

	private void displayDestinationList(PossibleDestination[] destinationList) {
		if (destinationList == null || destinationList.length < 1) {
			return;
		}

		// find the centroid of the possible destinations using
		// weighted means
		// for more info on weighted means see:
		// http://en.wikipedia.org/wiki/Weighted_mean
		LatLong centroid = new LatLong(0, 0);
		double probabilitySum = 0;

		// compute the weighted mean with the normalized probabilities of the
		// top 25 percentile
		int topPercentile = Math.max(destinationList.length / 4, 1);
		Arrays.sort(destinationList, new Comparator<PossibleDestination>() {
			public int compare(PossibleDestination lhs, PossibleDestination rhs) {
				if (lhs == null) {
					return 0;
				}
				if (rhs == null) {
					return 1;
				}

				return Double.compare(rhs.getProbability(),
						lhs.getProbability());
			}
		});

		PossibleDestination[] newList = new PossibleDestination[topPercentile];
		int loop = 0;
		while (loop < topPercentile) {
			newList[loop] = destinationList[loop];
			loop++;
		}

		for (PossibleDestination pd : newList) {
			centroid.setLatitude(centroid.getLatitude()
					+ (float) (pd.getLocation().getLatitude() * pd
							.getProbability()));
			centroid.setLongitude(centroid.getLongitude()
					+ (float) (pd.getLocation().getLongitude() * pd
							.getProbability()));
			probabilitySum += pd.getProbability();
		}

		centroid.setLatitude((float) (centroid.getLatitude() / probabilitySum));
		centroid.setLongitude((float) (centroid.getLongitude() / probabilitySum));

		this.targetLocation = centroid;
		// clear path and move to target location
		this.clearPins();
		this.moveToCenter(this.targetLocation, true, true);
	}

	public void onLocationChanged(Location location) {
		if (this.currentLocation != null) {
			while (this.tripCrumbs.size() >= MaxCrumbs) {
				this.tripCrumbs.poll();
			}

			this.tripCrumbs.offer(currentLocation);
		}

		this.currentLocation = Utility.convertToLatLong(location);
		this.moveToCenter(this.currentLocation, true);

		if (this.tripCrumbs.size() > 1 && !this.predictionRequestInProgress) {
			this.showMessage("ready for prediction");
			this.predictButton.setEnabled(true);
		}
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		if (status == LocationProvider.OUT_OF_SERVICE) {
			this.gpsStatusTextView.setText("GPS is out of service!");
			this.gpsStatusTextView.setTextColor(Color.RED);
		} else if (status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
			this.gpsStatusTextView.setText("GPS is temporarily unavailable!");
			this.gpsStatusTextView.setTextColor(Color.RED);
		}
	}

	public void onProviderEnabled(String provider) {
		Toast.makeText(this, "Enabled new provider " + provider,
				Toast.LENGTH_SHORT).show();
	}

	public void onProviderDisabled(String provider) {
		Toast.makeText(this, "Disabled provider " + provider,
				Toast.LENGTH_SHORT).show();

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private void displayLocation(LatLong point, boolean prediction) {
		if (point == null) {
			return;
		}

		GeoPoint p = Utility.convertToGeoPoint(point);
		this.addPin(p, prediction);
		this.mapController.animateTo(p);
		this.mapController.setCenter(p);
	}

	private void moveToCenter(LatLong point, boolean addPin) {
		moveToCenter(point, addPin, false);
	}

	private void moveToCenter(LatLong point, boolean addPin, boolean prediction) {
		GeoPoint p = Utility.convertToGeoPoint(point);
		this.mapController.setCenter(p);
		if (addPin) {
			addPin(p, prediction);
		}
	}

	private void addPin(GeoPoint point, boolean prediction) {
		OverlayItem overlayitem = new OverlayItem(point, "Hello",
				"Your destination here!");
		if (!prediction) {
			this.overLayPath.addOverlay(overlayitem);
		} else {
			this.overLayPrediction.addOverlay(overlayitem);
		}
	}

	private void resetPredictionState() {
		if (this.overLayPath.size() > MaxCrumbs) {
			this.tripCrumbs.clear();
			this.clearPins();
		}
	}

	private void clearPins() {
		this.overLayPath.clearOverlay();
		this.overLayPrediction.clearOverlay();
	}
}
