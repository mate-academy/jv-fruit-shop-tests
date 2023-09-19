package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.WriterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteServiceImplTest {
    private static final String OUTPUT_PATH = "src/main/java/resources/output.csv";
    private static final String REPORT = "fruit,quantity\n"
            + "banana,152\r\n"
            + "apple,90\r\n";
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriteServiceImpl();
    }

    @Test
    void invalidPath_NotOk() {
        assertThrows(FruitShopException.class, () -> writerService.writeToFile("", REPORT));
    }

    @Test
    void writeReport_Ok() {
        String actual = writerService.writeToFile(OUTPUT_PATH, REPORT);
        assertEquals(actual, REPORT);
    }
}
