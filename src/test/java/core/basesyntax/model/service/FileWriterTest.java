package core.basesyntax.model.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.FileWriterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {
    private static final String CORRECT_FILE_PATH = "src/test/java/resources/recorded.csv";
    private static final String INCORRECT_FILE_PATH = "resources/empty.csv";
    private static final String CORRECT_DATA = "incorrect";

    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_file_ok() {
        fileWriter.writeToCsv(CORRECT_DATA, CORRECT_FILE_PATH);
    }

    @Test
    void write_filePath_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.writeToCsv(CORRECT_DATA, INCORRECT_FILE_PATH));
    }

    @Test
    void write_data_notOk() {
        assertThrows(NullPointerException.class,
                () -> fileWriter.writeToCsv(null, CORRECT_FILE_PATH));
    }
}
