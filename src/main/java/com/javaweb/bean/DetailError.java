package com.javaweb.bean;

import java.util.ArrayList;
import java.util.List;

public class DetailError {
	private String nameError;
	private List<String> detailError = new ArrayList<String>();
	

	public DetailError() {

	}
	
	public String getNameError() {
		return nameError;
	}
	public void setNameError(String nameError) {
		this.nameError = nameError;
	}
	public List<String> getDetailError() {
		return detailError;
	}
	public void setDetailError(List<String> detailError) {
		this.detailError = detailError;
	}
	
	
}
