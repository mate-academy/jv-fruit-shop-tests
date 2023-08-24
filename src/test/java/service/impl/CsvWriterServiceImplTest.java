package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;
import service.CsvWriterService;

public class CsvWriterServiceImplTest {
    private static final CsvWriterService WRITER_SERVICE = new CsvWriterServiceImpl();
    private static final String TO_FILE_NAME = "src/test/java/test_files/toFile.csv";
    private static final String NO_FILE_NAME = "";
    private static final String REPORT_MESSAGE = "test message";

    @Test
    public void defaultFile_Ok() {
        WRITER_SERVICE.writeToFile(TO_FILE_NAME, REPORT_MESSAGE);

        try (BufferedReader reader = new BufferedReader(new FileReader(TO_FILE_NAME))) {
            assertEquals(REPORT_MESSAGE,
                    reader.readLine(),
                    "File content does not match the expected message");
        } catch (IOException e) {
            throw new RuntimeException("No such file ", e);
        }
    }

    @Test
    public void noFile_notOk() {
        assertThrows(RuntimeException.class, () ->
                WRITER_SERVICE.writeToFile(NO_FILE_NAME, REPORT_MESSAGE));
    }

}
