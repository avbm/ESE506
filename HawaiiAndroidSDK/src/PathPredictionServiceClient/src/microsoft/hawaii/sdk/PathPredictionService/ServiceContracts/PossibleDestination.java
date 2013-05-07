/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.PathPredictionService.ServiceContracts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An object that represents a latlong and an associated probability of being a
 * final destination
 */
public class PossibleDestination {

	/**
	 * 
	 * Initializes an instance of the {@link PossibleDestination} class
	 */
	public PossibleDestination() {
		this.probability = 0;
		this.location = new LatLong();
	}

	/**
	 * The location of the potential destination
	 */
	private LatLong location;

	/**
	 * The probability that this location is a potential destination
	 */
	private double probability;

	/**
	 * 
	 * Gets the location of the potential destination.
	 * 
	 * @return LatLong the potential destination
	 */
	@JsonProperty("Loc")
	public LatLong getLocation() {
		return location;
	}

	/**
	 * Sets location
	 * 
	 * @param location
	 *            the location of the potential destination
	 */
	public void setLocation(LatLong location) {
		this.location = location;
	}

	/**
	 * 
	 * Gets the probability that this location is a potential destination.
	 * 
	 * @return double the probability
	 */
	@JsonProperty("Prob")
	public double getProbability() {
		return probability;
	}

	/**
	 * Sets the probability that this location is a potential destination.
	 * 
	 * @param probability
	 *            the probability of this location
	 */
	public void setProbability(double probability) {
		this.probability = probability;
	}

}
