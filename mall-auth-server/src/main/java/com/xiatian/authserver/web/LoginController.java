package com.xiatian.authserver.web;


import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.TypeReference;
import com.xiatian.authserver.component.SmsComponent;
import com.xiatian.authserver.constant.AuthServerConstant;
import com.xiatian.authserver.feign.MemberFeignService;
import com.xiatian.authserver.service.RegisterService;
import com.xiatian.authserver.utils.R;
import com.xiatian.authserver.vo.UserLoginVo;
import com.xiatian.authserver.vo.UserRegistVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Controller
public class LoginController {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private RegisterService registerService;

	@Resource
	private MemberFeignService memberFeignService;

	@GetMapping({"/login.html","/","/index","/index.html"})
	public String loginPage(HttpSession session){
//		Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
//		if(attribute == null){
//			return "login";
//		}
//		return "redirect:http://gulimall.com";
		return "login";
	}

	@GetMapping({"reg.html"})
	public String regPage(HttpSession session){
		return "reg";
	}

	//注册成功回到首页，回到登陆页
	@PostMapping("/register")
	public String register(@Valid UserRegistVo vo, BindingResult result, RedirectAttributes redirectAttributes){
		//看是否有数据
		if(result.hasErrors()){
			Map<String, String> errors = result.getFieldErrors().stream().
					collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
			redirectAttributes.addFlashAttribute("errors", errors);
			return "redirect:http://auth.gulimall.com/reg.html ";
		}
		//真正注册成功，调用远程服务
		registerService.checkInfo(vo);
		String code = vo.getCode();
		String s = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX+vo.getPhone());
		if(!StringUtils.isEmpty(s)){
			if(code.equals(s.split("_")[0])){
				//删除验证码,令牌机制
				stringRedisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX+vo.getPhone());
				//真正注册位置，调用用户服务
				R r = memberFeignService.register(vo);
				if(r.getCode()==0){
					//成功
					return "redirect:http://auth.gulimall.com/login.html";
				}else{
					Map<String,String> errors = new HashMap<>();
					errors.put("msg",r.getMsg(new TypeReference<String>(){}));
					redirectAttributes.addFlashAttribute("errors",errors);
					return "redirect:http://auth.gulimall.com/reg.html";
				}
			}else{
				Map<String, String> errors = new HashMap<>();
				errors.put("code","验证码错误");
				redirectAttributes.addFlashAttribute("errors",errors);
				return "redirect:http://auth.gulimall.com/reg.html";
			}
		}else{
			Map<String, String> errors = new HashMap<>();
			errors.put("code","验证码已失效");
			redirectAttributes.addFlashAttribute("errors",errors);
			return "redirect:http://auth.gulimall.com/reg.html";
		}
	}

	@PostMapping("/login")
	public String login(UserLoginVo vo,RedirectAttributes redirectAttributes){
		R r = memberFeignService.login(vo);
		if(r.getCode()==0){
			return "redirect:http://gulimall.com";
		}else{
			Map<String,String> map = new HashMap<>();
			map.put("msg",r.getMsg(new TypeReference<String>(){}));
			//System.out.println(map);
			redirectAttributes.addFlashAttribute("errors",map);
			return "redirect:http://auth.gulimall.com/login.html";
		}
	}
}