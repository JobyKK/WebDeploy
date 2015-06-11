package demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.jms_service.MessageSender;
import demo.model.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	MessageSender messageSender;
	
	@Override
	public List<User> getAllUsers() throws ServerReceiveException{

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("method", "get");
		model.put("filter", "all");
        try {
			model = messageSender.send(model);
		} catch (JMSException e) {
			e.printStackTrace();
		}
        List<User> users = new ArrayList<User>();
        ObjectMapper mapper = new ObjectMapper();
        
        //from Map to list of User
        for(Map<String, String> user :  (List<Map<String, String>>) model.get("users") ){
        	users.add(mapper.convertValue(user, User.class));
        }
		return users;
	}
	
	@Override
	public Boolean updateUser(User user){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("method", "get");
		model.put("filter", "all");
		return true;
	}
	
	@Override
	public Boolean saveUser(User user) throws ServerReceiveException{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("method", "save");
		model.put("user", user);
		try {
			messageSender.send(model);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public Boolean deleteUser(Integer id) throws ServerReceiveException{
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("method", "delete");
		model.put("userId", id);
		try {
			messageSender.send(model);
		} catch (JMSException e) {
			e.printStackTrace();
		};
		return true;
	}
	
}
