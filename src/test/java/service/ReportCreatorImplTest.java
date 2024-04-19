package service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private ReportCreatorImpl reportCreatorService;

    @BeforeEach
    void setUp() {
        reportCreatorService = new ReportCreatorImpl();
        Storage.fruits.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void createReport_ok() {

        String expected = "fruit,quantity\nbanana,20\napple,71\n";

        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 71);

        String actual = reportCreatorService.createReport();

        assertEquals(expected, actual, "The generated report "
                + "should match the expected CSV format.");
    }
}
