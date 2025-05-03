package core.basesyntax.service.impl;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.ReadService;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReadServiceImplTest {
    private static final String WRONG_PATH =
            "src/test/java/core/basesyntax/resources/wrong.csv";
    private static final String CORRECT_PATH =
            "src/test/java/core/basesyntax/resources/input.csv";
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
        List<String> actualList = readService.readInputData(CORRECT_PATH);
        Assert.assertEquals(expectedList, actualList);
    }

    @Test
    void wrongPath_notOk() {
        Assert.assertThrows(FruitShopException.class,
                () -> readService.readInputData(WRONG_PATH));
    }
}
