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

	
	private String transCode;

	
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
        
        if (matcher.find()) {
            videoCode = matcher.group();
        }
        transCode = videoCode;
        
		model.addAttribute("url", url);
		model.addAttribute("videoCode", videoCode);
		
		return "trans";
	}
	

	@GetMapping("/start-OCR")
	public ResponseEntity<String> startOCR() {
		service.setDriver(transCode);
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
	
	
	@PostMapping("/yotubeTime")
	public ResponseEntity<String> youtubeTime(@RequestParam("currentTime") double currentTime, VideoInfo videoinfo) {
		videoinfo.setCurrentTime(currentTime);
		System.out.println(videoinfo.getCurrentTime());
		return ResponseEntity.ok("Current time received and processed");
	}
	
	@PostMapping("/yotubePlaying")
	public ResponseEntity<String> youtubePlaying(@RequestParam("isplaying") boolean isplaying, VideoInfo videoinfo) {
		videoinfo.setIsplaying(isplaying);
		return ResponseEntity.ok("Current time received and processed");
	}
	
	
//	// 동영상 정보를 가져오는 컨트롤러 메소드
//	@GetMapping("/getVideo")
//	@ResponseBody
//	public VideoInfo getVideoInfo() {
//	    // YouTube 동영상 정보를 가져오는 로직
//	    // VideoInfo 객체에 동영상 정보를 설정하여 반환
//	    VideoInfo videoInfo = new VideoInfo();
//	    videoInfo.setVideoId("동영상 아이디");
//	    videoInfo.setVideoUrl("동영상 URL");
//	    // 동영상 정보에는 재생 시간 등의 필요한 정보를 포함할 수 있습니다.
//	    
//	    return videoInfo;
//	}
//
//	// 클라이언트로부터 현재 재생 시간을 받는 컨트롤러 메소드
//	@PostMapping("/updateCurrentTime")
//	@ResponseBody
//	public void updateCurrentTime(@RequestParam("currentTime") double currentTime) {
//	    // 클라이언트로부터 전달받은 현재 재생 시간을 처리하는 로직
//	    // 해당 동영상의 현재 재생 시간을 업데이트하거나 필요한 작업을 수행
//		System.out.println(currentTime);
//	}
	
//	
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
