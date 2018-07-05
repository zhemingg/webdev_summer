package webdev.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class FillInTheBlankQuestion extends BaseExamQuestion {
	@Column
	@ElementCollection(targetClass=String.class)
	private List<String> items;

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}


}
