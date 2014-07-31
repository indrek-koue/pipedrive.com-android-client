package com.example.pipedrivetest.model;

import java.util.ArrayList;
import java.util.List;

public class ResponseBody extends BaseResponse {
	private ArrayList<Data> data;

	public ArrayList<Data> getData() {
		return this.data;
	}

	public void setData(ArrayList<Data> data) {
		this.data = data;
	}

}
