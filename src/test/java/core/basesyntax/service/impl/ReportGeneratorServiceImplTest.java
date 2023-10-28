package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGeneratorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceImplTest {
    private static ReportGeneratorService reportGenerator;
    private static final String TITLE = "fruit,quantity" + System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorServiceImpl();
    }

    @AfterEach
    void cleanStorage() {
        Storage.fruits.clear();
    }

    @Test
    void generate_emptyMap_Ok() {
        assertEquals(TITLE, reportGenerator.generate());
    }

    @Test
    void get_validGeneration_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        Storage.fruits.put("banana", 150);
        Storage.fruits.put("apple", 70);
        Storage.fruits.put("melon", 30);
        String expected = TITLE
                + "banana,150" + System.lineSeparator()
                + "apple,70" + System.lineSeparator()
                + "melon,30" + System.lineSeparator();
        assertEquals(expected, reportGenerator.generate());
    }
}
