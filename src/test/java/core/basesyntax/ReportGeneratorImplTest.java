package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;
import service.ReportGeneratorImpl;

class ReportGeneratorImplTest {
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    void getReport_Ok() {
        Storage.fruitStorage.put("banana", 100);
        Storage.fruitStorage.put("apple", 50);

        String result = reportGenerator.getReport();

        String expectedHeader = "fruit,quantity";
        assertEquals(true, result.contains(expectedHeader));
        assertEquals(true, result.contains("banana,100"));
        assertEquals(true, result.contains("apple,50"));
    }
}
