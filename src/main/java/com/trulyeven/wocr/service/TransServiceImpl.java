package com.trulyeven.wocr.service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Slf4j
@Service
public class TransServiceImpl implements TransService {
	
	private WebDriver driver; // 인스턴스 변수로 선언

	
    public void setDriver(String url) {
    	System.getProperty("webdriver.edge.driver", "C:\\worktool\\msedgedriver.exe");  // 웹드라이버 파일 경로
        EdgeOptions options = new EdgeOptions();
        options.addArguments("start-maximized");
//        options.addArguments("headless");
//        options.addArguments("debuggerAddress=localhost:9999");
//        options.addArguments("no-sandbox");
        driver = new EdgeDriver(options); // 인스턴스 변수에 WebDriver 객체 할당
        
        driver.get("http://localhost:9999/wocr/");
        
        // input 태그 선택
        WebElement inputElement = driver.findElement(By.id("url"));
        // 텍스트 입력
        inputElement.sendKeys(url);
        inputElement.submit();
        
     // JavaScript 실행
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        boolean isSelenium = (boolean) jsExecutor.executeScript("return window.Selenium !== undefined;");
        
        // 결과 출력
        if (isSelenium) {
            System.out.println("Edge 페이지는 Selenium으로 작동 중입니다.");
        } else {
            System.out.println("Edge 페이지는 Selenium으로 작동 중이 아닙니다.");
        }
    }
    
	/**
	 * 
	 */
	@Override
	public void screenShot() {
		
		WebElement searchBar = driver.findElement(By.tagName("body"));
		
		File file= searchBar.getScreenshotAs(OutputType.FILE);
		
		try {
			String filePath = "src/main/resources/static/image/";
			FileUtils.copyFile(file, new File(filePath + "OCR.png"));
			
		} catch (Exception e) {
			log.debug("selenium screenshot error");
		}
		
	}
	
	@Override
	public void quitDriver() {
		driver.quit();
	}

    
	/**
	 * 
	 * @return
	 */
	@Override
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
	 * 
	 */
	@Override
	public void delImage() {
		String filePath = "src/main/resources/static/image/OCR.png"; // 삭제할 PNG 파일 경로

		new Thread(() -> {
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

	public void youtubeApi() {

		try {
			// YouTube API 초기화
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, null)
                    .setApplicationName("YOUR_APPLICATION_NAME")
                    .build();
					
					// YouTube API 요청 예시
					YouTube.Videos.List videoRequest = youtube.videos().list("snippet,contentDetails,statistics");
            videoRequest.setKey(API_KEY);
            videoRequest.setId("VIDEO_ID");
            VideoListResponse response = videoRequest.execute();
			
            System.out.println(response);
        } catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
        }
	}

// 웹페이지 클릭연동
//	@Override
//	public void webClick(int x, int y) {
//		Actions actions = new Actions(driver);
//		actions.moveByOffset(x, y).click().perform();
//	}
		
}
