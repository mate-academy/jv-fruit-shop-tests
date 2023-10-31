package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportCreatorService;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportCreatorServiceImplTest {
    private static ReportCreatorService reportCreatorService;

    @BeforeAll
    static void beforeAll() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getFruits().clear();
    }

    @Test
    void createReport_validReport_ok() {
        Storage.getFruits().put("banana", 10);
        Storage.getFruits().put("apple", 40);
        StringBuilder expected = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator()).append("banana,10")
                .append(System.lineSeparator()).append("apple,40");
        assertEquals(expected.toString(), reportCreatorService.createReport());
    }

    @Test
    void createReport_emptyReport_ok() {
        String expected = "fruit,quantity";
        assertEquals(expected, reportCreatorService.createReport());
    }
}
