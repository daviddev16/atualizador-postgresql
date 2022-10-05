package br.com.alterdata.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Metrics {

	public static Map<String, Double> databaseSize = Collections.synchronizedMap(new HashMap<String, Double>());
	
}
