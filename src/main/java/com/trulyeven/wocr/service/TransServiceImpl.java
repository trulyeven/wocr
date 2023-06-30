package com.trulyeven.wocr.service;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.stereotype.Service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.trulyeven.wocr.domain.VideoInfo;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Slf4j
@Service
public class TransServiceImpl implements TransService {
	
	
	private WebDriver driver; // 인스턴스 변수로 선언
	private VideoInfo videoinfo;
	private Translate translate;
	
	
	public TransServiceImpl() {
		this.driver = new EdgeDriver(); // driver 객체 생성
        this.videoinfo = new VideoInfo(); // videoinfo 객체 생성
    }
	
	/**
	 * 
	 */
	@Override
    public void setDriver(String videocode) {
    	System.getProperty("webdriver.edge.driver", "C:\\worktool\\msedgedriver.exe");  // 웹드라이버 파일 경로
        EdgeOptions options = new EdgeOptions();
        options.addArguments("start-maximized");
//        options.addArguments("headless");
//        options.addArguments("debuggerAddress=localhost:9999");
//        options.addArguments("no-sandbox");
        driver = new EdgeDriver(options); // 인스턴스 변수에 WebDriver 객체 할당
        try {
			driver.get("https://www.youtube.com/embed/" + videocode);
		} catch (Exception e) {
			driver.quit();
		}
        
        videoinfo.setVideoId(videocode);

     // JavaScript 실행
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        boolean isSelenium = (boolean) jsExecutor.executeScript("return window.Selenium !== undefined;");
        
        // 결과 출력
        if (isSelenium) {
            log.debug("Edge 페이지는 Selenium으로 작동 중입니다.");
        } else {
            log.debug("Edge 페이지는 Selenium으로 작동 중이 아닙니다.");
        }
    }
    
	/*
	 * 
	 */
	@Override
	public void setYoutubeTime(double currentTime) {
		String videocode = videoinfo.getVideoId();
		double playtime = currentTime;
		videoinfo.setCurrentTime(currentTime);
		
		driver.get("https://www.youtube.com/embed/" + videocode + "?start=" + playtime);
		
		
	}

	@Override
	public void setIsPlaying(boolean isPlaying) {
		boolean playtime = isPlaying;
		boolean preisplaying = videoinfo.isIsplaying();
		if (preisplaying != playtime) {
			WebElement element = driver.findElement(By.id("player"));
	        // 요소 클릭
	        element.click();
		}
		videoinfo.setIsplaying(isPlaying);
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

	
	@Override
	public void setYoutube(VideoInfo videoinfo) {
	    double youtubesec = videoinfo.getCurrentTime();
	    
	    // 유튜브 동영상 **초부터 시작하도록 설정
	    JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
	    String script = "var videoElement = document.querySelector('#video-player-id');"
	                    + "videoElement.seekTo(" + (youtubesec * 1000) + ");"; // 밀리초
	    jsExecutor.executeScript(script);

	    // 유튜브 동영상 재생
	    script = "var videoElement = document.querySelector('#video-player-id');"
	            + "videoElement.play();";
	    jsExecutor.executeScript(script);
	}
	
	
	@Override
	public String translateText(String text) {
		// Google Translate 초기화
        translate = TranslateOptions.getDefaultInstance().getService();
        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage("ko"));
        return translation.getTranslatedText();
    }
	
}
