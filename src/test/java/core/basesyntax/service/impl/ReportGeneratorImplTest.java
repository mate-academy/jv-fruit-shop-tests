package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static String expected;
    private static String actual;

    @BeforeClass
    public static void initialize() {
        Storage.fruitStorage.put("apple", 50);
        Storage.fruitStorage.put("banana", 90);
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana,90" + System.lineSeparator()
                + "apple,50" + System.lineSeparator();
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        actual = reportGenerator.fruitReport();
    }

    @Test
    public void fruitReport_makingReportFromMap_Ok() {
        assertEquals(expected, actual);
        Storage.fruitStorage.clear();
    }
}
