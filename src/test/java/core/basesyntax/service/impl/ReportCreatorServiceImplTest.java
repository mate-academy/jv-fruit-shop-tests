package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.EmptyStorageException;
import core.basesyntax.service.ReportCreatorService;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceImplTest {
    private static final String STANDARD_HEADER = "fruit,quantity" + System.lineSeparator();
    private static final String BANANA_FRUIT_NAME = "banana";
    private static final Integer BANANA_FRUIT_QUANTITY = 150;
    private static final String APPLE_FRUIT_NAME = "apple";
    private static final Integer APPLE_FRUIT_QUANTITY = 250;
    private static final String TANGERINE_FRUIT_NAME = "tangerine";
    private static final Integer TANGERINE_FRUIT_QUANTITY = 10;
    private static final String EMPTY_STORAGE_ERROR_MESSAGE = "Can't create a report, "
                                                            + "since The storage is empty!";
    private ReportCreatorService reportCreator;

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        reportCreator = new ReportCreatorServiceImpl();
    }

    @Test
    void createReport_ok() {
        String expected = createReport();
        String actual = reportCreator.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyStorage_notOk() {
        EmptyStorageException actualException = assertThrows(EmptyStorageException.class,
                () -> reportCreator.createReport());
        assertEquals(EMPTY_STORAGE_ERROR_MESSAGE, actualException.getMessage());
    }

    private String createReport() {
        Storage.storage.put(BANANA_FRUIT_NAME, BANANA_FRUIT_QUANTITY);
        Storage.storage.put(APPLE_FRUIT_NAME, APPLE_FRUIT_QUANTITY);
        Storage.storage.put(TANGERINE_FRUIT_NAME, TANGERINE_FRUIT_QUANTITY);
        StringBuilder report = new StringBuilder(STANDARD_HEADER);
        for (Map.Entry<String, Integer> entry : Storage.storage.entrySet()) {
            report.append(entry.getKey());
            report.append(",");
            report.append(entry.getValue());
            report.append(System.lineSeparator());
        }
        return report.toString();
    }
}
