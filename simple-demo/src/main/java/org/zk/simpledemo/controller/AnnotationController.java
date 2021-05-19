package org.zk.simpledemo.controller;

import org.zk.simple.spring.web.bind.annotation.RequestMapping;
import org.zk.simple.spring.web.servlet.ModelAndView;
import org.zk.simplespring.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class AnnotationController {

	@RequestMapping("/test")
	public ModelAndView test() {
		Map<String, Object> model = new HashMap<>();
		model.put("username", "zk");
		return new ModelAndView("hello", model);
	}
}
