package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreationService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreationServiceImplTest {
    private static ReportCreationService reportCreatorService;

    @BeforeEach
    void setUp() {
        reportCreatorService = new ReportCreationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void createReport_ok() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add(System.lineSeparator() + "banana,20");
        expected.add(System.lineSeparator() + "apple,71");
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 71);
        List<String> actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }
}