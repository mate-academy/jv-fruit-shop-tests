package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static ReportCreatorImpl reportCreator;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void createReport_Ok() {
        Map<String, Integer> fruitReport = new HashMap<>();
        fruitReport.put("banana", 20);
        fruitReport.put("apple", 50);
        String expectedReport = "fruit,quantity\nbanana,20\napple,50\n";
        String actualReport = reportCreator.createReport(fruitReport);
        assertEquals(expectedReport, actualReport);
    }
}
