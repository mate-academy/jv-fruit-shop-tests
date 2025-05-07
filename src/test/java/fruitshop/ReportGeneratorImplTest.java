package fruitshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fruitshop.db.Storage;
import fruitshop.service.ReportGenerator;
import fruitshop.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @Test
    void getReport_nonEmptyStorage_ok() {
        Storage.put("apple", 50);
        Storage.put("banana", 30);

        String report = reportGenerator.getReport();
        String[] lines = report.split(System.lineSeparator());

        assertEquals(3, lines.length);
        assertEquals("fruit,quantity", lines[0]);

        boolean hasApple = false;
        boolean hasBanana = false;

        for (String line : lines) {
            if (line.equals("apple,50")) {
                hasApple = true;
            }
            if (line.equals("banana,30")) {
                hasBanana = true;
            }
        }

        assertTrue(hasApple, "Report should contain 'apple,50'");
        assertTrue(hasBanana, "Report should contain 'banana,30'");
    }

    @Test
    void getReport_emptyStorage_headerOnly() {
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity", report);
    }
}
