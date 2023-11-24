package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import core.basesyntax.service.impl.ReportCreatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportCreatorTest {
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        Storage.storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void createReport_EmptyStorage_HeaderRowOnly() {
        String expectedReport = "fruit,quantity";
        String actualReport = reportCreator.createReport();
        assertEquals(expectedReport, actualReport, "Report should be the header row only");
    }

    @Test
    void createReport_NonEmptyStorage_ReportMatchesExpectedData() {
        Storage.storage.put("apple", 50);
        Storage.storage.put("banana", 20);
        Storage.storage.put("orange", 30);
        String expectedReport =
                "fruit,quantity" + System.lineSeparator()
                        + "banana,20" + System.lineSeparator()
                        + "orange,30" + System.lineSeparator()
                        + "apple,50";

        String actualReport = reportCreator.createReport();
        assertEquals(expectedReport, actualReport, "Report should match the expected data");
    }
}
