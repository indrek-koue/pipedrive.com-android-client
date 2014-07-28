
package com.example.pipedrivetest.model.auth;

import java.util.List;

public class Company{
   	private List<Features> features;
   	private Info info;
   	private Settings settings;

 	public List<Features> getFeatures(){
		return this.features;
	}
	public void setFeatures(List<Features> features){
		this.features = features;
	}
 	public Info getInfo(){
		return this.info;
	}
	public void setInfo(Info info){
		this.info = info;
	}
 	public Settings getSettings(){
		return this.settings;
	}
	public void setSettings(Settings settings){
		this.settings = settings;
	}
}
