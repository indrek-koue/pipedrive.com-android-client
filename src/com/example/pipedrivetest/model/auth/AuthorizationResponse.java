package com.example.pipedrivetest.model.auth;

import java.util.List;

import com.example.pipedrivetest.model.BaseResponse;

public class AuthorizationResponse extends BaseResponse {
	private List<Data> data;

	public List<Data> getData() {
		return this.data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

}
