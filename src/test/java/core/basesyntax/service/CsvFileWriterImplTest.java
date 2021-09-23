package core.basesyntax.service;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileWriterImplTest {
    private static final String validPathToFile
            = "src/main/java/core/basesyntax/files/activities.csv";
    private static final String invalidPathToFile
            = "src/main/java/core/files/activities.csv";
    private static final String InvalidPathMessage
            = "Can't write into file: ";
    private static CsvFileWriter csvFileWriter;
    private static Map<String, Integer> report;

    @BeforeClass
    public static void setUp() {
        csvFileWriter = new CsvFileWriterImpl();
        report = new HashMap<>();
        report.put("banana", 10);
        report.put("apple", 10);
    }

    @Test
    public void writeWorkability_Ok() {
        csvFileWriter.write(validPathToFile, report);
    }

    @Test
    public void writeInvalidPath_NotOk() {
        RuntimeException exception = Assert.assertThrows(RuntimeException.class,
                () -> csvFileWriter.write(invalidPathToFile, report));
        Assert.assertEquals(InvalidPathMessage + invalidPathToFile,
                exception.getMessage());
    }
}
