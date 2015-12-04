package org.ranestar.dao;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.ranestar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import config.MybatisConfig;
import config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={RootConfig.class, MybatisConfig.class})
@Transactional
public class UserDaoTest {

	@Autowired
	UserDao userDao;
	
	@Before
	public void setUp(){
		User user = new User();
		user.setUser_id("test");
		user.setUser_pw(BCrypt.hashpw("test123", BCrypt.gensalt(12)));
		user.setUser_nm("테스트");
		user.setUser_tel("010-123-4567");
		user.setUser_email("test@gmail.com");
		user.setReg_date(new Date());
		
		userDao.save(user);
	}
	
	@Test
	public void saveTest() {
		User user = new User();
		user.setUser_id("test1");
		user.setUser_pw(BCrypt.hashpw("test123", BCrypt.gensalt(12)));
		user.setUser_nm("테스트1");
		user.setUser_tel("010-123-4567");
		user.setUser_email("test1@gmail.com");
		user.setReg_date(new Date());
		
		userDao.save(user);
		
		User me = userDao.findOne("test1");
		assertEquals(user.getUser_id(), me.getUser_id());
		
	}
	
	@Test
	public void updateTest(){
		User user = userDao.findOne("test");
		user.setUser_nm("테스트 수정");
		user.setUser_tel("010-098-7654");
		user.setUser_email("test수정@gmail.com");
		
		userDao.update(user);
		
		User me = userDao.findOne("test");
		
		assertEquals(user.getUser_nm(), me.getUser_nm());
		assertEquals(user.getUser_tel(), me.getUser_tel());
		assertEquals(user.getUser_email(), me.getUser_email());
	}
	
	@Test
	public void deleteTest(){
		assertEquals(1, userDao.count());
		userDao.delete("test");
		assertEquals(0, userDao.count());
	}
	
	@Test
	public void deleteAllTest(){
		User user = new User();
		user.setUser_id("test1");
		user.setUser_pw(BCrypt.hashpw("test123", BCrypt.gensalt(12)));
		user.setUser_nm("테스트1");
		user.setUser_tel("010-123-4567");
		user.setUser_email("test1@gmail.com");
		user.setReg_date(new Date());
		
		userDao.save(user);
		assertEquals(2, userDao.count());
		userDao.deleteAll();
		assertEquals(0, userDao.count());
	}
	
	@Test
	public void findAllTest(){
		User user = new User();
		user.setUser_id("test1");
		user.setUser_pw(BCrypt.hashpw("test123", BCrypt.gensalt(12)));
		user.setUser_nm("테스트1");
		user.setUser_tel("010-123-4567");
		user.setUser_email("test1@gmail.com");
		user.setReg_date(new Date());
		
		userDao.save(user);
		
		List<User> userList = userDao.findAll();
		assertEquals(2, userList.size());
		
	}

}
