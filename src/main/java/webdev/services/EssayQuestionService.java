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
import webdev.models.EssayQuestion;
import webdev.models.Exam;
import webdev.repositories.BaseExamQuestionRepository;
import webdev.repositories.EssayQuestionRepository;
import webdev.repositories.ExamRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class EssayQuestionService {

	@Autowired
	EssayQuestionRepository repository;
	

	@Autowired
	ExamRepository examRepository;
	
	@GetMapping("api/essayQuestion")
	public List<EssayQuestion> findAllEssayQuestion() {
		return (List<EssayQuestion>) repository.findAll();
	}
	
	@GetMapping("/api/essayQuestion/{eid}")
	public BaseExamQuestion findEssayQuestionById(@PathVariable("eid") int id) {			
		Optional<EssayQuestion> data =  repository.findById(id);		
		if (data.isPresent()) {
			return data.get();
		}
		return null;	
	}
	
	@GetMapping("/api/exam/{examId}/essayQuestion")
	public List<BaseExamQuestion> findAllEssayQuestionForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalQuestion = examRepository.findById(examId);
		if (optionalQuestion.isPresent()) {
			Exam exam = optionalQuestion.get();
			List<BaseExamQuestion> questions = exam.getBaseExamQuestion();
			List<BaseExamQuestion> res = exam.getBaseExamQuestion();
			for (BaseExamQuestion question:questions) {
				if (question instanceof EssayQuestion) {
					res.add(question);
				}	
			}
			return res;
		}
		return null;
	}
	
	@PostMapping("/api/exam/{examId}/essayQuestion")
	public BaseExamQuestion createEssayQuestion(@PathVariable("examId") int examId, @RequestBody EssayQuestion question) {
		Optional<Exam> data = examRepository.findById(examId);
		if (data.isPresent()) {
			Exam exam = data.get();
			question.setExam(exam);
			question.getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
			return repository.save(question);
		}
		return null;
	}

	
	@DeleteMapping("/api/essayQuestion/{bId}")
	public void deleteEssayQuestion( @PathVariable("bId") int questionId) {
		repository.deleteById(questionId);
	}

	@PutMapping("/api/essayQuestion/{bId}")
	public BaseExamQuestion updateEssayQuestion(@PathVariable("bId") int questionId, @RequestBody EssayQuestion newQuestion) {
		Optional<EssayQuestion> data = repository.findById(questionId);
		if (data.isPresent()) {
			EssayQuestion question = data.get();
			question.setDescription(newQuestion.getDescription());
			question.setTitle(newQuestion.getTitle());
			question.setPoints(newQuestion.getPoints());
			repository.save(question);
			return question;
		}
		return null;
	}

}
