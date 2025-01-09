package core.basesyntax;

import static core.basesyntax.db.Storage.modifyFruitStock;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_emptyStorage_notOk() {
        assertEquals("No data available for the report", reportGenerator.getReport());
    }

    @Test
    void getReport_storageWithOneRecord_Ok() {
        modifyFruitStock("orange", 10);
        assertEquals("fruit,quantity\norange,10",reportGenerator.getReport());
    }
}
