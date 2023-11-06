package core.basesyntax.serviceimpl;

import core.basesyntax.service.DataWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriter implements DataWriter {
    private static final String CANNOT_WRITE_TO_FILE_MESSAGE = "Cannot write to the file: ";
    private static final String NO_SUCH_FILE_MESSAGE = "File does not exist: ";

    @Override
    public boolean writeToFile(String[] report, String pathToFile) {
        File file = new File(pathToFile);
        if (!file.exists()) {
            throw new RuntimeException(NO_SUCH_FILE_MESSAGE + pathToFile);
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            for (String data : report) {
                bufferedWriter.write(data);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(CANNOT_WRITE_TO_FILE_MESSAGE
                    + pathToFile, e);
        }
    }
}
