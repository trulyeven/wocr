package com.trulyeven.wocr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.trulyeven.wocr.service.TransService;

import lombok.extern.slf4j.Slf4j;

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
		
		service.screenShot();  // 스크린샷
		String result = service.tessOCR();  // OCR 실행
		service.delImage();  // 이미지파일 제거
	    
		model.addAttribute("url", url);
		model.addAttribute("result", result);
		
		return "trans";
	}
}
