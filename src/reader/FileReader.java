package reader;

import model.Frage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

public class FileReader {
    /**
     * Reads data from file
     * @return list of questions
     * @throws IOException
     * @throws ParseException
     */
    public List<Frage> initialiseData() throws IOException, ParseException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Path path = Paths.get("questions.json");
        List<Frage> questionsFromJson;
        try (Reader reader = Files.newBufferedReader(path)) {
            questionsFromJson = gson.fromJson(reader,
                    new TypeToken<List<Frage>>(){}.getType());
        }
        return questionsFromJson;
    }
}
