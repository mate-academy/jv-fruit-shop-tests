package core.basesyntax.reportgenerator;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private static final Storage storage = new Storage();
    private static final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @AfterEach
    public void afterEach() {

        storage.getDataStorageMapOrDefault("randomFruit").clear();
    }

    @Test
    void getReport_dataToReportIsNull_notOk() {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        Assertions.assertThrows(RuntimeException.class, () -> reportGenerator.getReport(null));
    }

    @Test
    void getReport_generateReport_ok() {
        storage.getDataStorageMapOrDefault("orange").put("orange",20);
        storage.getDataStorageMapOrDefault("banana").put("banana",10);
        storage.getDataStorageMapOrDefault("apple").put("apple",50);
        String result = "fruit,quantity" + System.lineSeparator()
                + "orange,20" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,50" + System.lineSeparator();
        Assertions.assertEquals(result, reportGenerator
                .getReport(storage));
    }

    @Test
    void getReport_isOverHeadPresent_ok() {
        storage.getDataStorageMapOrDefault("banana").put("banana",10);
        String report = reportGenerator.getReport(storage);
        List<String> listFromReport = report.lines().toList();
        Assertions.assertEquals("fruit,quantity",
                listFromReport.get(0));
    }
}
