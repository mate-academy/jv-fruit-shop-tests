package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService report;
    private static FileService fileService;

    @BeforeClass
    public static void initialization() {
        fileService = new FileServiceImpl();
        report = new ReportToCsvService();
    }

    @After
    public void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    public void generateReport_Ok() {
        Storage.storage.put(new Fruit("orange"), 25);
        Storage.storage.put(new Fruit("apple"), 100);
        List<String> expected = List.of("fruit,quantity", "apple,100", "orange,25");
        report.generateReport();
        List<String> actual = fileService.read("src/main/resources/report.csv");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateReportEmptyStorage_Ok() {
        report.generateReport();
        List<String> readEmptyReport = fileService.read("src/main/resources/report.csv");
        int size = readEmptyReport.size();
        Assert.assertEquals(1, size);
    }
}
