package core.basesyntax.service.reportgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.fruits.clear();
    }

    @Test
    public void testGetReportWithEmptyStorage() {
        List<String> expected = List.of("fruit,quantity");
        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    public void testGetReportWithNonEmptyStorage() {
        Storage.fruits.put(new Fruit("apple"), 10);
        Storage.fruits.put(new Fruit("banana"), 20);

        List<String> expected = List.of("fruit,quantity", "banana,20", "apple,10");
        List<String> actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
