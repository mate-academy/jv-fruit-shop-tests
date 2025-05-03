package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.WriteService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteServiceImplTest {
    private static final String REPORT_FILE_PATH =
            "src/test/java/core/basesyntax/resources/report.csv";
    private static final String REPORT = "fruit,quantity\n"
            + "banana,152\n"
            + "apple,90\n";
    private static WriteService writeService;

    @BeforeAll
    static void beforeAll() {
        writeService = new WriteServiceImpl();
    }

    @Test
    void invalidFilePath() {
        assertThrows(FruitShopException.class,
                () -> writeService.writeReport(REPORT, ""));
    }

    @Test
    void writeReport_Ok() {
        String actual = writeService.writeReport(REPORT, REPORT_FILE_PATH);
        String expect = REPORT;
        assertEquals(expect, actual);
    }
}
