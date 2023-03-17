package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportMakerService;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerServiceImplTest {

    private static final String HEADER = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static ReportMakerService reportMakerService;
    private static List<String> expectedReport;

    @BeforeClass
    public void beforeAll() {
        reportMakerService = new ReportMakerServiceImpl(HEADER,SEPARATOR);
        expectedReport = new ArrayList<>();

        expectedReport.add("fruit,quantity");
        expectedReport.add("banana,152");
        expectedReport.add("apple,90");

        Storage.fruitMap.put("banana",152);
        Storage.fruitMap.put("apple",90);
    }

    @Test
    public void makeReport_ok() {
        List<String> actualReport = reportMakerService.createReport();
        assertEquals(expectedReport,actualReport);
    }
}
