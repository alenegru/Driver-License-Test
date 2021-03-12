package sample;

import ControllerFragebogen.ControllerFragebogen;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Frage;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import reader.FileReader;


public class Controller {
    static FileReader fileReader = new FileReader();
    static List<Frage> questionsList;

    static {
        try {
            questionsList = fileReader.initialiseData();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    static ControllerFragebogen controllerFragebogen = new ControllerFragebogen(questionsList);


    public Controller() throws IOException, ParseException {
        controllerFragebogen.generateQuestionaire();
    }

    /**
     * The method which takes the user from the Start scene to the Quiz scene
     * @param actionEvent
     * @throws IOException
     * @throws ParseException
     */
    @FXML
    protected void startQuiz(ActionEvent actionEvent) throws IOException, ParseException {
        Parent questionRoot = FXMLLoader.load(getClass().getResource("question.fxml"));
        Scene questionScene = new Scene(questionRoot);
        Main.primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Main.primaryStage.setScene(questionScene);
        Main.primaryStage.show();
    }
}
