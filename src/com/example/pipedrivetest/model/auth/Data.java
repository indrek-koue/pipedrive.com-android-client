
package com.example.pipedrivetest.model.auth;

public class Data{
   	private String add_time;
   	private String api_token;
   	private Company company;
   	private Number company_id;
   	private Number user_id;

 	public String getAdd_time(){
		return this.add_time;
	}
	public void setAdd_time(String add_time){
		this.add_time = add_time;
	}
 	public String getApi_token(){
		return this.api_token;
	}
	public void setApi_token(String api_token){
		this.api_token = api_token;
	}
 	public Company getCompany(){
		return this.company;
	}
	public void setCompany(Company company){
		this.company = company;
	}
 	public Number getCompany_id(){
		return this.company_id;
	}
	public void setCompany_id(Number company_id){
		this.company_id = company_id;
	}
 	public Number getUser_id(){
		return this.user_id;
	}
	public void setUser_id(Number user_id){
		this.user_id = user_id;
	}
}
