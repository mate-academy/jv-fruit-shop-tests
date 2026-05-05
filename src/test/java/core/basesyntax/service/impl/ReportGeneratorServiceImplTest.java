package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorServiceImplTest {
    private final ReportGeneratorServiceImpl report = new ReportGeneratorServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void createReport() {
        Storage.getStorage().put("banana", 200);
        Storage.getStorage().put("apple", 400);
        String actual = report.getReport();
        assertTrue(actual.contains("fruit,quantity"));
        assertTrue(actual.contains("apple,400"));
        assertTrue(actual.contains("banana,200"));
    }
}
