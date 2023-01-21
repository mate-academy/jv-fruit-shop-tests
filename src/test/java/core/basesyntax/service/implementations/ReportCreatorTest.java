package core.basesyntax.service.implementations;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportCreatorTest {
    private static final String REPORT_TEMPLATE = "fruit,quantity";
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreator();
        Storage.fruits.put("banana", 23);
        Storage.fruits.put("apple", 10);
    }

    @Test
    public void provideReport_ok() {
        String expectedString = REPORT_TEMPLATE + System.lineSeparator() + "banana,23"
                + System.lineSeparator() + "apple,10";
        String actualString = reportCreator.provideReport(Storage.fruits);
        Assertions.assertEquals(expectedString, actualString);
    }

    @Test
    public void provideReport_emptyStorage_ok() {
        String actualString = reportCreator.provideReport(new HashMap<>());
        Assertions.assertEquals(REPORT_TEMPLATE, actualString);
    }

    @Test
    public void provideReport_nullStorage_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> reportCreator.provideReport(null));
    }
}
