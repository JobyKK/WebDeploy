package demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.model.User;
import demo.service.ServerReceiveException;
import demo.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	
	//put
	//unused
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT )
	public Map<String, String> updateUser(@PathVariable String userId, @RequestBody User user, HttpServletResponse response){
		System.out.println("put");
		Map<String, String> model = new HashMap<String, String>();
		model.put("message", "OK");
		return model;
	}
	
	//post
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, String> createUser(@RequestBody User user, HttpServletResponse response){
		System.out.println("post");
		try {
			userService.saveUser(user);
		} catch (ServerReceiveException e) {
			e.printStackTrace();
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.sendError(500, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		Map<String, String> model = new HashMap<String, String>();
		model.put("message", "OK");
		return model;
	}
	
	//get
	@RequestMapping(method = RequestMethod.GET)
	public List<User> getUsers(HttpServletResponse response){
		System.out.println("get");
		try {
			return userService.getAllUsers();
		} catch (ServerReceiveException e) {
			e.printStackTrace();
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.sendError(500, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	//delete
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public Map<String , String> deleteUser(@PathVariable Integer userId, HttpServletResponse response){
		System.out.println("delete");
		try {
			userService.deleteUser(userId);
		} catch (ServerReceiveException e) {
			e.printStackTrace();
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
			try {
				response.sendError(500, e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		Map<String , String> model = new HashMap<String, String>();
		model.put("message", "OK");
		return model;
	}
	
	
}
