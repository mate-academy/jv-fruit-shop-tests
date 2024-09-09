package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    @Test
    public void testBuildReport() {
        Storage.quantities.put("Apple", 10);
        Storage.quantities.put("Banana", 20);
        Storage.quantities.put("Orange", 30);

        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
        String report = reportGenerator.buildReport();

        String[] expectedLines = new String[]{
                "fruit, quantity",
                "Apple,10",
                "Banana,20",
                "Orange,30"
        };
        String[] actualLines = report.split("\\r?\\n");
        Assertions.assertArrayEquals(expectedLines, actualLines);
    }
}
