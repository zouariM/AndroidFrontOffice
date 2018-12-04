package runingtracking.service;

import java.util.List;

import runingtracking.domain.User;

public interface UserService {
	public List<User> findAll();
	public User add(User user);
	public User findById(String id);
}
