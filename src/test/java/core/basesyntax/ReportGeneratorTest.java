package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void getReport_emptyStorage_returnsOnlyTitle() {
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity\n", report);
    }

    @Test
    void getReport_storageWithSingleEntry_ok() {
        Storage.add("apple", 50);
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity\napple,50\n", report);
    }

    @Test
    void getReport_storageWithMultipleEntries_ok() {
        Storage.add("apple", 50);
        Storage.add("banana", 30);
        Storage.add("banana", 10);

        String report = reportGenerator.getReport();
        String[] actualLines = report.split("\n");
        String[] expectedLines = {
            "fruit,quantity",
            "apple,50",
            "banana,40"
        };

        assertEquals(Set.of(expectedLines), Set.of(actualLines));
    }
}
