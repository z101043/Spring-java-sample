package org.ranestar.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.ranestar.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void save(User user) {
		sqlSession.insert("save", user);
	}

	@Override
	public void update(User user) {
		sqlSession.update("update", user);
	}

	@Override
	public void delete(String user_id) {
		sqlSession.delete("delete", user_id);
	}
	
	@Override
	public void deleteAll() {
		sqlSession.delete("deleteAll");
	}

	@Override
	public User findOne(String user_id) {
		return sqlSession.selectOne("findOne", user_id);
	}

	@Override
	public int count() {
		return sqlSession.selectOne("count");
	}

	@Override
	public List<User> findAll() {
		return sqlSession.selectList("findAll");
	}


}
