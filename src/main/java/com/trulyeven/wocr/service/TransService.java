package com.trulyeven.wocr.service;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class TransService {

	/**
	 * 
	 * @return
	 */
	public String tessOCR() {
		File imageFile = new File("C:/Users/g_yun/Desktop/example.png"); // 이미지파일 경로
		ITesseract instance = new Tesseract();
		instance.setLanguage("kor+eng");  // OCR 한글 영어 인식
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // tessdata directory 경로

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
            return result;
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
		return null;
	}
    
	/**
	 */
	public void screenshot(String url) {
	    
		System.getProperty("webdriver.edge.driver", "C:\\worktool\\msedgedriver.exe");
		WebDriver driver = new EdgeDriver();
		driver.get(url);
		driver.quit();
		
	}
}
