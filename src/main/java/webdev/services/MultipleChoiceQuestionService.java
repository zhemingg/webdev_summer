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
import webdev.models.MultipleChoiceQuestion;
import webdev.repositories.ExamRepository;
import webdev.repositories.MultipleChoiceQuestionRepository;

@RestController
public class MultipleChoiceQuestionService {
	@Autowired
	MultipleChoiceQuestionRepository repository;
	

	@Autowired
	ExamRepository examRepository;

	@GetMapping("/api/multipleChoiceQuestion")
	public List<MultipleChoiceQuestion> findAllMultipleChoiceQuestion() {
		return (List<MultipleChoiceQuestion>) repository.findAll();
	}
	
	@GetMapping("/api/multipleChoiceQuestion/{bid}")
	public MultipleChoiceQuestion findMultipleChoiceQuestionById(@PathVariable("bid") int id) {			
		Optional<MultipleChoiceQuestion> data =  repository.findById(id);		
		if (data.isPresent()) {
			return data.get();
		}
		return null;	
	}
	
	@GetMapping("/api/exam/{examId}/multipleChoiceQuestion")
	public List<BaseExamQuestion> findAllMultipleChoiceQuestionForExam(@PathVariable("examId") int examId) {
		Optional<Exam> optionalExam = examRepository.findById(examId);
		if (optionalExam.isPresent()) {
			Exam exam = optionalExam.get();
			List<BaseExamQuestion> questions = exam.getBaseExamQuestion();
			List<BaseExamQuestion> res = new LinkedList<>();
			for (BaseExamQuestion q:questions) {
				if (q instanceof MultipleChoiceQuestion)
					res.add(q);
			}
			
			return res;
		}
		return null;
	}
	
	@PostMapping("/api/exam/{eid}/multipleChoiceQuestion")
	public MultipleChoiceQuestion createMultipleChoiceQuestion(@PathVariable("eid") int examId, @RequestBody MultipleChoiceQuestion multipleChoiceQuestion) {
		Optional<Exam> data = examRepository.findById(examId);
		if (data.isPresent()) {
			System.out.println("create");
			Exam exam = data.get();
			multipleChoiceQuestion.setExam(exam);
			multipleChoiceQuestion.getExam().getTopic().getLesson().getModule().getCourse().setModified(new Date());
			return repository.save(multipleChoiceQuestion);
		}
		return null;

	}

	
	@DeleteMapping("/api/multipleChoiceQuestion/{mId}")
	public void deleteMultipleChoiceQuestion( @PathVariable("mId") int multipleChoiceQuestionId) {
		repository.deleteById(multipleChoiceQuestionId);
	}

	@PutMapping("/api/multipleChoiceQuestion/{mId}")
	public MultipleChoiceQuestion updateMultipleChoiceQuestion(@PathVariable("mId") int multipleChoiceQuestionId, @RequestBody MultipleChoiceQuestion newMultipleChoiceQuestion) {
		Optional<MultipleChoiceQuestion> data = repository.findById(multipleChoiceQuestionId);
		if (data.isPresent()) {
			MultipleChoiceQuestion multipleChoiceQuestion = data.get();
			multipleChoiceQuestion.setDescription(newMultipleChoiceQuestion.getDescription());
			multipleChoiceQuestion.setTitle(newMultipleChoiceQuestion.getTitle());
			multipleChoiceQuestion.setPoints(newMultipleChoiceQuestion.getPoints());
			multipleChoiceQuestion.setChoices(newMultipleChoiceQuestion.getChoices());
			multipleChoiceQuestion.setCorrectChoice(newMultipleChoiceQuestion.getCorrectChoice());
			repository.save(multipleChoiceQuestion);
			return multipleChoiceQuestion;
		}
		return null;
	}

}
