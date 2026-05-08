package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private final ReportGenerator report = new ReportGeneratorImpl();

    @AfterEach
    void clearStorage() {
        Storage.getStorage().clear();
    }

    @Test
    void generateReports_oneFruit_ok() {
        Storage.setStorage("banana", 20);

        String actual = report.getReport();

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator();

        assertEquals(expected, actual);
    }
}
