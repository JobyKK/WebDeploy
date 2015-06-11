package demo.service;

import java.util.List;

import demo.model.User;

public interface UserService {

	List<User> getAllUsers() throws ServerReceiveException;

	Boolean updateUser(User user) throws ServerReceiveException;

	Boolean saveUser(User user) throws ServerReceiveException;

	Boolean deleteUser(Integer id) throws ServerReceiveException;

}
