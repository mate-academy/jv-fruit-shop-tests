package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReportCreatorServiceImplTest {
    private static final ReportCreatorService reportCreatorService = new ReportCreatorServiceImpl();

    @Test
    void create_validData_ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("apple", 20);
        String actual = reportCreatorService.create();
        String expected = """
                fruit,quantity\r
                banana,10\r
                apple,20""";
        assertEquals(expected,actual);
    }

    @Test
    void create_emptyData_ok() {
        String actual = reportCreatorService.create();
        String expected = "fruit,quantity";
        assertEquals(expected,actual);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
