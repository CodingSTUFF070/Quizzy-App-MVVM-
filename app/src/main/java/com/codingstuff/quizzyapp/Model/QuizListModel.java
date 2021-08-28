package com.codingstuff.quizzyapp.Model;

import com.google.firebase.firestore.DocumentId;

public class QuizListModel {

    @DocumentId
    private String quizId;
    private String title,image, difficulty;
    private long questions;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public long getQuestions() {
        return questions;
    }

    public void setQuestions(long questions) {
        this.questions = questions;
    }

    public QuizListModel(){}
    public QuizListModel(String quizId, String title, String image, String difficulty, long questions) {
        this.quizId = quizId;
        this.title = title;
        this.image = image;
        this.difficulty = difficulty;
        this.questions = questions;
    }
}
