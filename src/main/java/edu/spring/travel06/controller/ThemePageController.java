package edu.spring.travel06.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.spring.travel06.domain.*;
import edu.spring.travel06.pageutil.*;
import edu.spring.travel06.security.CustomUserDetailsService;
import edu.spring.travel06.security.domain.CustomUser;
import edu.spring.travel06.service.*;
import edu.spring.travel06.util.*;



@Controller
@RequestMapping(value ="/theme") // url : /travel/theme/
public class ThemePageController {
	private static final Logger logger = 
			LoggerFactory.getLogger(ThemePageController.class);
	

	@Autowired
	ThemePageService themeService;
	
	@Autowired
	ThemeReviewService themeReview;
	
	@Autowired
	private NoticePageService noticeService;
	
	@Autowired
	CustomUserDetailsService cuds;
	
	@Autowired
	private UserService serviceUser;
	
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	
	

	@GetMapping("/list")
	public void list(Model model, Principal princ,
			HttpSession session, String type, String keyword,
			Integer page, Integer perPage) throws Exception 
	{
		logger.info("list ??????");
		logger.info("page = " + page + ", perPage = " + perPage);
		
		// Paging ??????
		PageCriteria criteria = new PageCriteria();
		if (page != null) 
		{
			criteria.setPage(page);
		}
		
		if (perPage != null) 
		{ 
			criteria.setNumsPerPage(perPage);
		}
		
		if(keyword != null) 
		{
			if(type.equals("all")) 
			{
				criteria.setType(type);
				criteria.setKeyword(keyword);
			}
			else if(type.equals("title"))
			{
				criteria.setType(type);
				criteria.setKeyword(keyword);
			}
			else if(type.equals("content")) 
			{
				criteria.setType(type);
				criteria.setKeyword(keyword);
			}
			else if(type.equals("user")) 
			{
				criteria.setType(type);
				criteria.setKeyword(keyword);
			}
		}
		
		
		
		List<ThemePageVO> list = themeService.read(criteria);
		model.addAttribute("themeList", list);
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);
		
		List<NoticeVO> noticeList = noticeService.select();
		model.addAttribute("noticeList", noticeList);
		
		int totalPage = list.size() + noticeList.size();
		
//		logger.info("???????????? :" + list.get(0).getThemeGoodCnt());
//		logger.info("????????? :" + list.get(0).getThemeTitle());
		PageMaker maker = new PageMaker();
		maker.setCriteria(criteria);
		maker.setTotalCount(totalPage);
		maker.setPageData();
		model.addAttribute("pageMaker", maker);
		
		CustomUser custom = new CustomUser();
		try
		{
			String id = princ.getName();
			
			custom = (CustomUser)cuds.loadUserByUsername(id);
			logger.info(custom.toString());
		}
		catch(NullPointerException ne)
		{
			
			logger.info("error");
		}
		
		model.addAttribute("custom", custom);
		
		
	} // end list()
	
	// ?????? ??????

	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void registerGET(Model model, Principal princ,
				HttpSession session) throws Exception 
	{
		logger.info("registerGet()?????? : " + uploadPath);
		
		String id = princ.getName();
		CustomUser custom = (CustomUser)cuds.loadUserByUsername(id);
		model.addAttribute("custom", custom);
		
		

	}
	


	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String registerPost(ThemePageVO vo,MultipartFile file,
						RedirectAttributes reAttr) throws IOException 
	{
		logger.info("registerPost()?????? : " + uploadPath);
		
		logger.info("?????? ?????? : " + file.getOriginalFilename());
		logger.info("?????? ?????? : " + file.getSize());

		
		String imgUploadPath = FileUploadUtil.getUploadPath(uploadPath + File.separator + "imgUpload");  //  /uploadPath/imgUpload 
		logger.info("imgUploadPath : " + imgUploadPath);
		String fileName = null;
		
		if(file.getOriginalFilename() != null && file.getOriginalFilename() != "") {
			fileName =  FileUploadUtil.saveUploadedFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			logger.info("fileName : " + fileName);
			vo.setImg(fileName);

	
			
		} 
		else 
		{ 
			fileName = File.separator + "images" + File.separator + "none.png";

			vo.setImg(fileName);

		}

		int result = themeService.create(vo);
		if(result == 1) { // DB insert ??????	
			reAttr.addFlashAttribute("insert_result", "success");
			return "redirect:/theme/list"; // /theme/list ????????? ??????(get)??????
		} else { // DB insert ??????
			reAttr.addFlashAttribute("insert_result", "fail");
			return "redirect:/theme/register"; // /theme/register ????????? ??????
		}		
	}
	
	
	
	@GetMapping("/detail")
	public void detailGET(int themeNo, Model model, 
				@ModelAttribute("page") int page, 
				HttpSession session, Principal princ) 
	{
		logger.info("detail() ?????? : themeNo = " + themeNo);
		logger.info("page = " + page);
		ThemePageVO themeVO = themeService.read(themeNo);
		logger.info("?????? ????????? : " + themeVO.getThemaViewCnt());
		int count = themeService.updateThemeViewCnt(themeNo);
		logger.info("????????? ?????? = " + count);
		themeVO.setThemaViewCnt(themeVO.getThemaViewCnt() + count);
		logger.info("?????? ?????? ???  : " + themeVO.getThemaViewCnt());
		
		model.addAttribute("ThemePageVO", themeVO);
		
		
		// ????????? + ?????????
		CustomUser custom = new CustomUser();
		try
		{
			String userID = princ.getName();
			int mno = 0;
			custom = (CustomUser)cuds.loadUserByUsername(userID);
			logger.info(custom.toString());
			
			if(custom.getUvo() != null)
			{
				mno = custom.getUvo().getUserNo();
				
			}
			if(custom.getAvo() != null)
			{
				mno = custom.getAvo().getAdminNo();
			}
			
			ThemeLikeVO likeVO = new ThemeLikeVO();
		     likeVO.setThemaNo(themeNo);
		     likeVO.setMno(mno);

		     int themelike = themeService.getThemeLike(likeVO);
		     logger.info("???????????? ????????? ??? : "+themelike);

		     model.addAttribute("heart",themelike);
		     
		     ThemeWishVO wishVO = new ThemeWishVO();
		     wishVO.setThemaNo(themeNo);
		     wishVO.setMno(mno);
		     
		     int themaWish = themeService.getThemeWish(wishVO);
		     logger.info("????????? ?????? : "+themaWish);
		     
		     
		     model.addAttribute("basket",themaWish);
			
			
		}
		catch(NullPointerException ne)
		{
			
			logger.info("error");
			custom = new CustomUser();
			logger.info(custom.toString());
		}
		
		model.addAttribute("custom", custom);
		
		
	}
	
	@GetMapping("/update")
	public void updateGET(int themeNo, Model model, 
			@ModelAttribute("page") int page, HttpServletRequest request)
	{
		logger.info("update() ?????? : themeNo = " + themeNo);
		ThemePageVO vo = themeService.read(themeNo);
		model.addAttribute("ThemePageVO", vo);
	}
	
	@PostMapping("/update")
	public String updatePOST(ThemePageVO vo, int page) throws Exception {
		logger.info("updatePOST() ?????? : themeNo = " + vo.getThemaNo());
		int result = themeService.update(vo);
		if(result == 1) {
			return "redirect:/theme/list?page=" + page;
		} else {
			return "redirect:/theme/update?themeNo=" + vo.getThemaNo();
		}
	}
	
	@GetMapping("/delete")
	public String delete(int themeNo, RedirectAttributes reAttr) throws Exception 
	{
		logger.info("delete() ?????? : themeNo = " + themeNo);
		
		List<ThemeReviewVO> reviewList = themeReview.read(themeNo);
		for(ThemeReviewVO rvo : reviewList)
		{
			logger.info(rvo.toString());
			themeReview.delete(rvo.getThemaReviewNo(), themeNo);
			
		}
		
		int result = themeService.delete(themeNo);
		if(result == 1) {
			return "redirect:/theme/list";
		} else {
			return "redirect:/theme/detail?themeNo=" + themeNo;
		}
	}
	
	
	@GetMapping("/warning")
	public void UserWarning(Model model, HttpServletResponse response,
					@RequestParam("userNickname") String nickName) throws IOException
	{
		logger.info("user Warning Page : " + nickName);
		
		UserVO uvo = serviceUser.read(nickName);
		if(uvo == null) 
		{
			uvo = new UserVO();
			response.sendRedirect("/travel06/theme/list");
		}
		
		logger.info(uvo.toString());
			
		model.addAttribute("UserVO", uvo);
		
		int warn = serviceUser.updateUserWarning(1, uvo.getUserNo());
		logger.info("?????? : " + warn);
		if(warn ==1)
		{
			logger.info("?????? ??????");
			response.sendRedirect("/travel06/theme/list");
		}
		else
		{
			logger.info("?????? ?????? ??????");
		}
		
		
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/heart", method = RequestMethod.POST, produces = "application/json")
	public int heart(HttpServletRequest httpRequest, Principal princi)
	{
		
		int heart = Integer.parseInt(httpRequest.getParameter("heart"));
		int themeNo = Integer.parseInt(httpRequest.getParameter("themaNo"));
		
		
		CustomUser custom = new CustomUser();
		
		String id = princi.getName();
		logger.info("principal id = " + id);
		int mno = 0;
		custom = (CustomUser)cuds.loadUserByUsername(id);
		logger.info(custom.toString());
		
		if(custom.getUvo() != null)
		{
			mno = custom.getUvo().getUserNo();
			
		}
		if(custom.getAvo() != null)
		{
			mno = custom.getAvo().getAdminNo();
		}
		
		logger.info("????????? ????????? : " + heart);
		
		
		ThemeLikeVO ThemeLikeVO = new ThemeLikeVO();
		
		ThemeLikeVO.setThemaNo(themeNo);
		ThemeLikeVO.setMno(mno);
		
		logger.info(ThemeLikeVO.toString());
		
		
		
		if(heart >= 1) 
		{
			themeService.deleteThemeLike(ThemeLikeVO);
			heart = 0;
		} 
		else 
		{
			themeService.insertThemeLike(ThemeLikeVO);
			heart = 1;
		}
		return heart;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/basket", method = RequestMethod.POST, produces = "application/json")
	public int basket(HttpServletRequest httpRequest, Principal princi)
	{
		
		int basket = Integer.parseInt(httpRequest.getParameter("basket"));
		int themeNo = Integer.parseInt(httpRequest.getParameter("themaNo"));
		
		
		CustomUser custom = new CustomUser();
		
		String id = princi.getName();
		logger.info("principal id = " + id);
		int mno = 0;
		
		custom = (CustomUser)cuds.loadUserByUsername(id);
		logger.info(custom.toString());
		
		if(custom.getUvo() != null)
		{
			mno = custom.getUvo().getUserNo();
			
		}
		if(custom.getAvo() != null)
		{
			mno = custom.getAvo().getAdminNo();
		}
		
		logger.info("????????? ????????? : " + basket);
		
		ThemeWishVO themeWishVO = new ThemeWishVO();
		
		themeWishVO.setThemaNo(themeNo);
		themeWishVO.setMno(mno);
		
		
		
		if(basket >= 1) 
		{
			themeService.deleteThemeWish(themeWishVO);
			basket = 0;
		} 
		else 
		{
			themeService.insertThemeWish(themeWishVO);
			basket = 1;
		}
		return basket;
	}
	
	
	
	
	
	@GetMapping("/display")
	public ResponseEntity<byte[]> display(String fileName) throws IOException
	{
		logger.info("display() ?????? : " + fileName);
		
		ResponseEntity<byte[]> entity = null;
		InputStream in = null;
		
		String filePath = uploadPath + fileName;
		in = new FileInputStream(filePath);
		
		// ?????? ?????????
		String extension = 
				filePath.substring(filePath.lastIndexOf(".") + 1);
		logger.info("?????? ????????? : " + extension);
		// ?????? ??????(response header)??? Content-Type ??????
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaUtil.geMediaType(extension));
		logger.info("Http ?????? : "+httpHeaders);
		
		// ????????? ??????
		entity = new ResponseEntity<byte[]> (
					IOUtils.toByteArray(in), // ???????????? ?????? ?????????
					httpHeaders, // ?????? ??????
					HttpStatus.OK
				);
		return entity;
	}
	
	
	@GetMapping("/bestList")
	public void bestList(Model model, Integer page, Integer perPage, 
			HttpSession session) throws Exception 
	{
		logger.info("bestList ??????");
		logger.info("page = " + page + ", perPage = " + perPage);
		
		// Paging ??????
		PageCriteria criteria = new PageCriteria();
		if (page != null) 
		{
			criteria.setPage(page);
		}
		if (perPage != null) 
		{ 
			criteria.setNumsPerPage(perPage);
		}
		
		List<ThemePageVO> list = themeService.bestListAll();
		model.addAttribute("themeList", list);
		
		PageMaker maker = new PageMaker();
		maker.setCriteria(criteria);
		maker.setTotalCount(themeService.getTotalNumsOfRecords());
		maker.setPageData();
		model.addAttribute("pageMaker", maker);
		
		
		
	} // end bestList()
	
	@GetMapping("/bestReview")
	public void bestReview(Model model, Integer page, Integer perPage, 
				HttpSession session) throws Exception 
	{
		logger.info("bestReview ??????");
		logger.info("page = " + page + ", perPage = " + perPage);
		
		// Paging ??????
		PageCriteria criteria = new PageCriteria();
		if (page != null) 
		{
			criteria.setPage(page);
		}
		
		if (perPage != null) 
		{ 
			criteria.setNumsPerPage(perPage);
		}
		
		List<ThemePageVO> list = themeService.bestReviewAll();
		model.addAttribute("themeList", list);
		
		PageMaker maker = new PageMaker();
		maker.setCriteria(criteria);
		maker.setTotalCount(themeService.getTotalNumsOfRecords());
		maker.setPageData();
		model.addAttribute("pageMaker", maker);
		
		
		
	} // end bestReview()
	
	@GetMapping("/bestGood")
	public void bestGood(Model model, Integer page, Integer perPage, HttpSession session) throws Exception {
		logger.info("bestGood ??????");
		logger.info("page = " + page + ", perPage = " + perPage);
		
		// Paging ??????
		PageCriteria criteria = new PageCriteria();
		if (page != null) {
			criteria.setPage(page);
		}
		if (perPage != null) { 
			criteria.setNumsPerPage(perPage);
		}
		
		List<ThemePageVO> list = themeService.bestGoodAll();
		model.addAttribute("themeList", list);
		
		PageMaker maker = new PageMaker();
		maker.setCriteria(criteria);
		maker.setTotalCount(themeService.getTotalNumsOfRecords());
		maker.setPageData();
		model.addAttribute("pageMaker", maker);
		
		
		
	} // end bestGood()
	
	@GetMapping("/bestWish")
	public void bestWish(Model model, Integer page, Integer perPage, HttpSession session) throws Exception {
		logger.info("bestWish ??????");
		logger.info("page = " + page + ", perPage = " + perPage);
		
		// Paging ??????
		PageCriteria criteria = new PageCriteria();
		if (page != null) {
			criteria.setPage(page);
		}
		if (perPage != null) { 
			criteria.setNumsPerPage(perPage);
		}
		
		List<ThemePageVO> list = themeService.bestWishAll();
		model.addAttribute("themeList", list);
		
		PageMaker maker = new PageMaker();
		maker.setCriteria(criteria);
		maker.setTotalCount(themeService.getTotalNumsOfRecords());
		maker.setPageData();
		model.addAttribute("pageMaker", maker);
		
		
		
	} // end bestWish()
	
	
	
	
	private String saveUploadFile(MultipartFile file) throws Exception 
	{
		// UUID : ??????????????? ?????? ????????? ???????????? ?????????
		UUID uuid = UUID.randomUUID();
		String savedName = uuid + "_" + file.getOriginalFilename();
		File target = new File(uploadPath, savedName); // ????????? ?????? ?????? ??????
		
		try {
			FileCopyUtils.copy(file.getBytes(), target);
			logger.info("?????? ?????? ??????");
			return savedName;
		} 
		catch (IOException e) 
		{
			logger.error("?????? ?????? ??????");
			return null;
		}
		
	}
	
	
	
	
}	