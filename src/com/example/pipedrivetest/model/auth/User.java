
package com.example.pipedrivetest.model.auth;

public class User{
   	private Locale locale;
   	private Profile profile;
   	private Timezone timezone;

 	public Locale getLocale(){
		return this.locale;
	}
	public void setLocale(Locale locale){
		this.locale = locale;
	}
 	public Profile getProfile(){
		return this.profile;
	}
	public void setProfile(Profile profile){
		this.profile = profile;
	}
 	public Timezone getTimezone(){
		return this.timezone;
	}
	public void setTimezone(Timezone timezone){
		this.timezone = timezone;
	}
}
