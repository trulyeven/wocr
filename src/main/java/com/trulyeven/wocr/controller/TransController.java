package com.trulyeven.wocr.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
		
		Pattern pattern = Pattern.compile("^.*(?:(?:youtu\\.be\\/|v\\/|vi\\/|u\\/\\w\\/|embed\\/)|(?:(?:watch)?\\?v(?:i)?=|\\&v(?:i)?=))([^#\\&\\?]*).*");
		Matcher matcher = pattern.matcher(url);

		String videoCode = "";
		
        if (matcher.find()) {
            videoCode = matcher.group();
        }
		
		service.screenShot();  // 스크린샷
//		service.tessOCR();
		String result = service.tessOCR();  // OCR 실행
//		service.delImage();  // 이미지파일 제거
		
		model.addAttribute("url", url);
		model.addAttribute("videoCode", videoCode);
		model.addAttribute("result", result);
		return "trans";
	}
	
	
	@PostMapping("/start-OCR")
	public void startOCR() {
	}
	
	@PostMapping("/stop-OCR")
	public void stopOCR() {
	}
	
	
	
	
//	@Scheduled(fixedDelay = 5000)  // 5초마다 실행
//	public void repeatOCR(String url, Model model) {
//		
//	}
}