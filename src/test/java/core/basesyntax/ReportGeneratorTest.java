package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {

    @AfterEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void getReport_ok() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 100);
        String actualReport = reportGenerator.getReport();
        assertTrue(actualReport.contains("fruit,quantity"));
        assertTrue(actualReport.contains("banana,20"));
        assertTrue(actualReport.contains("apple,100"));
    }
}
