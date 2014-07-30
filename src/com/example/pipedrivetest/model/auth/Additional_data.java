
package com.example.pipedrivetest.model.auth;

public class Additional_data{
   	private Number default_company_id;
   	private boolean multiple_companies;
   	private User user;

 	public Number getDefault_company_id(){
		return this.default_company_id;
	}
	public void setDefault_company_id(Number default_company_id){
		this.default_company_id = default_company_id;
	}
 	public boolean getMultiple_companies(){
		return this.multiple_companies;
	}
	public void setMultiple_companies(boolean multiple_companies){
		this.multiple_companies = multiple_companies;
	}
 	public User getUser(){
		return this.user;
	}
	public void setUser(User user){
		this.user = user;
	}
}
