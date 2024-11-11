package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    public void afterEachTest() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void getReport_validFruitQuantityInput_ok() {
        FruitStorage.fruitStorage.put("banana", 20);
        FruitStorage.fruitStorage.put("pineapple", 10);
        FruitStorage.fruitStorage.put("cocos", 40);
        String expected = """
                fruit,quantity
                banana,20
                pineapple,10
                cocos,40
                """;
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_zeroQuantityInput_ok() {
        FruitStorage.fruitStorage.put("banana", 0);
        FruitStorage.fruitStorage.put("pineapple", 0);
        FruitStorage.fruitStorage.put("cocos", 40);
        String expected = """
                fruit,quantity
                banana,0
                pineapple,0
                cocos,40
                """;
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_emptyStorage_ok() {
        String expected = "fruit,quantity\n";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
