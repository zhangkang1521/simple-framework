package org.zk.simplespring.beans;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

public class PropertyValue {
	private final String name;

	/**
	 * 可能是TypedStringValue, RuntimeBeanReference
	 */
	private final Object value;

	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return JSONUtil.toJsonStr(this);
	}
}
