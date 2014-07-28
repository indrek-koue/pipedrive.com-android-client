
package com.example.pipedrivetest.model.auth;

import java.util.List;

public class Timezone{
   	private String name;
   	private Number offset;

 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public Number getOffset(){
		return this.offset;
	}
	public void setOffset(Number offset){
		this.offset = offset;
	}
}
