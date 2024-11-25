package core.basesyntax.service;

import static core.basesyntax.storage.Storage.storageOfFruits;
import static junit.framework.TestCase.assertEquals;

import org.junit.jupiter.api.Test;

class CsvReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity";

    private CsvReportGenerator csvReportGenerator = new CsvReportGeneratorImpl();

    @Test
    public void generateReportWithClearStorage_NotOk() {
        storageOfFruits.clear();
        String actual = csvReportGenerator.generateReport();
        String expected = HEADER + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_Ok() {
        storageOfFruits.put("banana", 152);
        storageOfFruits.put("apple", 90);

        String actual = csvReportGenerator.generateReport();
        String expected = HEADER + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
