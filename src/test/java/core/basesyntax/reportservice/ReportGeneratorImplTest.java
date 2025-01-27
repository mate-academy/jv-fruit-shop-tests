package core.basesyntax.reportservice;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorImplTest {
    private static final String HEADERS = "fruit,quantity" + System.lineSeparator();
    private static final String VALID_REPORT = HEADERS + "apple,50";
    private ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() {
        Storage.clearStorage();
    }

    @Test
    void getReport_ReportWithoutData_Ok() {
        String actual = reportGenerator.getReport();
        assertEquals(HEADERS,actual);
    }

    @Test
    void getReport_ReportWithValidData_Ok() {
        Storage.save("apple",50);
        String actual = reportGenerator.getReport();
        assertEquals(VALID_REPORT,actual);
    }
}