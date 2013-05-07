/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService;

/**
 * Class to contain a set of const strings related to KeyValueService
 * 
 */
public final class KeyValueServiceConst {

	/**
	 * Specifies a signature for the REST methods that manage KeyValue item
	 */
	public final static String KeyValueSignature = "values";

	/**
	 * Specifies a query string key of prefix
	 */
	public final static String PrefixKey = "prefix";

	/**
	 * Specifies a query string key of prefix
	 */
	public final static String SizeKey = "size";

	/**
	 * Specifies a query string key of continuationToken.
	 */
	public final static String ContinuationTokenKey = "continuationToken";

	/**
	 * Specifies a query string key of batch operation.
	 */
	public final static String BatchOperationKey = "$batch";

	/**
	 * Specifies a query string key of timestamp before
	 */
	public final static String BeforeKey = "before";

}
