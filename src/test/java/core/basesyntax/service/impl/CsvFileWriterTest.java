package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String REPORT = "fruit,quantity"
            + System.lineSeparator()
            + "banana,152"
            + System.lineSeparator()
            + "apple,90"
            + System.lineSeparator();
    private static final String TEST_FILE_PATH = "test.csv";
    private static final String INCORRECT_TEST_FILE_PATH = "123/test.csv";
    private static final String DATA_TO_WRITE = "Java";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new CsvFileWriter();
    }

    @Test
    void writeToFile_withValidPath_ok() {
        fileWriter.writeToFile(REPORT, TEST_FILE_PATH);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Cant read data from file:", e);
        }
        assertEquals(REPORT, builder.toString());
    }

    @Test
    void writeToFile_withInvalidPath_notOk() {
        assertThrows(RuntimeException.class, () ->
                fileWriter.writeToFile(DATA_TO_WRITE, INCORRECT_TEST_FILE_PATH));
    }

    @AfterEach
    void tearDown() {
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}
