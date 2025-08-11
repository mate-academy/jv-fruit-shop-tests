package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.report.ReportGeneratorImpl;
import core.basesyntax.storage.FruitShop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReportEmptyStorage_returnsOnlyHeader() {
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void getReportStorageWithFruits_returnsHeaderAndData() {
        FruitShop.storage.put("Apple", 10);
        FruitShop.storage.put("Banana", 5);

        String report = reportGenerator.getReport();

        assertTrue(report.startsWith("fruit,quantity" + System.lineSeparator()));
        assertTrue(report.contains("Apple,10"));
        assertTrue(report.contains("Banana,5"));

        assertTrue(report.endsWith(System.lineSeparator()));
    }

    @AfterEach
    void tearDown() {
        FruitShop.storage.clear();
    }
}
