package core.basesyntax;

import core.basesyntax.base.Storage;
import core.basesyntax.impl.ReportGeneratorImpl;
import core.basesyntax.service.ReportGenerator;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.fruitStorage = new LinkedHashMap<>();
    }

    @Test
    void getReport_EmptyStorage_ReturnsOnlyHeader() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        assertEquals(expectedReport, reportGenerator.getReport());
    }

    @Test
    void getReport_SingleEntry_ReturnsCorrectReport() {
        Storage.fruitStorage.put("banana", 10);
        String expectedReport = "fruit,quantity" + System.lineSeparator() + "banana,10"
                + System.lineSeparator();

        assertEquals(expectedReport, reportGenerator.getReport());
    }

    @Test
    void getReport_MultipleEntries_ReturnsFormattedReport() {
        Storage.fruitStorage.put("banana", 10);
        Storage.fruitStorage.put("apple", 20);

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,20" + System.lineSeparator();

        assertEquals(expectedReport, reportGenerator.getReport());
    }
}
