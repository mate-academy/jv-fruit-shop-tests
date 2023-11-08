package core.basesyntax.services.reads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReaderImpl implements CsvFileReader {
    private static final String PREFIX = "\\s+";
    private static final String EXCEPTION_TEXT = "Error while reading a file: ";
    private static final String NOT_EXIST = ("File does not exist: ");
    private static final String BE_NULL = "File path cannot be null: ";

    @Override
    public List<String> read(String filePath) {
        List<String> transactions = new ArrayList<>();
        checkFileAndPath(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim().replaceAll(PREFIX, "");
                transactions.add(trimmedLine);
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException(EXCEPTION_TEXT, exception);
        }
        return transactions;
    }

    private void checkFileAndPath(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException(BE_NULL + filePath);
        }

        File file = new File(filePath);
        if (file.length() == 0) {
            throw new IllegalArgumentException(NOT_EXIST + filePath);
        }
    }
}
