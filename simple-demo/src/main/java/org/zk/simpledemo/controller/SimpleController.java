package org.zk.simpledemo.controller;

import org.zk.simple.spring.web.servlet.ModelAndView;
import org.zk.simple.spring.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SimpleController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<>();
		model.put("username", "zk");
		return new ModelAndView("hello", model);
	}
}
