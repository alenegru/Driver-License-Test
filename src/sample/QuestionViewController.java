package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.Frage;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class QuestionViewController {
    public int timeRemaining = 1799;
    public int secRemaining = 59;
    public Integer remainingQ = 24;
    public Integer counterQ = 0;
    public static Controller controller;
    Timeline timeline;

    //am facut static controller-ul pentru a-l putea folosi pe acelasi si in FinishViewController
    static {
        try {
            controller = new Controller();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Frage> questions;
    @FXML
    public Label correctAnswers;
    @FXML
    public Label wrongAnswers;
    @FXML
    public Label remainingQuestions;
    @FXML
    public Label timer;
    @FXML
    public Label questionNumber;
    @FXML
    public Label question;
    @FXML
    public CheckBox optionA;
    @FXML
    public CheckBox optionB;
    @FXML
    public CheckBox optionC;
    @FXML
    public Button next;
    @FXML
    public Button submit;

    public QuestionViewController() throws IOException, ParseException {
    }


    /**
     *Get questions from the questionaire, show the first question and create a timer using Timeline
     * @throws IOException
     * @throws ParseException
     */
    @FXML
    public void initialize() throws IOException, ParseException {
        questions = Controller.controllerFragebogen.fragebogen.getQuestions();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (secRemaining == 0) {
                timer.setText(String.valueOf(timeRemaining/60) + ":00");
                secRemaining = 60;
            } else if (secRemaining < 10) {
                timer.setText(String.valueOf(timeRemaining/60) + ":0" + String.valueOf(secRemaining));
            } else {
                timer.setText(String.valueOf(timeRemaining/60) + ":" + String.valueOf(secRemaining));
            }
            secRemaining = secRemaining - 1;
            timeRemaining = timeRemaining - 1;
        }));
        timeline.setCycleCount(1800);
        timeline.play();

        timeline.setOnFinished(event -> Platform.runLater(()->{
            //close current window
            Main.primaryStage.fireEvent(new WindowEvent(Main.primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));

            //change scene to finish.fxml
            try {
                goToFinish();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }));

        submit.setVisible(false);
        correctAnswers.setText("Raspunsuri corecte : 0");
        wrongAnswers.setText("Raspunsuri gresite : 0");
        remainingQuestions.setText("Intrebari ramase : 25");
        //timer
        questionNumber.setText("Nr. intrebarii: 1");
        question.setText(questions.get(counterQ).getQuestion());
        optionA.setText(questions.get(counterQ).getPossibleAnswers().get(0));
        optionB.setText(questions.get(counterQ).getPossibleAnswers().get(1));
        optionC.setText(questions.get(counterQ).getPossibleAnswers().get(2));
    }

    /**
     * Changes scene for primary stage
     * @throws IOException
     */
    @FXML
    public void goToFinish() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("finish.fxml"));
        Main.primaryStage.setTitle("Auto Questionnaire");
        Main.primaryStage.setScene(new Scene(root));
        Main.primaryStage.show();
    }

    /**
     * Changes stage
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void changeStage(ActionEvent actionEvent) throws IOException {
        Parent finishRoot = FXMLLoader.load(getClass().getResource("finish.fxml"));
        Scene questionScene = new Scene(finishRoot);
        Main.primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Main.primaryStage.setScene(questionScene);
        Main.primaryStage.show();
    }

    /**
     * Take the answers from the checkboxes and update labels
     * @param actionEvent next button
     * @throws IOException
     */
    @FXML
    public void next(ActionEvent actionEvent) throws IOException {
        if (counterQ == 24) {
            next.setVisible(false);
            submit.setVisible(true);
        }
        //daca am ajuns la ultima intrebare --> nu mai face next
        if (counterQ == 25)
            return;

        //cazul in care a gresit mai mult de 5 intrebari --> ma duce la finish
        if (Controller.controllerFragebogen.verifyNumberIncorrectAnswers()) {
            changeStage(actionEvent);
            timeline.stop();
        }


        if (handleOptions(questions.get(counterQ))){
            Controller.controllerFragebogen.fragebogen.setNumberOfCorrectAnswers(Controller.controllerFragebogen.fragebogen.getNumberOfCorrectAnswers()+1);
        }
        else {
            Controller.controllerFragebogen.fragebogen.setNumberOfIncorrectAnswers(Controller.controllerFragebogen.fragebogen.getNumberOfIncorrectAnswers()+1);
        }

        optionA.setSelected(false);
        optionB.setSelected(false);
        optionC.setSelected(false);
        counterQ+=1;
        correctAnswers.setText("Raspunsuri corecte: " + String.valueOf(Controller.controllerFragebogen.fragebogen.getNumberOfCorrectAnswers()));
        wrongAnswers.setText("Raspunsuri gresite: " + String.valueOf(Controller.controllerFragebogen.fragebogen.getNumberOfIncorrectAnswers()));
        remainingQuestions.setText("Intrebari ramase: " + remainingQ.toString());
        //timer
        questionNumber.setText("Nr. intrebarii: " + String.valueOf(counterQ + 1));
        question.setText(questions.get(counterQ).getQuestion());
        optionA.setText(questions.get(counterQ).getPossibleAnswers().get(0));
        optionB.setText(questions.get(counterQ).getPossibleAnswers().get(1));
        optionC.setText(questions.get(counterQ).getPossibleAnswers().get(2));
        remainingQ -= 1;
    }

    /**
     * fac o lista cu raspunsurile primite de user si o compar cu lista de raspunsuri corecte
     */
    private boolean handleOptions(Frage frage) {
        List<String> answersFromCheckbox = new ArrayList<>();
        if (optionA.isSelected())
            answersFromCheckbox.add(frage.getPossibleAnswers().get(0));
        if (optionB.isSelected())
            answersFromCheckbox.add(frage.getPossibleAnswers().get(1));
        if (optionC.isSelected())
            answersFromCheckbox.add(frage.getPossibleAnswers().get(2));

        return answersFromCheckbox.equals(frage.getCorrectAnswers()); //toate intrebarile corecte
    }

    /**
     * Change stage when user clicks submit button
     * @param actionEvent submit button
     * @throws IOException
     * @throws ParseException
     */
    @FXML
    public void submit(ActionEvent actionEvent) throws IOException, ParseException {
        changeStage(actionEvent);
    }
}
