package com.trulyeven.wocr.service;

import com.trulyeven.wocr.domain.VideoInfo;

public interface TransService {
	
	String tessOCR();
	
	void screenShot();
	
	void delImage();
	
	void setDriver(String videoCode);
	
	void setYoutubeTime(double currentTime, VideoInfo videoInfo);

	void setIsPlaying(boolean isPlaying);
	
	void quitDriver();

	void setYoutube(VideoInfo videoinfo);

	String translateText(String text);
	
	Boolean drivercheck();
}
