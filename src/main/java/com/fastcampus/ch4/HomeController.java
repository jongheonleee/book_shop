package com.fastcampus.ch4;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public String showHomePage(Model model) {

		// 현재 서버 시간 가져오기
		LocalDateTime now = LocalDateTime.now();
		// 시간을 문자열로 포맷팅
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedNow = now.format(formatter);
		// 모델에 시간 추가
		model.addAttribute("serverTime", formattedNow);
		return "home"; // home.jsp를 반환


	}
}
