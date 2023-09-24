package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.impl.WriteServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WriteServiceImplTest {
    private static WriterService writerService;

    @BeforeAll
    static void beforeAll() {
        writerService = new WriteServiceImpl();
    }

    @Test
    void writeTo_invalidPath_NotOk() {
        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90")
                .append(System.lineSeparator());
        assertThrows(FruitShopException.class, () ->
                writerService.writeToFile("", report.toString()));
    }

    @Test
    void writeTo_validPath_Ok() {
        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90")
                .append(System.lineSeparator());
        String actual = writerService.writeToFile(
                "src/main/java/resources/output.csv", report.toString());
        assertEquals(actual, report.toString());
    }
}
