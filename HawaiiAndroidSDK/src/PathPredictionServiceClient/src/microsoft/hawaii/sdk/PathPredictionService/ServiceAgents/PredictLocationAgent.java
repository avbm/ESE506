/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.PathPredictionService.ServiceAgents;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Status;
import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceFault;
import microsoft.hawaii.hawaiiClientLibraryBase.Exceptions.HawaiiException;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Json;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.PossibleDestination;
import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.PredictLocationRequest;
import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.PredictLocationResult;

/**
 * Service Agent class to create call PredictLocation from Path
 * PredictionService.
 */
public class PredictLocationAgent extends ServiceAgent<PredictLocationResult> {

	/**
	 * Location prediction request
	 */
	private PredictLocationRequest request;

	/**
	 * 
	 * Gets location prediction request.
	 * 
	 * @return PredictLocationRequest the location prediction request
	 */
	public PredictLocationRequest getRequst() {
		return request;
	}

	/**
	 * Sets location prediction request.
	 * 
	 * @param requst
	 *            the request of location prediction
	 */
	private void setRequst(PredictLocationRequest requst) {
		this.request = requst;
	}

	/**
	 * 
	 * Initializes an instance of the {@link PredictLocationAgent} class
	 * 
	 * @param baseUri
	 *            uri to access service
	 * @param clientIdentity
	 *            the client identity
	 * @param request
	 *            predict location request
	 * @throws Exception
	 */
	public PredictLocationAgent(URI baseUri, ClientIdentity clientIdentity,
			PredictLocationRequest request) throws Exception {
		super(PredictLocationResult.class, HttpMethod.POST, null);
	}

	/**
	 * 
	 * Initializes an instance of the {@link PredictLocationAgent} class
	 * 
	 * @param baseUri
	 *            uri to access service
	 * @param clientIdentity
	 *            the client identity
	 * @param request
	 *            predict location request
	 * @param state
	 *            specifies a user-defined object
	 * @throws Exception
	 */
	public PredictLocationAgent(URI baseUri, ClientIdentity clientIdentity,
			PredictLocationRequest request, Object state) throws Exception {
		super(PredictLocationResult.class, HttpMethod.POST, state);

		// parameter validation
		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", clientIdentity);
		Utility.assertNotNull("request", request);

		this.setRequst(request);
		this.clientIdentity = clientIdentity;
		this.serviceUri = baseUri;
	}

	/**
	 * Override {@link getRequestBodyData} method of {@link ServiceAgent} class
	 */
	@Override
	protected byte[] getRequestBodyData() throws HawaiiException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			Json.serializeToStream(this.request, outStream);
		} catch (IOException ex) {
			throw new HawaiiException("Failed to prepare request body", ex);
		}

		if (outStream.size() > 0) {
			return outStream.toByteArray();
		} else {
			return null;
		}
	}

	/**
	 * Override {@link parseOutput} method of {@link ServiceAgent} class
	 */
	@Override
	protected void parseOutput(InputStream inputStream) {
		Utility.assertNotNull("input stream can't be null", inputStream);
		try {
			if (this.serviceResult.getStatus() == Status.Success) {
				// get possibleDesstination array from json stream
				PossibleDestination[] possibleArray = Json
						.deserializeFromStream(PossibleDestination[].class,
								inputStream);
				PredictLocationResult predictResult = new PredictLocationResult();
				predictResult.setPossibleDestinations(possibleArray);
				this.setServiceResult(predictResult);
			} else {
				ServiceFault fault = Json.deserializeFromStream(
						ServiceFault.class, inputStream);
				if (fault != null) {
					this.handleException(fault.getMessage(), null);
				}
			}
		} catch (Exception ex) {
			this.handleException(
					"Failed to parse service result object from response", ex);
		}
	}
}
