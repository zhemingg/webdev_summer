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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.Course;
import webdev.models.Topic;
import webdev.models.User;
import webdev.models.Widget;
import webdev.repositories.TopicRepository;
import webdev.repositories.WidgetRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class WidgetService {
	@Autowired
	WidgetRepository repository;

	@Autowired
	TopicRepository topicRepository;

	@GetMapping("/api/widget")
	public List<Widget> findAllWidgets() {
		return (List<Widget>) repository.findAll();
	}

	@PostMapping("/api/topic/{tid}/widget")
	public void saveAllWidgets(@PathVariable("tid") int topicId, @RequestBody List<Widget> widgets) {
		List<Widget> current = findAllWidgetsForTopic(topicId);
		Optional<Topic> data = topicRepository.findById(topicId);
		if (data.isPresent()) {
			Topic topic = data.get();
			topic.getLesson().getModule().getCourse().setModified(new Date());
			for (Widget widget : current) {
				repository.delete(widget);
			}
			for (Widget widget : widgets) {
				widget.setTopic(topic);
				repository.save(widget);
			}
		}
	}

	@GetMapping("/api/topic/{topicId}/widget")
	public List<Widget> findAllWidgetsForTopic(@PathVariable("topicId") int topicId) {
		Optional<Topic> optionalTopic = topicRepository.findById(topicId);
		if (optionalTopic.isPresent()) {
			Topic topic = optionalTopic.get();
			return topic.getWidgets();
		}
		return null;
	}
	
	@DeleteMapping("/api/widget/{wId}")
	public void deleteWidget( @PathVariable("wId") int widgetId) {
		repository.deleteById(widgetId);
	}
	
	@PutMapping("/api/widget/{wId}}")
	public Widget updateWidget(@PathVariable("wId") int widgetId, @RequestBody Widget newWidget) {
		Optional<Widget> data = repository.findById(widgetId);
		if (data.isPresent()) {
			Widget widget = data.get();
			widget.setHeight(newWidget.getHeight());
			widget.setListType(newWidget.getListType());
			widget.setName(newWidget.getName());
			widget.setSize(newWidget.getSize());
			widget.setSrc(newWidget.getSrc());
			widget.setStyle(newWidget.getStyle());
			widget.setText(newWidget.getText());
			widget.setUrl(newWidget.getUrl());
			widget.setWidgetOrder(newWidget.getWidgetOrder());
			widget.setWidgetType(newWidget.getWidgetType());
			widget.setWidth(newWidget.getWidth());
			repository.save(widget);
			return widget;
		}
		return null;
	}
	
	@GetMapping("/api/widget/{id}")
	public Widget findWidgetById(@PathVariable("id") int id) {			
		Optional<Widget> data =  repository.findById(id);		
		if (data.isPresent()) {
			return data.get();
		}
		return null;	
	}

}
