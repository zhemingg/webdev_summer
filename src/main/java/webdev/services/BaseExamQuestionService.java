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

import webdev.models.Assignment;
import webdev.models.BaseExamQuestion;
import webdev.models.Exam;
import webdev.repositories.BaseExamQuestionRepository;
import webdev.repositories.ExamRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class BaseExamQuestionService {
	@Autowired
	BaseExamQuestionRepository repository;
	

	@Autowired
	ExamRepository examRepository;
	
	@GetMapping("api/baseExamQuestion")
	public List<BaseExamQuestion> findAllBaseExamQuestion() {
		return (List<BaseExamQuestion>) repository.findAll();
	}
	
	@GetMapping("/api/baseExamQuestion/{bid}")
	public BaseExamQuestion findBaseExamQuestionById(@PathVariable("bid") int id) {			
		Optional<BaseExamQuestion> data =  repository.findById(id);		
		if (data.isPresent()) {
			return data.get();
		}
		return null;	
	}
	
	@GetMapping("/api/exam/{examId}/baseExamQuestion")
	public List<BaseExamQuestion> findAllBaseExamQuestionForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalQuestion = examRepository.findById(examId);
		if (optionalQuestion.isPresent()) {
			Exam exam = optionalQuestion.get();
			return exam.getBaseExamQuestion();
		}
		return null;
	}
	
	@PostMapping("/api/exam/{examId}/baseExamQuestion")
	public BaseExamQuestion createBaseExamQuestion(@PathVariable("examId") int examId, @RequestBody BaseExamQuestion question) {
		Optional<Exam> data = examRepository.findById(examId);
		if (data.isPresent()) {
			Exam exam = data.get();
			question.setExam(exam);
			question.getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
			return repository.save(question);
		}
		return null;
	}

	
	@DeleteMapping("/api/baseExamQuestion/{bId}")
	public void deleteBaseExamQuestion( @PathVariable("bId") int baseExamQuestionId) {
		repository.deleteById(baseExamQuestionId);
	}

	@PutMapping("/api/baseExamQuestion/{bId}")
	public BaseExamQuestion updateBaseExamQuestion(@PathVariable("bId") int baseExamQuestionId, @RequestBody BaseExamQuestion newBaseExamQuestion) {
		Optional<BaseExamQuestion> data = repository.findById(baseExamQuestionId);
		if (data.isPresent()) {
			BaseExamQuestion baseExamQuestion = data.get();
			baseExamQuestion.setDescription(newBaseExamQuestion.getDescription());
			baseExamQuestion.setTitle(newBaseExamQuestion.getTitle());
			baseExamQuestion.setPoints(newBaseExamQuestion.getPoints());
			repository.save(baseExamQuestion);
			return baseExamQuestion;
		}
		return null;
	}

}
