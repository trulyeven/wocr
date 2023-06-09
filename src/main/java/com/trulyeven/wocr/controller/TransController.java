package com.trulyeven.wocr.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import net.sourceforge.tess4j.*;

@Controller
public class TransController {

	@GetMapping("trans")
	public String trans() {
		
		File imageFile = new File("C:/Users/g_yun/Desktop/sample.png");
		ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setDatapath("C:/Program Files/Tesseract-OCR/tessdata"); // path to tessdata directory

        try {
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        
		return "trans";
	}
}
