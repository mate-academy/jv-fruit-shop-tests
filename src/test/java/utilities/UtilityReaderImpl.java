package utilities;

import core.basesyntax.exceptions.ReaderEmptyFileException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilityReaderImpl implements UtilityReader {

    @Override
    public List<String> readFile(String filename) {
        List<String> dataFromFile = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                dataFromFile.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + filename, e);
        }
        if (dataFromFile.isEmpty()) {
            throw new ReaderEmptyFileException(
                    "No data found in the file. Try again with another file...");
        }
        return dataFromFile;
    }

    @Override
    public String getDataFromList(List<String> dataFromFile) {
        StringBuilder dataFromList = new StringBuilder();
        for (int i = 0; i < dataFromFile.size();) {
            while (i < dataFromFile.size() - 1) {
                dataFromList.append(dataFromFile.get(i++)).append(System.lineSeparator());
            }
            dataFromList.append(dataFromFile.get(i++));
        }
        return dataFromList.toString();
    }
}
