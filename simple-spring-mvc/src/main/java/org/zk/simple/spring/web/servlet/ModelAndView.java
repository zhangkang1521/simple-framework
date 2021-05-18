package org.zk.simple.spring.web.servlet;

import java.util.Map;

public class ModelAndView {
	private String view;
	private Map<String, Object> model;


	public ModelAndView(String view, Map<String, Object> model) {
		this.view = view;
		this.model = model;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}
}
