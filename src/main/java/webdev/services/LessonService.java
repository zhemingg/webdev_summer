package webdev.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Lesson;
import webdev.models.Module;
import webdev.repositories.LessonRepository;
import webdev.repositories.ModuleRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonService {
	@Autowired
	LessonRepository lessonRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@PostMapping("/api/course/{cid}/module/{mid}/lesson")
	public Lesson createLesson(@PathVariable("mid") int moduleId, @RequestBody Lesson newLesson) {
		Optional<Module> data = moduleRepository.findById(moduleId);

		if (data.isPresent()) {
			Module module = data.get();
			newLesson.setModule(module);
			newLesson.getModule().getCourse().setModified(new Date());
			return lessonRepository.save(newLesson);
		}
		return null;
	}
	
	@DeleteMapping("/api/lesson/{id}")
	public void deleteLesson (@PathVariable("id") int id){
		Optional<Lesson> data = lessonRepository.findById(id);
		if (data.isPresent()) {
			data.get().getModule().getCourse().setModified(new Date());
		}
		lessonRepository.deleteById(id);
	}
	
	@GetMapping("/api/lesson")
	public List<Lesson> findAllLessons()
	{
		return (List<Lesson>) lessonRepository.findAll();
	}
	
	@GetMapping("/api/course/{cid}/module/{mid}/lesson")
	public List<Lesson >findAllLessonsForModule(@PathVariable("mid") int mid){
		Optional<Module> data = moduleRepository.findById(mid);
		if(data.isPresent()) {
			Module module = data.get();
			return (List<Lesson>) module.getLessons();
		}
		return null;	
		
	}
	
	

}

