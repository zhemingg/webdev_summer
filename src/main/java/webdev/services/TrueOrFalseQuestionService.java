package webdev.services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webdev.models.BaseExamQuestion;
import webdev.models.Exam;
import webdev.models.TrueOrFalseQuestion;
import webdev.repositories.ExamRepository;
import webdev.repositories.TrueOrFalseQuestionRepository;

@RestController
public class TrueOrFalseQuestionService {
	@Autowired
	TrueOrFalseQuestionRepository repository;
	

	@Autowired
	ExamRepository examRepository;

	@GetMapping("/api/trueOrFalseQuestion")
	public List<TrueOrFalseQuestion> findAllTrueOrFalseQuestion() {
		return (List<TrueOrFalseQuestion>) repository.findAll();
	}
	
	@GetMapping("/api/trueOrFalseQuestion/{tid}")
	public TrueOrFalseQuestion findMultipleChoiceQuestionById(@PathVariable("tid") int id) {			
		Optional<TrueOrFalseQuestion> data =  repository.findById(id);		
		if (data.isPresent()) {
			return data.get();
		}
		return null;	
	}
	
	@GetMapping("/api/exam/{examId}/trueOrFalseQuestion")
	public List<BaseExamQuestion> findAllTrueOrFalseQuestionForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if (optionalExam.isPresent()) {
			Exam exam = optionalExam.get();
			List<BaseExamQuestion> questions = exam.getBaseExamQuestion();
			List<BaseExamQuestion> res = new LinkedList<>();
			for (BaseExamQuestion q:questions) {
				if (q instanceof TrueOrFalseQuestion)
					res.add(q);
			}
			return res;
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eid}/trueOrFalseQuestion")
	public TrueOrFalseQuestion createTrueOrFalseQuestion(@PathVariable("eid") int examId, @RequestBody TrueOrFalseQuestion question) {
		Optional<Exam> data = examRepository.findById(examId);
		if (data.isPresent()) {
			Exam exam = data.get();
			question.setExam(exam);
			question.getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
			return repository.save(question);
		}
		return null;

	}

	
	@DeleteMapping("/api/trueOrFalseQuestion/{tId}")
	public void deleteTrueOrFalseQuestion( @PathVariable("tId") int questionId) {
		System.out.println(questionId);
		repository.deleteById(questionId);
	}

	@PutMapping("/api/trueOrFalseQuestion/{tId}")
	public TrueOrFalseQuestion updateTrueOrFalseQuestion(@PathVariable("tId") int questionId, @RequestBody TrueOrFalseQuestion newQuestion) {
		Optional<TrueOrFalseQuestion> data = repository.findById(questionId);
		System.out.println(questionId);
		if (data.isPresent()) {
			TrueOrFalseQuestion question = data.get();
			question.setDescription(newQuestion.getDescription());
			question.setTitle(newQuestion.getTitle());
			question.setPoints(newQuestion.getPoints());
			question.setIsTrue(newQuestion.getIsTrue());
			repository.save(question);
			return  question;		

		}
		return null;
	}

}
