package webdev.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import webdev.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	/**
	 * Find a user by its username
	 * @param u username
	 * @return the user by the user name
	 */
	@Query("SELECT u FROM User u WHERE u.username=:username")
	Iterable<User> findUserByUsername(@Param("username") String u);
	
	
	/**
	 * Find user by its credentials
	 * @param username the username
	 * @param password the password
	 * @return a user whose credential match the username and password
	 */
	@Query("SELECT u FROM User u WHERE u.username=:username AND u.password=:password")
			Iterable<User> findUserByCredentials(
			@Param("username") String username, 
			@Param("password") String password);


}
