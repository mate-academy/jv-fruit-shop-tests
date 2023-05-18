package core.basesyntax.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.serviceimpl.ReportCreatorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportCreatorImplTest {
    @Test
    void createReport_Ok() {
        Storage.fruitsAndAmount.put("banana", 50);
        Storage.fruitsAndAmount.put("apple", 100);
        Storage.fruitsAndAmount.put("orange", 150);
        System.out.println(Storage.fruitsAndAmount);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,50"
                + System.lineSeparator()
                + "orange,150"
                + System.lineSeparator()
                + "apple,100";
        String actual = new ReportCreatorImpl().createReport(Storage.fruitsAndAmount);
        assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = new ReportCreatorImpl().createReport(Storage.fruitsAndAmount);
        assertEquals(expected, actual);
    }

    @Test
    void createReport_nullInput_NotOk() {
        assertThrows(
                NullPointerException.class, () -> new ReportCreatorImpl().createReport(null));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsAndAmount.clear();
    }
}
