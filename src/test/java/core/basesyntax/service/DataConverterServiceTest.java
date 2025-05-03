package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.DataConverterServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DataConverterServiceTest {
    private static DataConverterService dataConverterService;

    @BeforeAll
    static void beforeAll() {
        dataConverterService = new DataConverterServiceImpl();
    }

    @Test
    void convert_ValidLines_Ok() {
        List<String> parsedLines = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");

        List<FruitTransaction> expected = dataConverterService
                .convertToTransaction(parsedLines);
        assertEquals(8, expected.size());
    }

    @Test
    void convert_nullValue_NotOk() {
        assertThrows(NullPointerException.class,
                () -> dataConverterService.convertToTransaction(null));
    }

    @Test
    void convert_EmptyLine_NotOk() {
        List<String> parsedLines = List.of();
        List<FruitTransaction> expected = dataConverterService
                .convertToTransaction(parsedLines);
        assertTrue(expected.isEmpty());
    }

}
