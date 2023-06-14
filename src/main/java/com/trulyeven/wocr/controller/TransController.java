package com.trulyeven.wocr.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysql.cj.jdbc.Driver;
import com.trulyeven.wocr.service.TransService;


@Controller
public class TransController {
	@Autowired 
	TransService service;
	
	
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
        String result = null;
        
        if (matcher.find()) {
            videoCode = matcher.group();
        }
        
        service.setDriver(url);
        
		model.addAttribute("url", url);
		model.addAttribute("videoCode", videoCode);
		
		return "trans";
	}
	
	
//	@GetMapping("/start-OCR")
//	public String startOCR(Model model) {
//		service.screenShot();  // 스크린샷
//		String result = service.tessOCR();  // OCR 실행
//		service.delImage();  // 이미지파일 제거
//		
//		model.addAttribute("result", result);
//		return "redirect:/";
//	}

	@GetMapping("/start-OCR")
	public ResponseEntity<String> startOCR() {
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
	
	
	
	
//	@Scheduled(fixedDelay = 5000)  // 5초마다 실행
//	public void repeatOCR(String url, Model model) {
//		
//	}
}