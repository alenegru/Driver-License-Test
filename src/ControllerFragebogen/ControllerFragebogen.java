package ControllerFragebogen;

import model.Frage;
import model.Fragebogen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ControllerFragebogen {
    public List<Frage> questionsList;
    public Fragebogen fragebogen;

    public ControllerFragebogen(List<Frage> questionsList) {
        this.questionsList = questionsList;
    }

    /**
     * Generates a questionaire of 26 random questions from de JSON file
     */
    public void generateQuestionaire() {
        List<Frage> questionsCopy = new ArrayList<>(questionsList);
        Random rand = new Random();

        // create a temporary list for storing
        // selected element
        List<Frage> newList = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            // take a random index between 0 to size
            // of given List
            int randomIndex = rand.nextInt(questionsCopy.size());

            // add element in temporary list
            newList.add(questionsCopy.get(randomIndex));

            // Remove selected element from orginal list
            questionsCopy.remove(randomIndex);
        }
        fragebogen = new Fragebogen(newList);
    }

    public boolean verifyNumberIncorrectAnswers() {
        return fragebogen.getNumberOfIncorrectAnswers() >= 4;
    }
}

