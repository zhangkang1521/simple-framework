package org.zk.simplespring.beans;

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
}
