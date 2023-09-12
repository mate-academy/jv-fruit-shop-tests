package core.basesyntax.service.implementations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportCreator;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static ReportCreator creator;

    @BeforeAll
    static void beforeAll() {
        creator = new ReportCreatorImpl();
        Storage.storage.put("apple", 200);
    }

    @Test
    void create_CorrectStorage_ok() {
        Storage.storage.put("banana", 500);
        String expected = "fruit,quantity\nbanana,500\napple,200\n";
        assertEquals(expected, creator.create());
    }
}
