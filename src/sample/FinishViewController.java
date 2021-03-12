package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

public class FinishViewController {
    Stage window;
    public static QuestionViewController questionViewController;

    static {
        try {
            questionViewController = new QuestionViewController();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public Label result;
    @FXML
    public Label correctAnswers;
    @FXML
    public Label wrongAnswers;
    @FXML
    public Button restart;

    public FinishViewController() throws IOException, ParseException {
    }

    /**
     *
     */
    @FXML
    public void initialize() {
        if (Controller.controllerFragebogen.fragebogen.getNumberOfCorrectAnswers()>=22)
            result.setText("Felicitari! Esti ADMIS.");
        else
            result.setText("Ai fost declarat RESPINS. Incearca din nou.");

        correctAnswers.setText("Raspunsuri corecte: " + Controller.controllerFragebogen.fragebogen.getNumberOfCorrectAnswers());
        wrongAnswers.setText("Raspunsuri gresite: " + Controller.controllerFragebogen.fragebogen.getNumberOfIncorrectAnswers());
        //resetez nr de raspunsuri corecte si nr rasp gresite la 0
        Controller.controllerFragebogen.fragebogen.setNumberOfCorrectAnswers(0);
        Controller.controllerFragebogen.fragebogen.setNumberOfIncorrectAnswers(0);
    }

    /**
     *
     * @param actionEvent button
     * @throws IOException for file
     */
    @FXML
    public void restart(ActionEvent actionEvent) throws IOException {
        Parent startRoot = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene questionScene = new Scene(startRoot);
        window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(questionScene);
        window.show();
    }
}
