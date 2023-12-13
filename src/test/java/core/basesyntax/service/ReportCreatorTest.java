package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportCreatorTest {
    private static final String APPLE = "Apple";
    private static final String BANANA = "Banana";
    private static final String REPORT_HEADER = "fruit,quantity\n";
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreator();
    }

    @Test
    public void createReport_WithData_Ok() {
        FruitStorage.FRUITS.put(APPLE, 10);
        FruitStorage.FRUITS.put(BANANA, 20);
        String report = reportCreator.createReport();
        String expectedReport = REPORT_HEADER + APPLE + ",10\n" + BANANA + ",20";

        report = report.replaceAll("\\r\\n", "\n");
        expectedReport = expectedReport.replaceAll("\\r\\n", "\n");

        assertEquals(expectedReport, report);
    }

    @Test
    public void createReport_NoData_ThrowsRuntimeException_NotOk() {
        assertThrows(RuntimeException.class, () -> reportCreator.createReport());
    }
}
