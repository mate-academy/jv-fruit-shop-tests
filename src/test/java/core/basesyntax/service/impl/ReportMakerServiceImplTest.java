package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportMakerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportMakerServiceImplTest {
    private final ReportMakerService reportMakerService = new ReportMakerServiceImpl();

    @BeforeEach
    void preparation() {
        Storage.getStorage().clear();
    }

    @Test
    void convert_validStorage_Ok() {
        Storage.getStorage().put("banana", 120);
        Storage.getStorage().put("apple", 46);
        String actual = reportMakerService.createReport();
        String expected = "fruits,quantity" + System.lineSeparator()
                        + "banana,120" + System.lineSeparator()
                        + "apple,46" + System.lineSeparator();
        assertEquals(expected, actual);;
    }

    @Test
    void convert_emptyStorage_Ok() {
        String actual = reportMakerService.createReport();
        String expected = "fruits,quantity" + System.lineSeparator();
        assertEquals(expected, actual);;
    }
}
