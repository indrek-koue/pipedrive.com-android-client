
package com.example.pipedrivetest.model;

import java.util.List;

public class Im{
   	private boolean primary;
   	private String value;

 	public boolean getPrimary(){
		return this.primary;
	}
	public void setPrimary(boolean primary){
		this.primary = primary;
	}
 	public String getValue(){
		return this.value;
	}
	public void setValue(String value){
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value+"; ";
	}
}
