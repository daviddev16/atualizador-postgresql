package br.com.alterdata.core;

import java.io.File;
import java.util.Map.Entry;

public class MemoryChecker {

	public static final double PG_SIZE_BIN = 673857536;
	
	public static final double TO_GB = 1073741824;
	
	public static void printMem(String disk) {

		File c = new File(disk);
		double free = c.getFreeSpace() / TO_GB;
		double total = c.getTotalSpace() / TO_GB;
		double usable = c.getUsableSpace() / TO_GB;
		
		System.out.println(String.format("Total space: %.2f GB/%.2f GB", usable, total) );
		
		double totaldbs = 0;
		
		for(Entry<String, Double> dbs : Metrics.databaseSize.entrySet()) {
			totaldbs += dbs.getValue() / TO_GB;
		}
		
		totaldbs += PG_SIZE_BIN / TO_GB;
		
		System.out.println( String.format("Tamanho necessário minimo: %.2f GB", totaldbs)); 

		System.out.println(String.format("Tamanho pg bin: %.2f GB",  (PG_SIZE_BIN/TO_GB) ));
	}
	
	public static void main(String[] args) {
		printMem("C:\\");
	}
	
}
