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
import com.trulyeven.wocr.service.TransService;


@Controller
public class TransController {
	@Autowired 
	TransService service;
	
	@Value("${youtube.api.key}")
	String youtubeApiKey;

	private String transUrl;
	
	
	@GetMapping("/trans")
	public String trans(Model model) {
		
		String result = service.tessOCR();  // OCR 실행
		model.addAttribute("result", result);
		return "trans";
	}
	
	
	@PostMapping("/trans")
	public String transPost(String url, Model model) {
		// 비디오 코드를 URL에서 추출하기 위한 정규식 패턴
        Pattern pattern = Pattern.compile("(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*");
        Matcher matcher = pattern.matcher(url);
        
        String videoCode = "";
        
        @SuppressWarnings("unused")
		String result = null;
        
        if (matcher.find()) {
            videoCode = matcher.group();
        }
        
        transUrl = url;
        
		model.addAttribute("url", url);
		model.addAttribute("videoCode", videoCode);
		
		return "trans";
	}
	

	@GetMapping("/start-OCR")
	public ResponseEntity<String> startOCR() {
		service.setDriver(transUrl);
	    service.screenShot();  // 스크린샷
	    String result = service.tessOCR();  // OCR 실행
	    service.delImage();  // 이미지파일 제거
	    
	    return ResponseEntity.ok(result);
	}
	
	
	@GetMapping("/stop-OCR")
	public String stopOCR() {
		service.quitDriver();
		return "redirect:/";
	}
	
	
	 @PostMapping("/updatePlayerState")
	 public ResponseEntity<String> updatePlayerState(@RequestParam("playerState") int playerState) {
		 // 유튜브 재생 상태 업데이트 처리
		 // 처리 로직 구현

		 return ResponseEntity.ok("Player state updated successfully");
	 }
	
	
// 웹페이지 클릭연동
//	@PostMapping("/webClick")
//	public ResponseEntity<String> handleWebClick(@RequestParam int x, @RequestParam int y) {
//	    service.webClick(x, y);
//	    return ResponseEntity.ok("Click event received and processed by Spring");
//	}
	
	
}
	
//	@Scheduled(fixedDelay = 5000)  // 5초마다 실행
//	public void repeatOCR(String url, Model model) {
//		
//	}
