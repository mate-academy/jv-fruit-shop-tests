package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final ReportService reportService = new ReportServiceImpl();
    private static final String VALID_KEY_1 = "apple";
    private static final String VALID_KEY_2 = "banana";
    private static final Integer VALID_VALUE_1 = 90;
    private static final Integer VALID_VALUE_2 = 80;
    private static final String EXPECTED_RESULT = "apple, 90" + System.lineSeparator()
            + "banana, 80" + System.lineSeparator();

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void createReport_ValidData_Ok() {
        storage.put(VALID_KEY_1,VALID_VALUE_1);
        storage.put(VALID_KEY_2,VALID_VALUE_2);
        assertEquals(EXPECTED_RESULT, reportService.createReport());
    }
}
