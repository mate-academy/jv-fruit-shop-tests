package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.FruitReportServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitReportServiceImplTest {
    private static final String BANANA_FRUIT = "banana";
    private static final String APPLE_FRUIT = "apple";
    private static final int APPLE_QTY = 20;
    private static final int BANANA_QTY = 30;
    private final FruitReportService reportService = new FruitReportServiceImpl();

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void createReport_validData_Ok() {
        List<String> expectedOutput = new ArrayList<>(List.of("fruit,quantity",
                "banana,30",
                "apple,20"));
        Storage.storage.put(BANANA_FRUIT, BANANA_QTY);
        Storage.storage.put(APPLE_FRUIT, APPLE_QTY);
        Assertions.assertLinesMatch(expectedOutput, reportService.createReport());
    }

    @Test
    void createReport_emptyStorage_notOk() {
        RuntimeException reportException = assertThrows(RuntimeException.class,
                reportService::createReport);
        assertEquals("Can`t create report. Storage is empty", reportException.getMessage());
    }
}
