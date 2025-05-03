package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportCreatorServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeEach
    void setUp() {
        reportCreatorService = new ReportCreatorServiceImpl();
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
        expected.add(System.lineSeparator() + "apple,10");
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 10);
        List<String> actual = reportCreatorService.createReport();
        Assertions.assertEquals(expected, actual);
    }
}
