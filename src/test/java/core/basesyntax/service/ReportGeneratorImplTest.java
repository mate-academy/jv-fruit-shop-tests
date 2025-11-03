package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.FruitStorage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static Map<String, Integer> storage;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        storage.put("banana", 30);
        storage.put("apple", 50);
    }

    @Test
    void generateReport_correctPath_isOk() throws IOException {
        String testPath = "src/test/resources/testReport.csv";

        reportGenerator.generateReport(storage, testPath);

        File reportFile = new File(testPath);
        assertTrue(reportFile.exists());

        List<String> lines = Files.readAllLines(reportFile.toPath());
        assertEquals("fruit,quantity", lines.get(0));
        assertTrue(lines.contains("banana,30"));
        assertTrue(lines.contains("apple,50"));

        Files.deleteIfExists(reportFile.toPath());
    }

    @Test
    void generateReport_invalidFilePath_throwException() {
        String invalidPath = "src/main/unknownPath/finalReporttt.csv";
        assertThrows(RuntimeException.class,
                () -> reportGenerator.generateReport(FruitStorage.getStorage(), invalidPath));
    }
}
