package core.basesyntax.service.fileservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class ReaderImpl implements Reader {
    public static final String EXPECTED_FILE_FORMAT = "scv";
    public static final int DOT_SEPARATOR_INDEX = 1;

    public List<String> readFromInput(String fileName) {
        checkFileName(fileName);
        List<String> recordActivitiesForDay;
        try {
            recordActivitiesForDay = Files.readAllLines(new File(fileName).toPath());
        } catch (IOException e) {
            throw new RuntimeException("Cann`t read file " + fileName, e);
        }
        if (recordActivitiesForDay.isEmpty()) {
            throw new RuntimeException("The contents of the file cannot be empty");
        }
        return recordActivitiesForDay;
    }

    private void checkFileName(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("Wrong file name" + fileName);
        }
        if (!fileName.split("\\.")[DOT_SEPARATOR_INDEX].equals(EXPECTED_FILE_FORMAT)) {
            throw new RuntimeException("Wrong file format. Expected CSV file");
        }
    }
}
