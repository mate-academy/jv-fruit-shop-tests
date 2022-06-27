package core.basesyntax.service;

import core.basesyntax.service.impl.CreateReportImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateReportImplTest {
    private static Map<String, Integer> mapForTest;
    private static List<String> actualList;
    private static List<String> expectedList;
    private static CreateReport createReport;

    @Before
    public void setUp() {
        createReport = new CreateReportImpl();
        mapForTest = new HashMap<>();
        mapForTest.put("mango", 25);
        mapForTest.put("pineapple", 5);
    }

    @Test(expected = RuntimeException.class)
    public void createReport_nullInput_notOk() {
        createReport.createReport(null);
    }

    @Test
    public void createReport_validInput_isOk() {
        expectedList = List.of("fruits,quantity", "\r\npineapple,5", "\r\nmango,25");
        actualList = createReport.createReport(mapForTest);
        Assert.assertEquals(expectedList, actualList);
    }
}
