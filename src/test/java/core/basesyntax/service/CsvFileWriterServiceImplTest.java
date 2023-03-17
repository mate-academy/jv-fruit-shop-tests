package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.implementation.CsvFileWriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class CsvFileWriterServiceImplTest {
    private static final String FIRST_ROW = "fruit, quantity";
    private static final String SECOND_ROW = "banana, 152";
    private static final String REPORT = FIRST_ROW + System.lineSeparator() + SECOND_ROW;
    private static final String OUTPUT_TEST_FILE_PATH = "src/test/resources/testOutput.csv";
    private static final String INVALID_OUTPUT_FILE_PATH = "src/main/test/resources/testOutput.csv";

    private CsvFileWriterService writerService = new CsvFileWriterServiceImpl();

    @Test
    public void write_validDataToFile_Ok() {
        writerService.write(REPORT, OUTPUT_TEST_FILE_PATH);
        try {
            assertEquals(REPORT, Files.readString(Path.of(OUTPUT_TEST_FILE_PATH)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = RuntimeException.class)
    public void write_invalidData_notOk() {
        writerService.write(REPORT, INVALID_OUTPUT_FILE_PATH);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for invalid path, but it wasn't");
    }
}
