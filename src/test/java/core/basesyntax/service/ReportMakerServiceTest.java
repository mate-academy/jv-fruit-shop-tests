package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportMakerServiceImpl;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportMakerServiceTest {
    private static final String HEADER = "fruit,quantity";
    private static final String COMA_SEPARATOR = ",";
    private static ReportMakerService reportMakerService;
    private static List<String> expectedReport;

    @Before
    public void setUp() {
        Storage.fruitMap.put("banana", 152);
        Storage.fruitMap.put("apple", 90);
        reportMakerService = new ReportMakerServiceImpl(COMA_SEPARATOR, HEADER);
        expectedReport = List.of(HEADER, "banana,152", "apple,90");
    }

    @Test
    public void testMakeReport_ok() {
        List<String> actualReport = reportMakerService.makeReport();
        assertEquals(expectedReport, actualReport);
    }

    @After
    public void mapClear() {
        Storage.fruitMap.clear();
    }
}
