package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.implementation.CsvFileWriterServiceImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String FIRST_ROW = "fruit, quantity";
    private static final String SECOND_ROW = "banana, 152";
    private static final String REPORT = FIRST_ROW + System.lineSeparator() + SECOND_ROW;
    private static final String OUTPUT_TEST_FILE_PATH = "src/test/resources/testOutput.csv";
    private static final String INVALID_OUTPUT_FILE_PATH = "src/main/test/resources/testOutput.csv";

    private CsvFileWriterService writerService = new CsvFileWriterServiceImpl();

    @Test
    public void writeToFile_Ok() {
        writerService.write(REPORT, OUTPUT_TEST_FILE_PATH);
        File file = new File(OUTPUT_TEST_FILE_PATH);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            assertEquals("First row is invalid " + line, line, FIRST_ROW);
            line = bufferedReader.readLine();
            assertEquals("Second row is invalid " + line, line, SECOND_ROW);
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + file);
        }
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_not_Ok() {
        writerService.write(REPORT, INVALID_OUTPUT_FILE_PATH);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for invalid path, but it wasn't");
    }
}
