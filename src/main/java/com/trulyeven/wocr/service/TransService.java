package com.trulyeven.wocr.service;

import com.trulyeven.wocr.domain.VideoInfo;

public interface TransService {
	
	String tessOCR();
	
	void screenShot();
	
	void delImage();
	
	void setDriver(String videoCode);
	
	void setYoutubeTime(double currentTime, String youtubeid);

	void setIsPlaying(boolean isPlaying, VideoInfo videoinfo);
	
	void quitDriver();

	void setYoutube(VideoInfo videoinfo);

	String translateText(String text);
	
	Boolean drivercheck();
}
