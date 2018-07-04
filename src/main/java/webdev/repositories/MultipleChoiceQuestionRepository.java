package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.MultipleChoiceQuestion;

public interface MultipleChoiceQuestionRepository extends CrudRepository<MultipleChoiceQuestion, Integer> {
}

