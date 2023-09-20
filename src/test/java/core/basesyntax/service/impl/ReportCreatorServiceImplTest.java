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
    static void setUp() {
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
        String expectedString = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,40";
        assertEquals(expectedString, reportCreatorService.createReport());
    }

    @Test
    void createReport_emptyReport_ok() {
        String expectedString = "fruit,quantity";
        assertEquals(expectedString, reportCreatorService.createReport());
    }
}
