package shared;

import java.util.HashMap;
import java.util.Iterator;

/**
 * This is a utility class that provides a query builder
 * 
 * @author fieldsruthie
 *
 */
public class QueryStringBuilder {

	/**
	 * 
	 * @param params a String-String map of all the params
	 * @return
	 */
	public static String createQueryString(HashMap<String, String> params) {
		StringBuilder urlBuilder = new StringBuilder();
		if (params.size() > 0) {
			// if there are parameters, begin with a question mark
			urlBuilder.append("?");
			Iterator<String> i = params.keySet().iterator();
			// for each entry, add to the query string
			while (i.hasNext()) {
				String key = i.next();
				urlBuilder.append(key).append("=").append(params.get(key));
				// only add ampersand if there will be more on the string
				if (i.hasNext()) {
					urlBuilder.append("&");
				}
			}
		}
		return urlBuilder.toString();
	}
}
