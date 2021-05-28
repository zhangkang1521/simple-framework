package org.zk.simplespring.core.io.support;

import org.junit.Assert;
import org.junit.Test;
import org.zk.domain.User;

import java.util.List;

import static org.junit.Assert.*;

public class SpringFactoriesLoaderTest {

	@Test
	public void loadFactoryNames() {
		List<String> list = SpringFactoriesLoader.loadFactoryNames(User.class);
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration", list.get(0));
		Assert.assertEquals("org.springframework.boot.autoconfigure.aop.AopAutoConfiguration", list.get(1));
	}
}