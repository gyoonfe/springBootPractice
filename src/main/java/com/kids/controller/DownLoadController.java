package com.kids.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DownLoadController {

	@Value("${download.filepath}")
	String downFilePath;
	
	@Value("${download.filename}")
	String downFileName;
	
	@GetMapping("/download")
	public void download(HttpServletResponse response) {
		String filePath = downFilePath;
		String fileName = downFileName;
		
		response.setContentType("application/octer-stream");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
	
		try {
			File file = new File(filePath, fileName);
			OutputStream os = response.getOutputStream();
			FileInputStream fis = new FileInputStream(filePath + fileName);
			
			int count = 0;
			byte bytes[] = new byte[(int)file.length()];
			
			while( (count = fis.read(bytes)) != -1 ) {
				os.write(bytes, 0, count);
			}
			fis.close();
			os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
