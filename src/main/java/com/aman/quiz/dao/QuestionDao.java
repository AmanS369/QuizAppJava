package com.aman.quiz.dao;

import com.aman.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {
    // Option 1: Case-insensitive query using LOWER function
//    @Query("SELECT q FROM Question q WHERE LOWER(q.category) = LOWER(:category)")
//    List<Question> findByCategory(String category);

    // Alternative Option 2: Using Spring Data JPA method naming (case-insensitive)
     List<Question> findByCategoryIgnoreCase(String category);

    @Query(value = "SELECT * FROM question WHERE LOWER(category) = LOWER(:category) ORDER BY RANDOM() LIMIT :noOfQuestions", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int noOfQuestions);

}