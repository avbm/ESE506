/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService;

/**
 * Class to contain a set of constant strings related to RelayService
 */
public final class RelayServiceConst {

	/**
	 * Specifies a signature for the REST methods that manage endpoints.
	 */
	public final static String EndPointSignature = "Endpoint";

	/**
	 * Specifies a signature for the REST methods that manage groups.
	 */
	public final static String GroupSignature = "Group";

	/**
	 * Query string key for name.
	 */
	public final static String NameKey = "name";

	/**
	 * Query string key for timeout.
	 */
	public final static String TtlKey = "ttl";

	/**
	 * Query string key for to.
	 */
	public final static String ToKey = "to";

	/**
	 * Query string key for filter.
	 */
	public final static String FilterKey = "filter";

	/**
	 * Query string key for wait.
	 */
	public final static String WaitKey = "wait";

	/**
	 * Format string for converting seconds to TimeSpan string The format is:
	 * {days}.{hours}:{minutes}:{seconds}
	 */
	private final static String TimeSpanFormatString = "%s.%s:%s:%s";

	/**
	 * Convert seconds to time span (format 00.00:00:00)
	 * 
	 * @param seconds
	 *            the seconds
	 * @return String Returns time span with string format.
	 */
	public final static String ConvertSecondsToTimespan(int seconds) {
		boolean isMinus = seconds < 0;
		if (isMinus) {
			seconds = -1 * seconds;
		}
		int hours = (int) seconds / 3600;
		int days = hours / 24;
		if (days > 0) {
			hours = hours - days * 24;
		}

		String formatString = TimeSpanFormatString;
		if (isMinus) {
			formatString = "-" + formatString;
		}

		return String.format(formatString, days, hours, (seconds % 3600) / 60,
				seconds % 60);
	}
}
