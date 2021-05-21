package org.zk.config;

import org.zk.simplespring.context.annotation.ImportSelector;

public class DemoImportSelector implements ImportSelector {
	@Override
	public String selectImport() {
		return  FooConfig.class.getName();
	}
}
