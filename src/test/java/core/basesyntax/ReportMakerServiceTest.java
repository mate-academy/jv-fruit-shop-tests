package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportMakerService;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportMakerServiceTest {
    private static ReportMakerService reportMakerService;

    @BeforeAll
    public static void initialize() {
        reportMakerService = new ReportMakerServiceImpl();
    }

    @Test
    public void makingReportFromMap_Ok() {
        Map<Fruit, Integer> resultMap = Map.of(new Fruit("banana"), 90, new Fruit("apple"), 100);
        String actual = reportMakerService.getReport(resultMap);
        String expected = "fruit,quantity\nbanana,90\napple,100";
        assertEquals(actual, expected);
    }
}
