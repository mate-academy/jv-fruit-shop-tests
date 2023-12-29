package core.basesyntax.database;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportFileWriterTest {
    private static final String FRUIT_NAME = "Banana";
    private static final String SECOND_FRUIT_NAME = "Apple";
    private static final ReportFileWriter reportFileWriter = new ReportFileWriterImpl();
    private static final String filePath = "src/test/resources/report.csv";
    private static Map<String, Integer> reportFile = new HashMap<>();

    @AfterAll
    static void afterAll() {
        reportFileWriter.writeToFile(reportFile,filePath);
    }

    @AfterEach
    void tearDown() {
        FruitStorage.storageData.clear();
        reportFile = new HashMap<>();
    }

    @Test
    void reportFileWriter_validData_Ok() {
        reportFile = Map.of(FRUIT_NAME,20, SECOND_FRUIT_NAME,30);
        reportFileWriter.writeToFile(reportFile, filePath);
    }

    @Test
    void reportFileWriter_negativeQuantity_notOk() {
        reportFile.put(FRUIT_NAME, -20);
        assertThrows(RuntimeException.class,
                () -> reportFileWriter.writeToFile(reportFile,filePath));
    }

    @Test
    void reportFileWriter_wrongPath_notOk() {
        String wrongPath = "wrongPath/report.csv";
        assertThrows(RuntimeException.class,
                () -> reportFileWriter.writeToFile(reportFile,wrongPath));
    }
}
