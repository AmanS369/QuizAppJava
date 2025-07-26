package com.aman.quiz.service;

import com.aman.quiz.model.Question;
import com.aman.quiz.dao.QuestionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> questions = questionDao.findAll();
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {

        try {
            List<Question> questions =  questionDao.findByCategoryIgnoreCase(category);
            return new ResponseEntity<>(questions, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error while fetching questions for category: {}", category, e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {

        try {
            questionDao.save(question);
            return new ResponseEntity<>("Added Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error while adding questions : {}", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            questionDao.deleteById(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while deleting questions : {}", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<String> updateQuestion( Question question) {
        try {
            if(questionDao.existsById(question.getId())){
                questionDao.save(question);
                return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
            }else    return new ResponseEntity<>("ID doesnt Exist", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error("Error while Updating questions : {}", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
