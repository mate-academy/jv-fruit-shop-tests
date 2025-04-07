package core.basesyntax.reportgenerator;

import static org.junit.Assert.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.clearStorage();
    }

    @Test
    void getReport_withMultipleFruits_returnsCorrectReport() {
        Storage.putFruit("banana", 20);
        Storage.putFruit("apple", 50);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,50";

        String actualReport = reportGenerator.getReport();

        assertEquals(expected, actualReport);
    }

    @Test
    void getReport_emptyStorage_returnsOnlyHeader() {
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, reportGenerator.getReport());
    }
}
