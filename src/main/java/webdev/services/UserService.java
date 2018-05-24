package webdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import webdev.models.User;
import webdev.repositories.UserRepository;

import java.util.*;

import javax.servlet.http.HttpSession;

/**
 * @author zheminggao
 *
 */
@RestController
public class UserService {
	@Autowired
	UserRepository userRepository;

	/**
	 * accepts a POST with a user object embedded in the HTTP BODY. Parses the user
	 * from the BODY and then uses the UserRepository to insert it in the database
	 * 
	 * @param user
	 *            the user object
	 * @return the saved entity will never be null.
	 */
	@PostMapping("/api/user")
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	/**
	 * accepts a GET request and responds with a JSON array of all users
	 * 
	 * @return all instances of the type
	 */
	@GetMapping("/api/user")
	public List<User> findAllUsers(@RequestParam(name = "username", required = false) String username) {
		if (username != null) {
			return (List<User>) userRepository.findUserByUsername(username);
		} else {
			return (List<User>) userRepository.findAll();
		}

	}

	/**
	 * accepts a GET request and parses the userId from the path. Retrieves the user
	 * whose primary key is equal to the userId, and then returns to user a single
	 * JSON object
	 * 
	 * @param userId
	 *            the id of the user
	 * @return a user whose id is identity to the given id.
	 */
	@GetMapping("/api/user/{userId}")
	public User findUserById(@PathVariable("userId") int userId) {
		Optional<User> data = userRepository.findById(userId);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}

	/**
	 * accepts an HTTP PUT request, parses the userId as a path parameter and a
	 * User. java instance from the HTTP BODY. Uses the UserRepository to find the
	 * user by its userId and then updates the record with the new values in the
	 * user object
	 * 
	 * @param userId
	 *            the id of the user
	 * @param newUser
	 *            the user that contains new information
	 * @return the updated user or null if can not find the user by userId
	 */
	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser) {
		Optional<User> data = userRepository.findById(userId);
		if (data.isPresent()) {
			User user = data.get();
			user.setUsername(newUser.getUsername());
			user.setPassword(newUser.getPassword());
			user.setFirstName(newUser.getFirstName());
			user.setLastName(newUser.getLastName());
			user.setRole(newUser.getRole());
			userRepository.save(user);
			return user;
		}
		return null;
	}

	/**
	 * accepts an HTTP DELETE action, parses the userId as a path parameter and uses
	 * the UserRepository to remove the user whose id is equal to the userId path
	 * parameter
	 * 
	 * @param id
	 *            the id of the user
	 */
	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		userRepository.deleteById(id);
	}

	/**
	 * parses the username from a query parameter called username, then uses the
	 * repository's findUserByUsername() method to retrieve the user and returns the
	 * user instance
	 * 
	 * @param username
	 *            the username
	 * @return the user whose username is identity to the given username.
	 */
	@GetMapping("api/user/findByUsername")
	public List<User> findUserByUsername(@RequestParam(name = "username", required = true) String username) {
		return (List<User>) userRepository.findUserByUsername(username);
	}
	
	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session) throws Exception { 
		List<User> users = (List<User>)userRepository.findUserByUsername(user.getUsername());
		if(users.size() == 0) {
			createUser(user);
			session.setAttribute("user", user);
			return (User) session.getAttribute("user");
		} else {
			throw new Exception ("can not register");
		}
	}


}
