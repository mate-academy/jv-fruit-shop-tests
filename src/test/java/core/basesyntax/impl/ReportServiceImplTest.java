package core.basesyntax.impl;

import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String[] head = {
            "fruits", "quantity\r\n"
    };
    private ReportService reportService;

    private List<String[]> convertedList;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        convertedList = new ArrayList<>();
        String[] value = {"banana", "152\r\n"};
        String[] value2 = {"apple,90"};
        convertedList.add(head);
        convertedList.add(value);
        convertedList.add(value2);
    }

    @Test
    public void reportService_createReport_Ok() {
        String expected = "fruits,quantity\r\n"
                + "banana,152\r\n" + "apple,90";
        String actual = reportService.createReport(convertedList);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void reportService_emptyConvertedList() {
        List<String[]> convertedList = new ArrayList<>();
        reportService.createReport(convertedList);
    }

    @Test(expected = RuntimeException.class)
    public void reportService_covetedListNull_notOk() {
        reportService.createReport(null);
    }
}
