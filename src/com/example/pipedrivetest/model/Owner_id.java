
package com.example.pipedrivetest.model;

public class Owner_id{
   	private String email;
   	private boolean has_pic;
   	private Number id;
   	private String name;
   	private Number value;

 	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
 	public boolean getHas_pic(){
		return this.has_pic;
	}
	public void setHas_pic(boolean has_pic){
		this.has_pic = has_pic;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public Number getValue(){
		return this.value;
	}
	public void setValue(Number value){
		this.value = value;
	}
}
