package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator generator;

    @BeforeAll
    static void beforeAll() {
        generator = new ReportGeneratorImpl();
    }

    @AfterEach
    void clearFruitStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    void getReport_fromEmptyStorage_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = generator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_fromStorageData_Ok() {
        FruitStorage.fruits.put("banana", 20);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20";
        String actual = generator.getReport();
        assertEquals(expected, actual);
    }
}
