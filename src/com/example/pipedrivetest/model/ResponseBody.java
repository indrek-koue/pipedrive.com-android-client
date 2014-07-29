package com.example.pipedrivetest.model;

import java.util.List;

public class ResponseBody extends BaseResponse {
	private List<Data> data;

	public List<Data> getData() {
		return this.data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

}
