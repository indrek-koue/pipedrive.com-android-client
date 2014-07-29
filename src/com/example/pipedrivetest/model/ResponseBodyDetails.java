package com.example.pipedrivetest.model;

import java.util.List;

public class ResponseBodyDetails extends BaseResponse {
	private Data data;

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
