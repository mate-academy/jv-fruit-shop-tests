package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportCreatorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceImplTest {
    public static final String FIRST_KEY = "dummy1";
    public static final String SECOND_KEY = "dummy2";
    private static ReportCreatorService reportCreatorService;

    @BeforeAll
    static void beforeAll() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.put(FIRST_KEY, 3);
        Storage.fruitStorage.put(SECOND_KEY, 0);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void createReport_validData_ok() {
        String expectedReportData = "fruit,quantity\n"
                + FIRST_KEY + "," + Storage.fruitStorage.get(FIRST_KEY) + "\n"
                + SECOND_KEY + "," + Storage.fruitStorage.get(SECOND_KEY) + "\n";
        String actualReportData = reportCreatorService.createReport();
        assertEquals(expectedReportData, actualReportData);
    }
}
