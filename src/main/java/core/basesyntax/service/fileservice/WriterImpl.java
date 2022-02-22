package core.basesyntax.service.fileservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WriterImpl implements Writer {
    public static final String EXPECTED_FILE_FORMAT = "csv";
    public static final int DOT_SEPARATOR_INDEX = 1;

    public void write(String fileName, String data) {
        checkInputData(fileName, data);
        try {
            Files.write(new File(fileName).toPath(), data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Cann`t write to file " + fileName, e);
        }
    }

    private void checkInputData(String fileName, String data) {
        if (fileName == null || fileName.isEmpty()) {
            throw new RuntimeException("Wrong file name" + fileName);
        }
        if (data == null) {
            throw new RuntimeException("Can`t write null to " + fileName);
        }
        if (!fileName.split("\\.")[DOT_SEPARATOR_INDEX].equals(EXPECTED_FILE_FORMAT)) {
            throw new RuntimeException("Wrong file format. Expected CSV file");
        }
    }
}
