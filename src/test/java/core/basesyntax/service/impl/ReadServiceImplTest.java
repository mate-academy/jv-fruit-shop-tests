package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.ReadService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadServiceImplTest {
    private static final String INVALID_PATH = "src/main/java/resources/invalid.csv";
    private static final String VALID_PATH = "src/main/java/resources/input.csv";
    private static ReadService readService;
    private static List<String> expectedList;

    @BeforeAll
    static void beforeAll() {
        readService = new ReadServiceImpl();
        expectedList = List.of("type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50");
    }

    @Test
    void readInputData_Ok() {
        List<String> actualList = readService.readInputData(VALID_PATH);
        assertEquals(expectedList, actualList);
    }

    @Test
    void invalidPath_notOk() {
        assertThrows(FruitShopException.class, () -> readService.readInputData(INVALID_PATH));
    }
}
