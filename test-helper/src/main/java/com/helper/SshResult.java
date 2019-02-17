package com.helper;

public class SshResult {
	
	private int exitStatus;
	private String output;
	
	public SshResult(int exitStatus, String output){
		this.exitStatus = exitStatus;
		this.output = output;
	}

	public int getExitStatus() {
		return exitStatus;
	}

	public void setExitStatus(int exitStatus) {
		this.exitStatus = exitStatus;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
}
