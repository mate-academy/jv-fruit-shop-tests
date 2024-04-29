package fruit.shop.tests;

import fruit.shop.db.Storage;
import fruit.shop.model.Fruit;
import fruit.shop.service.ReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportTest {
    private static final ReportServiceImpl REPORT_SERVICE = new ReportServiceImpl();
    private static final fruit.shop.service.WriterServiceImpl WRITER_SERVICE
            = new fruit.shop.service.WriterServiceImpl();

    @AfterEach
    public void afterEachTest() {
        Storage.clear();
    }

    @Test
    public void getReport_ok() {
        Storage.put(Fruit.APPLE, 20);
        Storage.put(Fruit.BANANA, 50);
        String report = REPORT_SERVICE.generateReport();
        Assertions.assertTrue(report.contains("fruit,quantity"));
        Assertions.assertTrue(report.contains("apple,20"));
        Assertions.assertTrue(report.contains("banana,50"));
    }

    @Test
    public void writeReport_ok() {
        String path = "src/test/java/Resources/";
        String report = "test";
        Assertions.assertDoesNotThrow(() -> WRITER_SERVICE.saveReport(report, path));
    }
}
