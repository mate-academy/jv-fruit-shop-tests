package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreationService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreationServiceImplTest {
    private static ReportCreationService reportCreationService;

    @BeforeEach
    void setUp() {
        reportCreationService = new ReportCreationServiceImpl();
    }

    @Test
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void createReport_ok() {
        List<String> expectedValues = new ArrayList<>();
        expectedValues.add("fruit,quantity");
        expectedValues.add(System.lineSeparator() + "banana,20");
        expectedValues.add(System.lineSeparator() + "apple,71");
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 71);
        List<String> actualValues = reportCreationService.createReport();
        assertEquals(expectedValues, actualValues);
    }
}
