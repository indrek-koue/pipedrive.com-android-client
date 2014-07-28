
package com.example.pipedrivetest.model.auth;

import java.util.List;

public class Profile{
   	private boolean activated;
   	private String default_currency;
   	private String email;
   	private String icon_url;
   	private Number id;
   	private boolean is_admin;
   	private String name;

 	public boolean getActivated(){
		return this.activated;
	}
	public void setActivated(boolean activated){
		this.activated = activated;
	}
 	public String getDefault_currency(){
		return this.default_currency;
	}
	public void setDefault_currency(String default_currency){
		this.default_currency = default_currency;
	}
 	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
 	public String getIcon_url(){
		return this.icon_url;
	}
	public void setIcon_url(String icon_url){
		this.icon_url = icon_url;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public boolean getIs_admin(){
		return this.is_admin;
	}
	public void setIs_admin(boolean is_admin){
		this.is_admin = is_admin;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
}
