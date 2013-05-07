/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.PathPredictionService.ServiceContracts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request for the Predict Location service
 */
public class PredictLocationRequest {

	/**
	 * The LatLong crumbs of the current trip whose destinations we are
	 * attempting to predict.
	 */
	private LatLong[] path;

	/**
	 * The top N results to retrieve.
	 * 
	 * Example:Calling this function with the value 500 will return the top 500
	 * most likely destinations
	 */
	private int maxDestinations;

	/**
	 * 
	 * Gets the latlong crumbs of the current trip whose destinations we are
	 * attempting to predict.
	 * 
	 * @return LatLong[] path
	 */
	@JsonProperty("Path")
	public LatLong[] getPath() {
		return path;
	}

	/**
	 * Sets the latlong crumbs of the current trip whose destinations we are
	 * attempting to predict.
	 * 
	 * @param path
	 *            the latlong array
	 */
	public void setPath(LatLong[] path) {
		this.path = path;
	}

	/**
	 * 
	 * Gets max destinations
	 * 
	 * @return int the max destinations number
	 */
	@JsonProperty("MaxDestinations")
	public int getMaxDestinations() {
		return maxDestinations;
	}

	/**
	 * Sets max destinations
	 * 
	 * @param maxDestinations
	 *            the max destinations number
	 */
	public void setMaxDestinations(int maxDestinations) {
		this.maxDestinations = maxDestinations;
	}
}
