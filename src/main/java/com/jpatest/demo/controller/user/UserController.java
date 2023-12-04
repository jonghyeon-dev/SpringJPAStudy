package com.jpatest.demo.controller.user;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jpatest.demo.model.common.ResponseEntity;
import com.jpatest.demo.model.user.TADM00100VO;
import com.jpatest.demo.repo.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    private static final int listSize = 10;

    /**
     * 
     * @param session
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value="/main.do")
	public String mainPage(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("UserController mainPage View");

        PageRequest pageable = PageRequest.of(0, listSize, Sort.by(Direction.DESC, "seq"));
        Page<TADM00100VO> userInfoList = userRepository.findAll(pageable);
        model.addAttribute("userInfoList", userInfoList);
        return "main/mainPage";
	}

    /**
     * 
     * @param session
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value="/error.do")
	public String errorPage(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("UserController errorPage View");
        model.addAttribute("errorCode",request.getAttribute("errorCode"));
        model.addAttribute("errorMsg",request.getAttribute("errorMsg"));
        return "error/errorPage";
	}

    /**
     * Class UserController
     * Method getUserController
     * Info : 사용자 번호(seq), 사용자 ID(eno)를 기반으로 페이징된 사용자 데이터 호출
     * @param session
     * @param seq
     * @param eno
     * @param paging
     * @return
     * @throws Exception
    */
    @ResponseBody
    @RequestMapping(value="/getUserInfo.do", method=RequestMethod.GET)
    public  ResponseEntity getUserInfo(HttpSession session, String seq, String eno, String paging) throws Exception {
        LOGGER.info("UserController getUserInfo");
        ResponseEntity js = new ResponseEntity();  

        Page<TADM00100VO> userInfoList;

        //Long ID 데이터 Null 체크
        Long seqData = null;
        if(seq != null && !"".equals(seq)){
            seqData = Long.valueOf(seq);
        }
        //페이징 세팅
        PageRequest pageable = PageRequest.of(Integer.parseInt(paging), listSize, Sort.by(Direction.DESC, "seq"));
        userInfoList = userRepository.findBySeqOrEnoTest(seqData, eno, pageable);

        js.setSucceed(true);
        js.setData(userInfoList);
        return js;
    }

    @GetMapping(value="/userJoin.do")
    public String userJoin(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, Model model){
        LOGGER.info("UserController userJoin View");
        return "main/joinPage";
    }

    @PostMapping(value="/addUser.do")
    public String addUser(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, Model model, RedirectAttributes redirect){
        LOGGER.info("UserController addUser");
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd"); 
        LocalTime nowTime = LocalTime.now(); 
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        String eno = request.getParameter("eno");
        String enoPw = request.getParameter("enoPw");
        String celph = request.getParameter("celph");
        String email = request.getParameter("email");
        String cretDt = nowDate.format(dateFormatter);
        String cretTm = nowTime.format(timeFormatter);
        String chgDt = nowDate.format(dateFormatter);
        String chgTm = nowTime.format(timeFormatter);
        try{
            userRepository.save(TADM00100VO.builder()
                                .eno(eno)
                                .enoPw(enoPw)
                                .celph(celph)
                                .email(email)
                                .cretDt(cretDt)
                                .cretTm(cretTm)
                                .chgDt(chgDt)
                                .chgTm(chgTm)
                                .build());
        }catch(Exception e){
            LOGGER.info("Error Is : {}",e.getMessage());
            redirect.addAttribute("errorCode", "ER001");
            redirect.addAttribute("errorMsg", "addUser Error Check The Server Log");
            return "redirect:/errorPage.do";
        }
        redirect.addAttribute("successMsg", "등록 성공");
        return "redirect:/main.do";
    }
}
