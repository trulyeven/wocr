package com.trulyeven.wocr.service;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Slf4j
@Service
public class TransService {

	/**
	 * 
	 * @return
	 */
	public String tessOCR() {
		File imageFile = new File("src/main/resources/static/image/OCR.png"); // 이미지파일 경로

		ITesseract instance = new Tesseract();
		instance.setLanguage("kor+eng");  // OCR 한글 영어 인식
        instance.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // tessdata directory 경로

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
            return result;
        } catch (TesseractException e) {
            log.debug(e.getMessage());
        }
		return null;
	}
    
	/**
	 */
	public void screenShot() {
	    
		System.getProperty("webdriver.edge.driver", "C:\\worktool\\msedgedriver.exe");  // 웹드라이버 파일 경로
		WebDriver driver = new EdgeDriver();
		
		driver.get("http://localhost:9999/wocr/trans");

		WebElement searchBar = driver.findElement(By.tagName("body"));
		
		File file= searchBar.getScreenshotAs(OutputType.FILE);

		try {
			String filePath = "src/main/resources/static/image/";
			FileUtils.copyFile(file,new File(filePath + "OCR.png"));
			
		} catch (Exception e) {
			log.debug("selenium screenshot error");
		}finally {
			driver.quit();
		}
		
	}
	
	public void delImage() {
		String filePath = "src/main/resources/static/image/OCR.png"; // 삭제할 PNG 파일 경로

		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(2000);  // 2초 지연
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
        // 파일 객체 생성
        File file = new File(filePath);
        
        // 파일이 존재하는지 확인 후 삭제
        if (file.exists()) {
            if (file.delete()) {
                log.debug("PNG 파일이 삭제되었습니다.");
            } else {
            	log.debug("PNG 파일 삭제에 실패했습니다.");
            }
        } else {
            log.debug("삭제할 PNG 파일이 존재하지 않습니다.");
        }
	}
}
