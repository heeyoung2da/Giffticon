package com.ncncn.controller;

import com.ncncn.domain.BankAccountVO;
import com.ncncn.domain.CmmnCodeVO;
import com.ncncn.domain.UserInfoDTO;
import com.ncncn.domain.UserVO;
import com.ncncn.service.*;
import com.ncncn.util.UserAuthCheckUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Log4j
@RequestMapping("/user/mypage")
@AllArgsConstructor
public class UserInfoController {

    UserService userService;

    DealListService dealListService;

    SellListService sellListService;

    CmmnCodeService cmmnCodeService;

    PointService pointService;

    @GetMapping("/userInfo")
    public void userInfo(HttpServletRequest request, Model model) {

        int userId = (int) request.getSession().getAttribute("userId");

        UserInfoDTO user = userService.getMyInfo(userId);
        List<CmmnCodeVO> bnkList = cmmnCodeService.getBankList();

        log.info("map bnk:" + bnkList);

        model.addAttribute("bnkList", bnkList);
        model.addAttribute("user", user);

    }

    @RequestMapping(value = "/checkPassword",
            method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<String> checkPwdUserUpdate(@RequestBody UserVO checkuser,
                                                     HttpServletRequest request) {

        int userId = (int) request.getSession().getAttribute("userId");

        UserVO user = userService.readbyId(userId);

        //입력된 비밀번호가 db값과 일치여부 확인
        if (UserAuthCheckUtils.userAuthCheck(checkuser.getEmail(), checkuser.getPwd(), user)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/userUpdate",
            method = RequestMethod.POST)
    public ResponseEntity<String> userUpdate(@RequestParam("newPwd") String newPwd,
                                             @RequestParam("email") String email,
                                             HttpServletRequest request) {

        int userId = (int) request.getSession().getAttribute("userId");

        int isUpdated = userService.updatePwd(newPwd, email, userId);

        if (isUpdated == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/accRegister",
            method = RequestMethod.POST)
    public ResponseEntity<String> accRegister(@RequestBody BankAccountVO newAccount) {

        boolean registered = pointService.accRegister(newAccount);

        if (registered) {
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/accUpdate",
            method = RequestMethod.POST)
    public ResponseEntity<String> accUpdate(@RequestBody BankAccountVO newAccount) {

        boolean updated = pointService.accUpdate(newAccount);

        if (updated) {
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
