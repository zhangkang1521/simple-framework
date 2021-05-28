package org.zk.simplespring.context.annotation;

/**
 * 配置选择
 * @see Import
 */
public interface ImportSelector {

	// TODO 需要参数，参考EnableTransactionManagement
	String[] selectImport();
}
