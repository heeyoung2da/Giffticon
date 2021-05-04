package com.ncncn.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.ncncn.domain.UserVO;
import com.ncncn.service.SignUpService;
import com.ncncn.service.SignUpServiceImpl;
import com.ncncn.util.EmailAuthCodeUtils;
import lombok.extern.log4j.Log4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account/*")
@Log4j
public class AccountController {

    private SignUpService signUpService;
    private JavaMailSender javaMailSender;

    public AccountController(SignUpService signUpService, JavaMailSender javaMailSender) {
        this.signUpService = signUpService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping("/signIn")
    public void signIn(HttpServletRequest request, String error, Model model) {

        model = cookieChecker(request, model);

        //로그인 실패 시
        if (error != null) {
            model.addAttribute("msg", "이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        //직접 logout 해서 로그인 창으로 왔을 시
        if (request.getSession().getAttribute("logout") != null) {
            model.addAttribute("msg", "로그아웃되었습니다.");
            request.getSession().removeAttribute("logout");
        }

        request.getSession().setAttribute("referer", request.getHeader("referer"));
    }

    @GetMapping("/signUp")
    public String getSignUp() {
        return "/account/signUp";
    }

    // 회원 등록 요청
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> register(@RequestBody UserVO user) {
        try {
            // 사용자 등록
            int result = signUpService.register(user);
        } catch (Exception e) {
            // 등록 실패사유를 응답에 담아 전송
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    // 이메일 중복확인
    @GetMapping(value = "/checkExists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> checkExists(@RequestParam("email") String email) {
        int isExists = 0;
        try {
            UserVO userVO = signUpService.getByEmail(email);
            if (userVO != null) isExists = 1;                   // 해당 이메일을 가진 사용자가 존재하면 1
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(isExists, HttpStatus.OK);   // 존재하지않으면 0 반환
    }

    // 사용자가 입력한 이메일로 인증메일 전송
    @GetMapping(value = "/emailConfirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> confirmEmail(@RequestParam("email") String email) {
        Map<String, String> map = new HashMap<>();

        String code = EmailAuthCodeUtils.getAuthCode();         // 인증코드 생성
        SimpleMailMessage message = new SimpleMailMessage();

        try {
            message.setTo(email);
            message.setSubject("기쁘티콘 회원가입 이메일 인증");
            message.setText("인증 코드: " + code);

            javaMailSender.send(message);           // 생성한 메일내용 전송
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        map.put("code", code);                      // 인증메일 전송에 성공하면 map에 인증코드를 담아 전달
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    public Model cookieChecker(HttpServletRequest request, Model model) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("remEmail")) {
                    model.addAttribute("email", cookies[i].getValue());
                    model.addAttribute("isRemember", "checked");
                }
            }
        }
        return model;
    }
}
