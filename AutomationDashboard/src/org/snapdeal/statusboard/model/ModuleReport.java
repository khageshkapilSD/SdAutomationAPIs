package org.snapdeal.statusboard.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ModuleReport {
	
	String releaseName;
	String moduleName;
	String platform;
	String total;
	String passed;
	String failed;
	String heading1;
	String heading2;
	String report;
	
	public ModuleReport() {}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPassed() {
		return passed;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	public String getFailed() {
		return failed;
	}

	public void setFailed(String failed) {
		this.failed = failed;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public String getHeading1() {
		return heading1;
	}

	public void setHeading1(String heading1) {
		this.heading1 = heading1;
	}

	public String getHeading2() {
		return heading2;
	}

	public void setHeading2(String heading2) {
		this.heading2 = heading2;
	}

	
}
