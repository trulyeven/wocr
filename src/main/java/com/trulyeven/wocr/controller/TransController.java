package com.trulyeven.wocr.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.util.Value;
import com.trulyeven.wocr.domain.VideoInfo;
import com.trulyeven.wocr.service.TransService;


@Controller
public class TransController {
	@Autowired 
	TransService service;
	
	@Value("${youtube.api.key}")
	String youtubeApiKey;
	
	VideoInfo videoinfo = new VideoInfo(); // VideoInfo 객체 인스턴스화
	
	@PostMapping("/trans")
	public String transPost(String url, Model model) {
		// 비디오 코드를 URL에서 추출하기 위한 정규식 패턴
        Pattern pattern = Pattern.compile("(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*");
        Matcher matcher = pattern.matcher(url);
        
        String videoCode = "";
        
        if (matcher.find()) {
            videoCode = matcher.group();
        }
        videoinfo.setVideoId(videoCode);
		model.addAttribute("url", url);
		model.addAttribute("videoCode", videoCode);
		
		service.setDriver(videoCode);
		return "trans";
	}
	
//	@Scheduled(fixedDelay = 1500)  // 1.5초마다 실행
	@GetMapping("/start-OCR")
	public ResponseEntity<String> startOCR() {
		
		boolean drivercheck = service.drivercheck();
		if (drivercheck == false) {
			service.setDriver(videoinfo.getVideoId());
		}
		
	    service.screenShot();  // 스크린샷
	    String result = service.tessOCR();  // OCR 실행
	    service.delImage();  // 이미지파일 제거
		
	    return ResponseEntity.ok(result);
	}
	
	
	@GetMapping("/stop-OCR")
	public String stopOCR() {
		service.quitDriver();
		String videoCode = videoinfo.getVideoId();
		service.setDriver(videoCode);
		return "redirect:/";
	}
	
	
	@PostMapping("/yotubeTime")
	public ResponseEntity<String> youtubeTime(@RequestParam("currentTime") double currentTime) {
        service.setYoutubeTime(currentTime, videoinfo);
        return ResponseEntity.ok("Current time received and processed");
	}
	
	@PostMapping("/yotubePlaying")
	public ResponseEntity<String> youtubePlaying(@RequestParam("isplaying") boolean isplaying) {
		service.setIsPlaying(isplaying, videoinfo);
		return ResponseEntity.ok("Current time received and processed");
	}
	
	
}
