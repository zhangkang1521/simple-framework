package org.zk.config;

import org.zk.simplespring.context.annotation.ImportSelector;

public class DemoImportSelector implements ImportSelector {
	@Override
	public String[] selectImport() {
		return  new String[] { FooConfig.class.getName() };
	}
}
