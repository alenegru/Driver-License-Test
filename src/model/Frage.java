package model;

import java.util.List;

public class Frage {
    String question;
    List<String> possibleAnswers;
    List<String> correctAnswers;
    int idQuestion;

    public Frage(int idQuestion, String question, List<String> possibleAnswers, List<String> correctAnswers) {
        this.idQuestion = idQuestion;
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.correctAnswers = correctAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }


    public String toStringQuestion(){
        StringBuilder questionSet = new StringBuilder();
        questionSet = new StringBuilder(idQuestion + ". " + question + "\n");
        for (String possibleAnswer : possibleAnswers)
            questionSet.append(possibleAnswer).append("\n");
        for (String correctAnswer : correctAnswers)
            questionSet.append(correctAnswer).append("\n");
        return questionSet.toString();
    }
}
