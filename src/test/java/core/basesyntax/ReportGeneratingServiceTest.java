package core.basesyntax;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.service.ReportGeneratingService;
import core.basesyntax.service.impl.ReportGeneratingServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratingServiceTest {
    private static ReportGeneratingService reportGeneratingService;

    @BeforeClass
    public static void setUp() {
        reportGeneratingService = new ReportGeneratingServiceImpl();
    }

    @Test
    public void generateReportForEmptyStorage_ok() {
        List<Fruit> emptyStorage = new ArrayList<>();
        List<String> result = reportGeneratingService.createReport(emptyStorage);
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        Assert.assertEquals(expected,result);
    }

    @Test
    public void generateReportForStorageWithValues_ok() {
        List<Fruit> storage = new ArrayList<>();
        storage.add(new Fruit(Operation.BALANCE, "banana", 100));
        storage.add(new Fruit(Operation.BALANCE, "apple", 100));
        storage.add(new Fruit(Operation.BALANCE, "orange", 100));
        storage.add(new Fruit(Operation.BALANCE, "pineapple", 100));
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add(System.lineSeparator() + "banana,100");
        expected.add(System.lineSeparator() + "apple,100");
        expected.add(System.lineSeparator() + "orange,100");
        expected.add(System.lineSeparator() + "pineapple,100");
        List<String> result = reportGeneratingService.createReport(storage);
        Assert.assertEquals(expected,result);
    }
}
