package com.trulyeven.wocr.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.sourceforge.tess4j.*;

@Controller
public class TransController {

	@GetMapping("trans")
	public String trans() {
		
		File imageFile = new File("C:/Users/g_yun/Desktop/example.png"); // 이미지파일 경로
		ITesseract instance = new Tesseract();
		instance.setLanguage("kor+eng");  // OCR 한글 영어 인식
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // tessdata directory 경로

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        
		return "trans";
	}
}
