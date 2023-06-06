package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        reportService = new ReportServiceImpl();
        Storage.fruitStorage.clear();
    }

    @Test
    public void generateReport_emptyStorage_ok() {
        String expected = "fruit,quantity" + System.lineSeparator() + System.lineSeparator();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_nonEmptyStorage_ok() {
        Storage.fruitStorage.put("banana", 14);
        Storage.fruitStorage.put("apple", 70);
        List<String> expected = List.of(
                "fruit,quantity",
                "banana, 14",
                "apple, 70"
                );
        String actual = reportService.createReport();
        List<String> actualLines = actual.lines().collect(Collectors.toList());
        assertEquals(expected, actualLines);
    }
}
