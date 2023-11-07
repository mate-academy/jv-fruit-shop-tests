package core.basesyntax.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReader implements ReaderFromFile {
    private static final String EXCEPTION_FIND_FILE_MESSAGE = "Can't find file by path: ";
    private static final String EXCEPTION_EMPTY_FILE_MESSAGE = "The input file is empty";

    @Override
    public List<String> readFile(String filePath) {
        File file = new File(filePath);
        List<String> inputData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String value = reader.readLine();
            while (value != null) {
                inputData.add(value);
                value = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(EXCEPTION_FIND_FILE_MESSAGE + filePath, e);
        }
        if (inputData.size() < 2) {
            throw new RuntimeException(EXCEPTION_EMPTY_FILE_MESSAGE);
        }
        return inputData;
    }
}
