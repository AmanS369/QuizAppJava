package com.aman.quiz.service;

import com.aman.quiz.dao.QuestionDao;
import com.aman.quiz.dao.QuizDao;
import com.aman.quiz.model.Question;
import com.aman.quiz.model.Quiz;
import com.aman.quiz.model.Response;
import com.aman.quiz.wrapper.QuestionWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int noOfQuestions, String title) {
        try {
            List<Question> questions = questionDao.findRandomQuestionByCategory(category, noOfQuestions);
            Quiz quiz = new Quiz();
            quiz.setQuizTitle(title);
            quiz.setQuestionList(questions);
            quizDao.save(quiz);
            return new ResponseEntity<>("New Quiz Created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(Integer id) {
        try {
            Optional<Quiz> quizOptional = quizDao.findById(id);

            if (quizOptional.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            List<Question> questionsFromQuiz = quizOptional.get().getQuestionList();
            List<QuestionWrapper> questionForUser = new ArrayList<>();

            for (Question q : questionsFromQuiz) {
                QuestionWrapper qw = new QuestionWrapper(
                        q.getId(),
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4()
                );
                questionForUser.add(qw);
            }

            return new ResponseEntity<>(questionForUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        try {
            Quiz quiz = quizDao.findById(id).get();
            List<Question> questionsFromQuiz = quiz.getQuestionList();

            Integer finalScore=0;
            int i = 0;
            for(Response r : responses){
                if(r.getResponse().equals(questionsFromQuiz.get(i).getRightAnswer()))
                finalScore +=1;
                i++;
            }

            return new ResponseEntity<>(finalScore, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}