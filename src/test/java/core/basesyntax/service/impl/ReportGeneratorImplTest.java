package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    public void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    public void tearDown() {
        Storage.clear();
    }

    @Test
    public void getReport_validStorageData_ok() {
        Storage.updateQuantity("banana", 20);
        Storage.updateQuantity("apple", 15);

        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,15" + System.lineSeparator();

        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}


