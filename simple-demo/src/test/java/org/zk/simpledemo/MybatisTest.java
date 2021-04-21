package org.zk.simpledemo;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zk.simpledemo.dao.UserDao;
import org.zk.simpledemo.domain.User;
import org.zk.simplemybatis.SqlSession;
import org.zk.simplemybatis.SqlSessionFactory;
import org.zk.simplemybatis.SqlSessionFactoryBuilder;
import org.zk.simplemybatis.io.Resources;

import java.io.InputStream;
import java.util.List;

public class MybatisTest {

	static SqlSessionFactory sqlSessionFactory;
	SqlSession sqlSession;

	@BeforeClass
	public static void beforeClass() throws Exception {
		// 创建SessionFactory
		InputStream inputStream = Resources.getResourceAsStream("mybatis.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Before
	public void before() {
		sqlSession = sqlSessionFactory.openSession();
	}

	@After
	public void after() {
		sqlSession.close();
	}

	@Test
	public void testSqlSession() {
		List<User> list = sqlSession.selectList("org.zk.simpledemo.dao.UserDao.findAll", null);
	}

	@Test
	public void testDao() {
		UserDao userDao = sqlSession.getMapper(UserDao.class);
		List<User> userList = userDao.findAll();
		System.out.println(userList);
	}
}
