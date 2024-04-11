package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_nullData_notOk() {
        Storage.remainsOfFruits.put(null, null);
        Assert.assertThrows(NullPointerException.class, () ->
                reportGenerator.getReport());
    }

    @Test
    void getReport_correctReport_ok() {
        Storage.remainsOfFruits.put("banana", 152);
        Storage.remainsOfFruits.put("apple", 90);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator()
                + "banana,152";
        String actual = reportGenerator.getReport();
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.remainsOfFruits.clear();
    }
}
