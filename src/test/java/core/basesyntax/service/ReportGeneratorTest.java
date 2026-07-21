package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {

    @BeforeEach
    void setUp() {
        ShopStorage.storage.clear();
    }

    @Test
    void getReport_validStorage_ok() {
        ShopStorage.storage.put("banana", 20);
        ShopStorage.storage.put("apple", 100);

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        String actualReport = reportGenerator.getReport();

        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100";

        assertEquals(expectedReport.trim(), actualReport.trim(),
                "The generated report does not match the expected format.");
    }
}
