package webdev.repositories;

import org.springframework.data.repository.CrudRepository;
import webdev.models.Module;

public interface TopicRepository extends CrudRepository<Module, Integer>{
	
}
