package org.zk.simplespring.beans.factory.xml;


public interface NamespaceHandlerResolver {

	/**
	 * 获取自定义标签命名空间对应的NamespaceHandler
	 * @param namespaceUri
	 * @return
	 */
	NamespaceHandler resolve(String namespaceUri);

}
