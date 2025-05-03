package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static List<Fruit> input;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
        input = new ArrayList<>();
        input.add(new Fruit("banana", 152));
        input.add(new Fruit("apple", 90));
    }

    @Test
    public void report_service_rightInput_ok() {
        String actual = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String expected = reportService.createReport(input);
        Assert.assertNotNull(actual);
        Assert.assertEquals("Expected " + expected + ", but actual " + actual, expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void report_service_nullInput_notOk() {
        reportService.createReport(null);
    }
}
