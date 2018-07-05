package webdev.repositories;

import org.springframework.data.repository.CrudRepository;

import webdev.models.FillInTheBlankQuestion;

public interface FillInTheBlankQuestionRepository extends CrudRepository<FillInTheBlankQuestion, Integer> {

}
