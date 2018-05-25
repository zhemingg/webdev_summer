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
	public List<User> findAllUsers(@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "password", required = false) String password) {
		if (username != null && password != null) {
			return (List<User>) userRepository.findUserByCredentials(username, password);
		} else if (username != null) {
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
			user.setEmail(newUser.getEmail());
			user.setPhone(newUser.getPhone());
			user.setDateOfBirth(newUser.getDateOfBirth());
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

	/**
	 * Register a user if the username is valid, otherwise throws an exception.
	 * 
	 * @param user
	 *            a new user will be registered
	 * @param session
	 *            a session
	 * @return the user that registered successfully
	 * @throws Exception
	 *             if the username has been taken.
	 */
	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session) throws Exception {
		List<User> users = (List<User>) findAllUsers(user.getUsername(), null);
		if (users.size() == 0) {
			createUser(user);
			session.setAttribute("user", user);
			return (User) session.getAttribute("user");
		} else {
			throw new Exception("can not register");
		}
	}

	/**
	 * find a user by credentials
	 * 
	 * @param username
	 *            username
	 * @param password
	 *            password
	 * @return a user whose username and password are matched.
	 */
	@GetMapping("/api/user/findByCredentials")
	public List<User> findUserByCredentials(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = false) String password) {
		return (List<User>) userRepository.findUserByCredentials(username, password);
	}

	/**
	 * log in
	 * 
	 * @param user
	 *            the user
	 * @param session
	 *            the session
	 * @return a user whose username and password are matched, otherwise throw an
	 *         exception.
	 * @throws Exception
	 *             if can not log in.
	 */
	@PostMapping("/api/login")
	public User login(@RequestBody User user, HttpSession session) throws Exception {
		List<User> users = (List<User>) userRepository.findUserByCredentials(user.getUsername(), user.getPassword());
		if (users.size() == 0) {
			throw new Exception("can not log in");
		} else {
			User user2login = users.get(0);
			session.setAttribute("user", user2login);
			return user2login;
		}
	}

	@GetMapping("/api/profile")
	public User profile(HttpSession session) {
		User currentUser = (User) session.getAttribute("user");
		return currentUser;
	}
	
	@PutMapping("/api/profile")
	public User updateProfile(@RequestBody User user, HttpSession session) throws Exception { 
		//List<User> users = (List<User>) userRepository.findUserByCredentials(user.getUsername(), user.getPassword());
		User currUser = (User) session.getAttribute("user");
		//System.out.println("*************"+ user.getFirstName());
		if (currUser == null) {
			return null;
		} else {
			//System.out.println("*************"+users.get(0).getId());
			User newU = updateUser(currUser.getId(), user);
			session.setAttribute("user", newU);
			System.out.println(newU.getDateOfBirth());
			System.out.println(newU.getPhone());
			System.out.println(user.getDateOfBirth());
			System.out.println(user.getPhone());
			return newU;
		}
	}

	@PostMapping("/api/logout")
	public void logout
	(HttpSession session) {
		session.invalidate();
	}

}
