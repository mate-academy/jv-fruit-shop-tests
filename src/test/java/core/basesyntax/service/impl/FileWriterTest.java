package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileWriterTest {
    private static final String VALID_FILE_PATH = "src/test/resources/report.csv";
    private static final String INVALID_FILE_PATH = "test/report.csv";
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void writeReportToFile_ValidData_Ok() throws IOException {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator() + "apple,30" + System.lineSeparator();
        fileWriter.writeReportToFile(VALID_FILE_PATH, report);
        String actual = Files.readString(Path.of(VALID_FILE_PATH));
        assertEquals(report, actual);
    }

    @Test
    void writeReportToFile_InvalidFilePath_NotOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator() + "apple,30" + System.lineSeparator();
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeReportToFile(INVALID_FILE_PATH, report));
    }
}
