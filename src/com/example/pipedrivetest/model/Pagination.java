
package com.example.pipedrivetest.model;

import java.util.List;

public class Pagination{
   	private Number limit;
   	private boolean more_items_in_collection;
   	private Number start;

 	public Number getLimit(){
		return this.limit;
	}
	public void setLimit(Number limit){
		this.limit = limit;
	}
 	public boolean getMore_items_in_collection(){
		return this.more_items_in_collection;
	}
	public void setMore_items_in_collection(boolean more_items_in_collection){
		this.more_items_in_collection = more_items_in_collection;
	}
 	public Number getStart(){
		return this.start;
	}
	public void setStart(Number start){
		this.start = start;
	}
}
