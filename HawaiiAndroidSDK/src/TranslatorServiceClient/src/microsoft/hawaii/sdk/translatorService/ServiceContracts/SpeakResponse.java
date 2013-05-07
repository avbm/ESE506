/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.translatorService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for the speak method.
 */
public class SpeakResponse extends ServiceResult {

	/**
	 * The byte array of the audio stream for speech.
	 */
	private byte[] Audio;

	/**
	 * Initializes an instance of the {@link SpeakResponse} class
	 */
	public SpeakResponse() {
	}

	/**
	 * Gets the audio steam byte array
	 * 
	 * @return byte[] Returns the audio stream with byte[].
	 */
	@JsonProperty("Audio")
	public byte[] getAudio() {
		return this.Audio;
	}

	/**
	 * Sets the audio byte array of the stream
	 * 
	 * @param audio
	 *            The byte array to set
	 */
	public void setAudio(byte[] audio) {
		this.Audio = audio;
	}
}
