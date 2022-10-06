package br.com.alterdata.core.installation;

import java.io.File;

public final class Distribution {

	private String name;
	private File distributionZip;

	public Distribution(String name, File distributionZip) {
		this.name = name;
		this.distributionZip = distributionZip;
	}

	public String getName() {
		return name;
	}
	public File getDistributionZip() {
		return distributionZip;
	}

	@Override
	public String toString() {
		return getName();
	}

}
