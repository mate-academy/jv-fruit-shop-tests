package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.CsvFileWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvFileWriterTest {
    private static final String REPORT_FILE_LOCATION = "src/main/resources/report.csv";
    private static final String WRONG_REPORT_FILE_LOCATION = "src/main/resourcess/report.csv";
    private FileWriter fileWriter;
    private File reportFile;
    private String expected;

    @BeforeEach
    void setUp() {
        fileWriter = new CsvFileWriter();
        reportFile = new File(REPORT_FILE_LOCATION);
        expected = "\"I want wo write this data :\""
                + "apple 1050";
    }

    @AfterEach
    void tearDown() {
        boolean delete = reportFile.delete();
    }

    @Test
    void writeDataToFile_report_ok() {
        fileWriter.writeDataToFile(expected, REPORT_FILE_LOCATION);
        String actual;
        try {
            actual = Files.readString(Path.of(REPORT_FILE_LOCATION));
        } catch (IOException e) {
            throw new RuntimeException("Can't find file: " + REPORT_FILE_LOCATION, e);
        }
        assertEquals(expected, actual);
    }

    @Test
    void writeDataToFile_incorrectFilePath_notOk() {
        assertThrows(RuntimeException.class,
                () -> fileWriter.writeDataToFile(expected, WRONG_REPORT_FILE_LOCATION));
    }
}
