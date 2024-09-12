package com.fastcampus.ch4.controller.notice;

import com.fastcampus.ch4.domain.notice.PageHandler;
import com.fastcampus.ch4.dto.notice.NoticeDto;
import com.fastcampus.ch4.service.notice.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("cscenter/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue="ALL") String cateCode, @RequestParam(defaultValue="1") Integer page,
                       @RequestParam(defaultValue="10") Integer pageSize, Model model, HttpServletRequest request) {

        int totalCnt = noticeService.countDsply();
        model.addAttribute("totalCnt", totalCnt);

        PageHandler pageHandler = new PageHandler(page, pageSize, totalCnt);

        if(page < 0 || page > pageHandler.getTotalPage())
            page = 1;
        if(pageSize < 0 || pageSize > 50)
            pageSize = 10;

        Map map = new HashMap();
        map.put("offset", (page-1)*pageSize);
        map.put("pageSize", pageSize);
        map.put("cateCode", cateCode);

        HttpSession session = request.getSession(false);
        String userId = session == null ? null : (String) session.getAttribute("id");

        List<NoticeDto> list = noticeService.getPage(userId, map);
        model.addAttribute("notices", list);
        model.addAttribute("ph", pageHandler);
        model.addAttribute("cateCode", cateCode);

        return "notice/list";
    }

    @GetMapping("/detail/{ntcSeq}")
    public String detail(@PathVariable("ntcSeq") Integer ntcSeq, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String userId = session == null ? null : (String) session.getAttribute("id");

        NoticeDto noticeDto = noticeService.read(userId, ntcSeq);
        model.addAttribute("notice", noticeDto);

        return "notice/detail";
    }
}