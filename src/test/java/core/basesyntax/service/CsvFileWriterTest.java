package core.basesyntax.service;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CsvFileWriterTest {
    private static final String validPathToFile
            = "src/main/java/core/basesyntax/files/activities.csv";
    private static final String invalidPathToFile
            = "src/main/java/core/files/activities.csv";
    private static final String InvalidPathMessage
            = "Can't write into file: ";
    private CsvFileWriter csvFileWriter;
    private Map<String, Integer> report;

    @BeforeEach
    void setUp() {
        csvFileWriter = new CsvFileWriterImpl();
        report = new HashMap<>();
        report.put("banana", 10);
        report.put("apple", 10);
    }

    @Test
    void writeWorkability_Ok() {
        csvFileWriter.write(validPathToFile, report);
    }

    @Test
    void writeInvalidPath_NotOk() {
        RuntimeException exception
                = Assertions.assertThrows(RuntimeException.class,
                    () -> csvFileWriter.write(invalidPathToFile, report));
        Assertions.assertEquals(InvalidPathMessage + invalidPathToFile,
                exception.getMessage());
    }
}
