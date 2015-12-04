package org.ranestar.dao;

import java.util.List;

import org.ranestar.model.User;

public interface UserDao {
	
	void save(User user);
	
	void update(User user);
	
	void delete(String user_id);
	
	void deleteAll();
	
	User findOne(String user_id);
	
	int count();
	
	List<User> findAll();

}
