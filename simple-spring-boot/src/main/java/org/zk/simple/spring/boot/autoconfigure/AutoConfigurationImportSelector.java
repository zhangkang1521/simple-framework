package org.zk.simple.spring.boot.autoconfigure;

import org.zk.simplespring.context.annotation.ImportSelector;
import org.zk.simplespring.core.io.support.SpringFactoriesLoader;

/**
 * 自动配置，扫描所有jar包下spring.factories
 */
public class AutoConfigurationImportSelector implements ImportSelector {

	@Override
	public String[] selectImport() {
		return SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class).toArray(new String[0]);
	}

}
