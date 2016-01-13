package edu.kaist.tgm.ko.rest;

public class ErrorType {
	private boolean isValid;
	private String type;
	
	public ErrorType(boolean isValid, String type){
		this.isValid = isValid;
		this.type = type;
	}

	public boolean isValid() {
		return isValid;
	}

	public String getType() {
		return type;
	}

}
