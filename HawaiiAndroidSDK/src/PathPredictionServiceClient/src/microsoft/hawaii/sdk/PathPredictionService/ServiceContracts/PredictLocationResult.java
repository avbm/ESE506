/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.PathPredictionService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class to carry the results of PathPrediction.PredictLocation method
 * invocation
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictLocationResult extends ServiceResult {

	/**
	 * Result of PredictLocation call
	 */
	private PossibleDestination[] possibleDestinations;

	/**
	 * 
	 * Gets possible destinations
	 * 
	 * @return PossibleDestination[] the possible destinations in an array
	 */
	public PossibleDestination[] getPossibleDestinations() {
		return possibleDestinations;
	}

	/**
	 * Sets possible destinations
	 * 
	 * @param possibleDestinations
	 *            the possible destinations array
	 */
	public void setPossibleDestinations(
			PossibleDestination[] possibleDestinations) {
		this.possibleDestinations = possibleDestinations;
	}

}
