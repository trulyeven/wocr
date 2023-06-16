package com.trulyeven.wocr.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@ToString
public class VideoInfo {
	// 동영상 정보를 담을 필드들
	private String videoId;
	private String videoUrl;
	private double currentTime;
	private boolean isplaying;
}
