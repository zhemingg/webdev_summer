package webdev.services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Assignment;
import webdev.models.Lesson;
import webdev.models.Topic;
import webdev.models.Widget;
import webdev.repositories.AssignmentRepository;
import webdev.repositories.TopicRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AssignmentService {
	@Autowired
	AssignmentRepository repository;
	

	@Autowired
	TopicRepository topicRepository;
	
	@GetMapping("api/assignment")
	public List<Assignment> findAllAssignments() {
		return (List<Assignment>) repository.findAll();
	}
	
	@GetMapping("/api/assignment/{aid}")
	public Widget findAssignmentById(@PathVariable("aid") int id) {			
		Optional<Assignment> data =  repository.findById(id);		
		if (data.isPresent()) {
			return data.get();
		}
		return null;	
	}
	
	@GetMapping("/api/topic/{topicId}/assignment")
	public List<Widget> findAllAssignmentForTopic(@PathVariable("topicId") int topicId) {
		Optional<Topic> optionalTopic = topicRepository.findById(topicId);
		if (optionalTopic.isPresent()) {
			Topic topic = optionalTopic.get();
			List<Widget> widgets = topic.getWidgets();
			List<Widget> res = new LinkedList<>();
			for (Widget w:widgets) {
				if (w.getWidgetType().equals("assignment"))
					res.add(w);
			}
			
			return res;
		}
		return null;
	}
	
	@PostMapping("/api/topic/{tid}/assignment")
	public Assignment createAssignment(@PathVariable("tid") int topicId, @RequestBody Assignment assignment) {
		Optional<Topic> data = topicRepository.findById(topicId);
		if (data.isPresent()) {
			Topic topic = data.get();
			assignment.setTopic(topic);
			assignment.getTopic().getLesson().getModule().getCourse().setModified(new Date());
			return repository.save(assignment);
		}
		return null;

	}

	
	@DeleteMapping("/api/assignment/{aId}")
	public void deleteAssignment( @PathVariable("aId") int assignmentId) {
		repository.deleteById(assignmentId);
	}

}
