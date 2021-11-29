package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService report;
    private static FileServiceImpl fileService;

    @BeforeClass
    public static void beforeClass() {
        fileService = new FileServiceImpl();
        report = new ReportToCsvService();
        Storage.storage.put(new Fruit("orange"), 25);
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @Test
    public void generateReport_Ok() {
        List<String> expected = List.of("fruit,quantity", "apple,100", "orange,25");
        report.generateReport();
        List<String> actual = fileService.read("src/main/resources/report.csv");
        Assert.assertEquals(expected, actual);
    }
}
