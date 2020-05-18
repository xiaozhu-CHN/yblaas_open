package com.yiban.yblaas.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrHandler {
	
	private static final Logger LOG = LoggerFactory.getLogger(ErrHandler.class);

	/**
	 * 功能描述:
	 * (运行时异常的处理)
	 *
	 * @param exception 运行时异常
	 * @return : org.springframework.web.servlet.ModelAndView
	 * @author : xiaozhu
	 * @date : 2020/2/29 16:48
	 */
	@ExceptionHandler({RuntimeException.class})
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(RuntimeException exception){
		LOG.info("自定义异常处理-RuntimeException"+exception.getMessage());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/error/500.html");
		return mav;
	}

	/**
	 * 功能描述:
	 * (编译时异常)
	 *
	 * @param exception 编译时异常
	 * @return : org.springframework.web.servlet.ModelAndView
	 * @author : xiaozhu
	 * @date : 2020/2/29 16:49
	 */
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView processException(Exception exception){
		LOG.info("统一异常处理-Exception"+exception.getMessage());
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/error/500.html");
		return mav;
	}

}
