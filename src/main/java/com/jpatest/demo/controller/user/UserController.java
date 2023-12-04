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

import com.jpatest.demo.config.SecureSHA256;
import com.jpatest.demo.model.common.ResponseEntity;
import com.jpatest.demo.model.user.EnoVO;
import com.jpatest.demo.model.user.UserVO;
import com.jpatest.demo.repo.UserLoginRepository;
import com.jpatest.demo.repo.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private SecureSHA256 secureSHA256;

    private static final int listSize = 10;

    /**
     * 로그인 페이지
     * @param session
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value="/userLogin.do")
	public String UserLoginPage(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("UserController UserLoginPage");

        return "main/userLoginPage";
	}

    /**
     * 회원가입 페이지
     * @param session
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value="/userRegist.do")
	public String UserRegistPage(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("UserController UserRegistPage");

        return "main/userRegistPage";
	}

    /**
     * 로그아웃 기능
     * @param session
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value="/userLogout.do")
    public String UserLogOut(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("UserController UserLoginPage");
        session.invalidate();
        return "redirect:/userLogin.do";
	}


    /**
     * 메인 페이지
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
        Page<EnoVO> userInfoList = userRepository.findAll(pageable);
        model.addAttribute("userInfoList", userInfoList);
        return "main/mainPage";
	}

    /**
     * 에러 페이지
     * @param session
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value="/error/error.do")
	public String errorPage(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		LOGGER.info("UserController errorPage View");
        model.addAttribute("errorCode",request.getAttribute("errorCode"));
        model.addAttribute("errorMsg",request.getAttribute("errorMsg"));
        return "error/errorPage";
	}

    /**
     * Class UserController
     * Method LoginUser
     * Info : 로그인 정보 체크
     * @param session
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
    */
    @PostMapping(value="/loginUser.do")
    public  String LoginUser(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        LOGGER.info("UserController loginUser");
        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");

        UserVO userInfo;
        //페이징 세팅
        userInfo = userLoginRepository.CheckUserLogin(userId, secureSHA256.encryptSHA256(userPw));
        if(userInfo != null){
            session.setAttribute("loginUser",userInfo);
            return "redirect:/main.do";
        }
        model.addAttribute("isSucceed", "false");
        model.addAttribute("failMsg", "로그인에 실패하였습니다.");
        return "redirect:/userLogin.do";
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

        Page<EnoVO> userInfoList;

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

    /**
     * Class UserController
     * Method UserJoin
     * Info : 사원데이터 추가 페이지
     * @param session
     * @param request
     * @param response
     * @param model
     * @return String
    */
    @GetMapping(value="/userJoin.do")
    public String UserJoin(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, Model model){
        LOGGER.info("UserController UserJoin View");
        return "main/joinPage";
    }

    /**
     * Class UserController
     * Method addUser
     * Info : 사원데이터 추가
     * @param session
     * @param request
     * @param response
     * @param model
     * @param redirect
     * @return String
    */
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
            userRepository.save(EnoVO.builder()
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

    /**
     * Class UserController
     * Method getUserRegist
     * Info : 사용자데이터 추가
     * @param session
     * @param request
     * @param response
     * @param model
     * @param redirect
     * @return String
    */
    @PostMapping(value="/getUserRegist.do")
    public String getUserRegist(HttpSession session, HttpServletRequest request
    , HttpServletResponse response, Model model, RedirectAttributes redirect){
        LOGGER.info("UserController getUserRegist");
        LocalDate nowDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd"); 
        LocalTime nowTime = LocalTime.now(); 
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        
        String userId = request.getParameter("userId");
        String userPw = request.getParameter("userPw");
        String celph = request.getParameter("celph");
        String email = request.getParameter("email");
        String cretDate = nowDate.format(dateFormatter);
        String cretTime = nowTime.format(timeFormatter);
        String chgDate = nowDate.format(dateFormatter);
        String chgTime = nowTime.format(timeFormatter);
        try{
            userLoginRepository.save(UserVO.builder()
                                .userId(userId)
                                .userPw(secureSHA256.encryptSHA256(userPw))
                                .celph(celph)
                                .email(email)
                                .cretDate(cretDate)
                                .cretTime(cretTime)
                                .chgDate(chgDate)
                                .chgTime(chgTime)
                                .build());
        }catch(Exception e){
            LOGGER.info("Error Is : {}",e.getMessage());
            redirect.addAttribute("errorCode", "ER001");
            redirect.addAttribute("errorMsg", "joinUser Error Check The Server Log");
            return "redirect:/errorPage.do";
        }
        redirect.addAttribute("successMsg", "등록 성공");
        return "redirect:/userLogin.do";
    }
}
