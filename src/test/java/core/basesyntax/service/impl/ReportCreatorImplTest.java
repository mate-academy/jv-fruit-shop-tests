package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeAll
    static void beforeAll() {
        reportCreator = new ReportCreatorImpl();
        Storage.storage.put("apple", 35);
    }

    @Test
    void reportCreator_correctStorage_ok() {
        Storage.storage.put("banana", 40);
        String expected = "fruit, quantity\nbanana,40\napple,35\n";
        assertEquals(expected, reportCreator.create());
    }
}
