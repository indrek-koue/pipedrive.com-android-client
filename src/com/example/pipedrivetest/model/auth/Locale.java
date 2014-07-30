
package com.example.pipedrivetest.model.auth;

public class Locale{
   	private String country;
   	private String language;
   	private boolean uses_12_hour_clock;

 	public String getCountry(){
		return this.country;
	}
	public void setCountry(String country){
		this.country = country;
	}
 	public String getLanguage(){
		return this.language;
	}
	public void setLanguage(String language){
		this.language = language;
	}
 	public boolean getUses_12_hour_clock(){
		return this.uses_12_hour_clock;
	}
	public void setUses_12_hour_clock(boolean uses_12_hour_clock){
		this.uses_12_hour_clock = uses_12_hour_clock;
	}
}
