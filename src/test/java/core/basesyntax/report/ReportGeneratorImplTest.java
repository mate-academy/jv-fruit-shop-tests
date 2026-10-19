package core.basesyntax.report;

import static org.junit.Assert.assertEquals;

import core.basesyntax.reportgenerator.ReportGenerator;
import core.basesyntax.reportgenerator.ReportGeneratorImpl;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @Before
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.storage.clear();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void getReport_emptyStorage_returnsHeaderOnly() {
        String expected = "fruit,quantity";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_storageWithEntries_returnsCorrectReport() {
        Storage.storage.put("banana", 10);
        Storage.storage.put("apple", 5);
        Storage.storage.put("orange", 20);

        String expected = String.join(System.lineSeparator(),
                "fruit,quantity",
                "apple,5",
                "banana,10",
                "orange,20");

        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
