package webdev.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Exam extends Widget {
	private String title;
	private String description;
	private String points;
	@OneToMany(mappedBy="exam")
	@JsonIgnore
	private List<BaseExamQuestion> questions;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<BaseExamQuestion> getBaseExamQuestion() {
		return questions;
	}
	public void setBaseExamQuestion(List<BaseExamQuestion> questions) {
		this.questions = questions;
	}
}