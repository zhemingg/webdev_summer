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
import webdev.models.Topic;
import webdev.repositories.LessonRepository;
import webdev.repositories.TopicRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TopicService {
	@Autowired
	LessonRepository lessonRepository;

	@Autowired
	TopicRepository topicRepository;

	@PostMapping("/api/course/{cid}/module/{mid}/lesson/{lid}/topic")
	public Topic createTopic(@PathVariable("lid") int lessonId, @RequestBody Topic newTopic) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if (data.isPresent()) {
			Lesson lesson = data.get();
			newTopic.setLesson(lesson);
			newTopic.getLesson().getModule().getCourse().setModified(new Date());
			return topicRepository.save(newTopic);
		}
		return null;
	}

	@GetMapping("/api/course/{cid}/module/{mid}/lesson/{lid}/topic")
	public List<Topic> findAllTopicsForLesson(@PathVariable("lid") int lessonId) {
		Optional<Lesson> data = lessonRepository.findById(lessonId);
		if (data.isPresent()) {
			Lesson lesson = data.get();
			return lesson.getTopics();
		}
		return null;
	}

	@GetMapping("api/topic")
	public List<Topic> findAllTopics() {
		return (List<Topic>) topicRepository.findAll();
	}
	
	@DeleteMapping("/api/topic/{tId}")
	public void deleteTopic( @PathVariable("tId") int topicId) {
		Optional<Topic> data = topicRepository.findById(topicId);
		if (data.isPresent()) {
			data.get().getLesson().getModule().getCourse().setModified(new Date());
		}
		topicRepository.deleteById(topicId);
	}


}
