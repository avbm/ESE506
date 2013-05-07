/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.translatorspeech;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.media.MediaPlayer;
import android.media.MediaRecorder;

/**
 *
 */
public final class MediaUtility {

	private final static String TEMP_PLAYBACKFILE_PATH_PREFIX = "playertemp";
	private final static String RECORDER_PLAYBACKFILE_PATH_PREFIX = "recordertemp";

	private static File tempAudioFileForRecorder = null;

	public static byte[] getTempAudioFileContent() {
		if (tempAudioFileForRecorder == null) {
			return null;
		}

		byte[] buffer = null;
		try {
			FileInputStream out = new FileInputStream(tempAudioFileForRecorder);
			BufferedInputStream bis = null;
			try {
				bis = new BufferedInputStream(out);
				buffer = new byte[bis.available()];
				bis.read(buffer);
			} finally {
				if (bis != null) {
					bis.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer;
	}

	/**
	 * create MediaRecorder to record audio
	 * 
	 * @return MediaRecorder
	 */
	public static MediaRecorder createMediaRecorder() {
		MediaRecorder recorder = null;
		try {
			recorder = new MediaRecorder();
			tempAudioFileForRecorder = File.createTempFile(
					RECORDER_PLAYBACKFILE_PATH_PREFIX, "temp");
			recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

			// recorder.setMaxDuration(500);
			recorder.setAudioChannels(1);
			recorder.setAudioSamplingRate(8000);
			recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			recorder.setOutputFile(tempAudioFileForRecorder.getAbsolutePath());
			recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

			recorder.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return recorder;
	}

	/**
	 * release specified MediaRecorder object
	 * 
	 * @param recorder
	 *            specified MediaRecorder object
	 */
	public static void releaseMediaRecorder(MediaRecorder recorder) {
		if (recorder != null) {
			recorder.release();
		}
	}

	/**
	 * create MediaPlayer object to playback the specified audio stream
	 * 
	 * @param stream
	 *            specified audio stream
	 * @return MediaPlayer
	 */
	public static MediaPlayer createMediaPlayer(InputStream stream) {
		MediaPlayer mediaplayer = null;
		try {
			File temp = File.createTempFile(TEMP_PLAYBACKFILE_PATH_PREFIX,
					"temp");
			String tempPath = temp.getAbsolutePath();
			FileOutputStream out = new FileOutputStream(temp);
			BufferedOutputStream bis = null;
			try {
				bis = new BufferedOutputStream(out);
				byte buf[] = new byte[128];
				do {
					int numread = stream.read(buf);
					if (numread <= 0)
						break;
					bis.write(buf, 0, numread);
				} while (true);
			} finally {
				if (bis != null) {
					bis.close();
				}
			}

			MediaPlayer mp = new MediaPlayer();
			mp.setDataSource(tempPath);
			mp.prepare();
			mediaplayer = mp;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mediaplayer;
	}

	/**
	 * release specified MediaPlayer object
	 * 
	 * @param player
	 *            specified MediaPlayer object
	 */
	public static void releaseMediaPlayer(MediaPlayer player) {
		if (player == null) {
			return;
		}

		if (player.isPlaying()) {
			player.stop();
		}

		player.release();
	}
}
