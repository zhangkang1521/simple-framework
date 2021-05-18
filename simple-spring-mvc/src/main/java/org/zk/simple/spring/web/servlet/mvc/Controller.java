package org.zk.simple.spring.web.servlet.mvc;

import org.zk.simple.spring.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * mvc Controller接口
 */
public interface Controller {

	ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response);
}
