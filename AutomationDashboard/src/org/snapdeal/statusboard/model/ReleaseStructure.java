package org.snapdeal.statusboard.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReleaseStructure {

	String releaseName;
	List<String> modules;
	List<String> platforms;
	
	public ReleaseStructure() {
	}
	
	public String getReleaseName() {
		return releaseName;
	}
	
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	
	public List<String> getModules() {
		return modules;
	}
	
	public void setModules(List<String> modules) {
		this.modules = modules;
	}
	
	public List<String> getPlatforms() {
		return platforms;
	}
	
	public void setPlatforms(List<String> platforms) {
		this.platforms = platforms;
	}

}
