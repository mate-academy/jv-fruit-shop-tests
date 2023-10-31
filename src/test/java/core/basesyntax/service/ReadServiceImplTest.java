package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.impl.ReadServiceImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadServiceImplTest {
    private static List<String> expectedList;
    private static ReadService readService;

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
    void readData_input_Ok() {
        List<String> actualList = readService.readInputData("src/main/java/resources/input.csv");
        assertEquals(expectedList, actualList);
    }

    @Test
    void readData_invalidPath_notOk() {
        assertThrows(FruitShopException.class, () ->
                readService.readInputData("src/main/java/resources/invalid.csv"));
    }
}
