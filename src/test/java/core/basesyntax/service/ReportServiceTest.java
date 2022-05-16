package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.storage.FruitStorage;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void init() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void makeReportFromStorage_Ok() {
        List<String> expected = new ArrayList<>();
        expected.add("fruit,quantity");
        expected.add("banana,10");
        expected.add("orange,10");
        expected.add("apple,10");

        FruitStorage.fruits.put(new Fruit("banana"), 10);
        FruitStorage.fruits.put(new Fruit("orange"), 10);
        FruitStorage.fruits.put(new Fruit("apple"), 10);

        List<String> actual = reportService.makeReport(FruitStorage.fruits.entrySet());
        assertTrue(actual.size() == expected.size() && actual.containsAll(expected));
    }

    @Test(expected = RuntimeException.class)
    public void makeReportFromNullData_NorOk() {
        reportService.makeReport(null);
    }
}
