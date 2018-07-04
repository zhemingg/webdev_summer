package webdev.models;

import java.util.List;

//import java.util.List;

import javax.persistence.*;

@Entity
public class MultipleChoiceQuestion extends BaseExamQuestion{
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> choices;
	private String correctChoice;
	public List<String> getChoices() {
		return choices;
	}
	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
	public String getCorrectChoice() {
		return correctChoice;
	}
	public void setCorrectChoice(String correctChoice) {
		this.correctChoice = correctChoice;
	}

}
