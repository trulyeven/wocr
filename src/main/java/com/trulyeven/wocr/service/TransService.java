package com.trulyeven.wocr.service;

import com.trulyeven.wocr.domain.VideoInfo;

public interface TransService {
	
	String tessOCR();
	
	void screenShot();
	
	void delImage();
	
	void setDriver(String videoCode);
	
	void setYoutubeTime(double currentTime);

	void setIsPlaying(boolean isPlaying);
	
	void quitDriver();

	void setYoutube(VideoInfo videoinfo);

	void googletrans();
//	void webClick(int x, int y);  // 웹페이지 클릭연동


	
	
}
