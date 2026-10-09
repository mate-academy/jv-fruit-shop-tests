package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {

    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Test
    void getReport_validReport_ok() {
        Storage.getFruits().clear();
        Storage.getFruits().add(new Fruit("banana", 10));
        String report = reportGenerator.getReport();
        Assertions.assertNotNull(report);
        Assertions.assertTrue(report.contains("fruit,quantity"));
        Assertions.assertTrue(report.contains("banana,10"));
    }

    @Test
    void getReport_reportIsEmpty_ok() {
        Storage.getFruits().clear();
        String report = reportGenerator.getReport();

        Assertions.assertEquals("fruit,quantity", report);
    }
}
