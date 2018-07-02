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

import webdev.models.Exam;

import webdev.models.Topic;
//import webdev.models.MultipleChoiceQuestion;
//import webdev.models.Question;
//import webdev.models.TrueFalseQuestion;
import webdev.models.Widget;
import webdev.repositories.ExamRepository;

import webdev.repositories.TopicRepository;
//import webdev.repositories.MultipleChoicesQuestionRepository;
//import webdev.repositories.TrueFalseQuestionRepository;


@RestController
@CrossOrigin(origins = "*")
public class ExamService {
	@Autowired
	ExamRepository examRepository;
	
	@Autowired
	TopicRepository topicRepository;
//	@Autowired
//	TrueFalseQuestionRepository trueFalseRepository;
//	@Autowired
//	MultipleChoicesQuestionRepository mutiRepo;

//	@GetMapping("/api/multi/{questionId}")
//	public MultipleChoiceQuestion findMultiQuestionById(@PathVariable("questionId") int questionId) {
//		Optional<MultipleChoiceQuestion> optional = mutiRepo.findById(questionId);
//		if(optional.isPresent()) {
//			return optional.get();
//		}
//		return null;
//	}
//
//	@GetMapping("/api/truefalse/{questionId}")
//	public TrueFalseQuestion findTrueFalseQuestionById(@PathVariable("questionId") int questionId) {
//		Optional<TrueFalseQuestion> optional = trueFalseRepository.findById(questionId);
//		if(optional.isPresent()) {
//			return optional.get();
//		}
//		return null;
//	}
//	
//	@GetMapping("/api/exam/{examId}/question")
//	public List<Question> findAllQuestionsForExam(@PathVariable("examId") int examId) {
//		Optional<Exam> optionalExam = examRepository.findById(examId);
//		if(optionalExam.isPresent()) {
//			Exam exam = optionalExam.get();
//			List<Question> questions = exam.getQuestions();
//			int count = questions.size();
//			return questions;
//		}
//		return null;
//	}
	
	@GetMapping("/api/exam")
	public List<Exam> findAllExam() {
		return (List<Exam>)examRepository.findAll();
	}
	
	@GetMapping("/api/exam/{eid}")
	public Exam findExamById(@PathVariable("eid") int id) {			
		Optional<Exam> data =  examRepository.findById(id);		
		if (data.isPresent()) {
			return data.get();
		}
		return null;	
	}
	
	@GetMapping("/api/topic/{topicId}/exam")
	public List<Widget> findAllExamForTopic(@PathVariable("topicId") int topicId) {
		Optional<Topic> optionalTopic = topicRepository.findById(topicId);
		if (optionalTopic.isPresent()) {
			Topic topic = optionalTopic.get();
			List<Widget> widgets = topic.getWidgets();
			List<Widget> res = new LinkedList<>();
			for (Widget w:widgets) {
				if (w instanceof Exam)
					res.add(w);
			}
			return res;
		}
		return null;
	}
	
	@PostMapping("/api/topic/{tid}/exam")
	public Exam createAssignment(@PathVariable("tid") int topicId, @RequestBody Exam exam) {
		Optional<Topic> data = topicRepository.findById(topicId);
		if (data.isPresent()) {
			Topic topic = data.get();
			exam.setTopic(topic);
			exam.getTopic().getLesson().getModule().getCourse().setModified(new Date());
			return examRepository.save(exam);
		}
		return null;

	}

	
	@DeleteMapping("/api/exam/{eId}")
	public void deleteAssignment( @PathVariable("eId") int examId) {
		examRepository.deleteById(examId);
	}

	@PutMapping("/api/exam/{eId}")
	public Exam updateAssignemnt(@PathVariable("eId") int examId, @RequestBody Exam newExam) {
		Optional<Exam> data = examRepository.findById(examId);
		if (data.isPresent()) {
			Exam exam = data.get();
			exam.setDescription(newExam.getDescription());
			exam.setTitle(newExam.getTitle());
			exam.setPoints(newExam.getPoints());
			exam.setBaseExamQuestion(newExam.getBaseExamQuestion());
			examRepository.save(exam);
			return exam;
		}
		return null;
	}
}
