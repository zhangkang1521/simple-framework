package org.zk.simpledemo.controller;

import org.zk.simple.spring.web.bind.annotation.RequestMapping;
import org.zk.simple.spring.web.servlet.ModelAndView;
import org.zk.simpledemo.domain.User;
import org.zk.simpledemo.service.UserService;
import org.zk.simplespring.beans.factory.BeanFactory;
import org.zk.simplespring.beans.factory.BeanFactoryAware;
import org.zk.simplespring.beans.factory.annotation.Autowired;
import org.zk.simplespring.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class AnnotationController implements BeanFactoryAware {

//	@Autowired
//	private UserService userService;

	private BeanFactory beanFactory;

	@RequestMapping("/list")
	public ModelAndView list() {
//		List<User> userList = userService.findAll();
		Map<String, Object> model = new HashMap<>();
//		model.put("username", userList.get(0).getUsername());
//		model.put("username", "zk");
		return new ModelAndView("hello", model);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
