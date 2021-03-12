package model;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Fragebogen {
    private static final AtomicInteger count = new AtomicInteger(0);
    private int id;
    private int fragebogenNumber;
    private int numberOfCorrectAnswers;
    private int numberOfIncorrectAnswers;
    private List<Frage> questions;

    public boolean isFilled;

    public Fragebogen(List<Frage> questions) {
        this.id = ThreadLocalRandom.current().nextInt(1000, 9999);
        isFilled = false;
        this.fragebogenNumber = count.incrementAndGet();
        this.numberOfCorrectAnswers = 0;
        this.numberOfIncorrectAnswers = 0;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFragebogenNumber() {
        return fragebogenNumber;
    }

    public void setFragebogenNumber(int fragebogenNumber) {
        this.fragebogenNumber = fragebogenNumber;
    }

    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public int getNumberOfIncorrectAnswers() {
        return numberOfIncorrectAnswers;
    }

    public void setNumberOfIncorrectAnswers(int numberOfIncorrectAnswers) {
        this.numberOfIncorrectAnswers = numberOfIncorrectAnswers;
    }

    public List<Frage> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Frage> questions) {
        this.questions = questions;
    }
}
