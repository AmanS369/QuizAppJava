package com.aman.quiz.dao;

import com.aman.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
     List<Question> findByCategoryIgnoreCase(String category);

    @Query(value = "SELECT * FROM question WHERE LOWER(category) = LOWER(:category) ORDER BY RANDOM() LIMIT :noOfQuestions", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int noOfQuestions);

}