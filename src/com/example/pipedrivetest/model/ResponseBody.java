
package com.example.pipedrivetest.model;

import java.util.List;

public class ResponseBody{
   	private Additional_data additional_data;
   	private List<Data> data;
   	private boolean success;
   	private String error;

 	public Additional_data getAdditional_data(){
		return this.additional_data;
	}
	public void setAdditional_data(Additional_data additional_data){
		this.additional_data = additional_data;
	}
 	public List<Data> getData(){
		return this.data;
	}
	public void setData(List<Data> data){
		this.data = data;
	}
 	public boolean getSuccess(){
		return this.success;
	}
	public void setSuccess(boolean success){
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
}
