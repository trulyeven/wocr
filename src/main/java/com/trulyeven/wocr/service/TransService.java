package com.trulyeven.wocr.service;

public interface TransService {
	
	String tessOCR();
	
	void screenShot();
	
	void delImage();
	
	void setDriver(String url);
	
	void quitDriver();

//	void webClick(int x, int y);  // 웹페이지 클릭연동


	
	
}
