package com.ncncn.controller;

import com.ncncn.domain.CriteriaSM;
import com.ncncn.domain.PrcUpdateDTO;
import com.ncncn.domain.UserVO;
import com.ncncn.service.GifticonService;
import com.ncncn.service.UserService;
import com.ncncn.util.UserAuthCheckUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@Log4j
@RequestMapping("/gifticon")
@AllArgsConstructor
public class GifticonController {

    GifticonService gifticonService;
    UserService userService;

    @GetMapping("/stus005")
    public String gftDealCmpl(HttpServletRequest request, int gftId, CriteriaSM cri, Model model) {

        gifticonService.gftDealCmpl(gftId);

        model.addAttribute("cri", cri);

        if (request.getHeader("referer").contains("/dealDetail")) { //구매상세페이지에서 구매확정한 경우 구매상세로 다시 보냄

            model.addAttribute("gftId", gftId);
            return "redirect:/user/mypage/dealDetail";
        }

        //구매리스트에서 구매확정한 경우 구매리스트로 다시 보냄
        return "redirect:/user/mypage/deal";
    }

    @GetMapping("/delGft")
    public String deleteGifticon(HttpServletRequest request, int gftId, CriteriaSM cri, Model model) {

        log.info("gifticon delete Controller....");

        model.addAttribute("cri", cri);

        try {
            gifticonService.deleteGifticon(gftId);

        } catch (Exception e) {
            log.info("기프티콘 삭제에 실패했습니다.");
        }
        return "redirect:/user/mypage/sells";
    }

    @RequestMapping(method = {RequestMethod.POST}, //기프티콘 가격 수정 컨트롤러
            value = "/updateGft",
            consumes = "application/json",
            produces = "application/text; charset=utf-8")
    public ResponseEntity<String> updateGifticon(@RequestBody PrcUpdateDTO prcUpdate, HttpServletRequest request) {

        int userId = (int) request.getSession().getAttribute("userId");
        UserVO user = userService.readbyId(userId);

        String msg = "";

        if (UserAuthCheckUtils.userAuthCheck(prcUpdate.getEmail(), prcUpdate.getPassword(), user)) {

            log.info("기프티콘 가격 수정....비밀번호 일치");

            int isUpdated = gifticonService.updateGftPrc(prcUpdate.getGftId(), prcUpdate.getIsAutoPrc(), prcUpdate.getDcPrc());

            if (isUpdated == 1) { //가격 수정 성공한 경우 success

                log.info("수정완료");
                return new ResponseEntity<>(HttpStatus.OK);
            } else { //가격 수정 실패한 경우 serverError

                log.info("가격수정 실패");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else { //비밀번호 일치하지 않는 경우 serverError

            log.info("비밀번호가 일치하지 않습니다.");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}