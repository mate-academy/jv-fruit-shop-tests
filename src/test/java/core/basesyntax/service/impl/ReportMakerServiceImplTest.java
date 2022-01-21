package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportMakerService;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerServiceImplTest {
    private static ReportMakerService reportMakerService;

    @BeforeClass
    public static void initialize() {
        reportMakerService = new ReportMakerServiceImpl();
    }

    @Test
    public void makingReportFromMap_Ok() {
        Map<Fruit, Integer> resultMap = Map.of(new Fruit("banana"), 90, new Fruit("apple"), 100);
        String actual = reportMakerService.getReport(resultMap);
        String expected = "fruit,quantity\napple,100\nbanana,90";
        assertEquals(actual, expected);
    }
}
