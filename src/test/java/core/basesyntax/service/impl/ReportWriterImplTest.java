package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportWriterImplTest {
    private static ReportWriter writer;
    private static final String RESULT_FILE_PATH = "src/test/resources/result.csv";

    @BeforeAll
    static void beforeAll() {
        writer = new ReportWriterImpl();
    }

    @Test
    void write_CorrectDataToFile_Ok() {
        String expectedData = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator() + "apple,30";
        writer.writeToFile(expectedData, RESULT_FILE_PATH);
        try {
            String actualData = new String(Files.readAllBytes(Paths.get(RESULT_FILE_PATH)));
            assertEquals(expectedData, actualData);
        } catch (IOException e) {
            throw new RuntimeException("Can't read data from file " + RESULT_FILE_PATH + e);
        }
    }
}
