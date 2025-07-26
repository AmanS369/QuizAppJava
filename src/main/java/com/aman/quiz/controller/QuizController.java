package com.aman.quiz.controller;


import com.aman.quiz.model.Question;
import com.aman.quiz.model.Response;
import com.aman.quiz.service.QuizService;
import com.aman.quiz.wrapper.QuestionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    QuizService quizService;
    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int noOfQuestions,@RequestParam String title){
        return quizService.createQuiz(category,noOfQuestions,title);

    }

    @GetMapping("/getQuiz/{id}")
    public ResponseEntity<List<QuestionWrapper>> createQuiz(@PathVariable Integer id){
        return quizService.getQuiz(id);

    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id,responses);

    }
}
