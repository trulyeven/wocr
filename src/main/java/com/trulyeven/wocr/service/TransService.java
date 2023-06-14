package com.trulyeven.wocr.service;

public interface TransService {
	
	String tessOCR();
	
	void screenShot();
	
	void delImage();
	
	void setDriver(String url);
	
	void quitDriver();

	
	
}
