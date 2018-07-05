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
import webdev.models.FillInTheBlankQuestion;
import webdev.repositories.ExamRepository;
import webdev.repositories.FillInTheBlankQuestionRepository;

@RestController
public class FillInTheBlankQuestionService {
	@Autowired
	FillInTheBlankQuestionRepository repository;
	

	@Autowired
	ExamRepository examRepository;

	@GetMapping("/api/fillInTheBlankQuestion")
	public List<FillInTheBlankQuestion> findAllFillInTheBlankQuestion() {
		return (List<FillInTheBlankQuestion>) repository.findAll();
	}
	
	@GetMapping("/api/fillInTheBlankQuestion/{fid}")
	public FillInTheBlankQuestion findFillInTheBlankQuestionById(@PathVariable("fid") int id) {			
		Optional<FillInTheBlankQuestion> data =  repository.findById(id);		
		if (data.isPresent()) {
			return data.get();
		}
		return null;	
	}
	
	@GetMapping("/api/exam/{examId}/fillInTheBlankQuestion")
	public List<BaseExamQuestion> findAllFillInTheBlankQuestionForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if (optionalExam.isPresent()) {
			Exam exam = optionalExam.get();
			List<BaseExamQuestion> questions = exam.getBaseExamQuestion();
			List<BaseExamQuestion> res = new LinkedList<>();
			for (BaseExamQuestion q:questions) {
				if (q instanceof FillInTheBlankQuestion)
					res.add(q);
			}
			
			return res;
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eid}/fillInTheBlankQuestion")
	public FillInTheBlankQuestion createFillInTheBlankQuestion(@PathVariable("eid") int examId, @RequestBody FillInTheBlankQuestion question) {
		System.out.println(examId);
		Optional<Exam> data = examRepository.findById(examId);
		if (data.isPresent()) {
			System.out.println("create");
			Exam exam = data.get();
			question.setExam(exam);
			question.getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
			return repository.save(question);
		}
		return null;

	}

	
	@DeleteMapping("/api/fillInTheBlankQuestion/{fId}")
	public void deleteFillInTheBlankQuestion( @PathVariable("fId") int questionId) {
		repository.deleteById(questionId);
	}

	@PutMapping("/api/fillInTheBlankQuestion/{mId}")
	public FillInTheBlankQuestion updateFillInTheBlankQuestion(@PathVariable("mId") int questionId, @RequestBody FillInTheBlankQuestion newQuestion) {
		Optional<FillInTheBlankQuestion> data = repository.findById(questionId);
		if (data.isPresent()) {
			FillInTheBlankQuestion question = data.get();
			question.setDescription(newQuestion.getDescription());
			question.setTitle(newQuestion.getTitle());
			question.setPoints(newQuestion.getPoints());
			question.setItems(newQuestion.getItems());
			repository.save(question);
			return  question;		
		}
		return null;
	}

}
