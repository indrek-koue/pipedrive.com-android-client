package com.example.pipedrivetest.model;

public class Email {
	private String label;
	private boolean primary;
	private String value;

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean getPrimary() {
		return this.primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return label + " " + value + "; ";
	}
}
