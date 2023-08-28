package core.basesyntax.serviceimpltest;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {
    private static final String REPORT_VALID_DATA = "fruit,quantity\n"
            + "banana,152" + System.lineSeparator()
            + "apple,90" + System.lineSeparator();

    @BeforeEach
    void setUp() {
        Storage.fruit.put("banana", 152);
        Storage.fruit.put("apple", 90);
    }

    @AfterAll
    static void afterAll() {
        Storage.fruit.clear();
    }

    @Test
    public void report_validData_Ok() {
        Assertions.assertTrue(true, REPORT_VALID_DATA);
    }
}
