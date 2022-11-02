package core.basesyntax;

import static core.basesyntax.db.FruitStorage.storage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.service.CreateReportService;
import core.basesyntax.service.impl.CreateReportServiceImpl;
import org.junit.jupiter.api.Test;

public class CreateReportServiceTest {
    private static final String REPORT_HEAD = "fruit,quantity";
    private static final String REPORT_SPLITTER = ",";
    private static final String REPORT_SEPARATOR = System.lineSeparator();
    private static final String TEST_FRUIT_NAME = "apple";
    private static final int TEST_QUANTITY = 20;
    private final CreateReportService createReportService = new CreateReportServiceImpl();

    @Test
    void createReport_correctData_Ok() {
        storage.put(TEST_FRUIT_NAME, TEST_QUANTITY);
        String expected = REPORT_HEAD + REPORT_SEPARATOR + TEST_FRUIT_NAME + REPORT_SPLITTER
                + TEST_QUANTITY;
        String actual = createReportService.createReport(storage);
        assertEquals(expected, actual);
        storage.clear();
    }

    @Test
    void createReport_NullData_notOk() {
        assertThrows(RuntimeException.class, () -> {
            createReportService.createReport(null);
        });
    }
}
