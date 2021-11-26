package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_test_ok() {
        Storage.fruits.clear();
        Storage.fruits.add(new Fruit("banana", 207));
        Storage.fruits.add(new Fruit("apple", 90));
        List<String> actual;
        actual = reportService.createReport();
        List<String> expectedFirstList = List.of("fruit,quantity",
                "banana,207", "apple,90");
        assertEquals(expectedFirstList, actual);

        Storage.fruits.clear();
        Storage.fruits.add(new Fruit("banana", 120));
        Storage.fruits.add(new Fruit("apple", 80));
        actual = reportService.createReport();
        List<String> expectedSecondList = List.of("fruit,quantity",
                "banana,120", "apple,80");
        assertEquals(expectedSecondList, actual);

        Storage.fruits.clear();
        Storage.fruits.add(new Fruit("banana", 152));
        Storage.fruits.add(new Fruit("apple", 90));
        Storage.fruits.add(new Fruit("pineapple", 20));
        actual = reportService.createReport();
        List<String> expectedThirdList = List.of("fruit,quantity",
                "banana,152", "apple,90", "pineapple,20");
        assertEquals(expectedThirdList, actual);

        Storage.fruits.clear();
    }
}
