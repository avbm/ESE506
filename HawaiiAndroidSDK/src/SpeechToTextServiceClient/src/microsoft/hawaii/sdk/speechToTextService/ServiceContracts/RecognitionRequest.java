/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.speechToTextService.ServiceContracts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The request of Recognition service call.
 */
public class RecognitionRequest {

	/**
	 * Specifies a buffer containing the audio data to be translated to text.
	 * The audio buffer should have the following characteristics:
	 * 'SamplesPerSecond=16000', 'AudioBitsPerSample=16' and
	 * 'AudioChannel=Mono'.
	 */
	private byte[] speechBuffer;

	/**
	 * Initializes an instance of the {@link RecognitionRequest} class
	 */
	public RecognitionRequest() {
	}

	/**
	 * Gets the speechBuffer
	 * 
	 * @return byte[] Returns the speech buffer.
	 */
	@JsonProperty("SpeechBuffer")
	public byte[] getSpeechBuffer() {
		return this.speechBuffer;
	}

	/**
	 * Sets the SpeechBuffer
	 * 
	 * @param speechBuffer
	 *            the speech buffer
	 */
	public void setSpeechBuffer(byte[] speechBuffer) {
		this.speechBuffer = speechBuffer;
	}
}
