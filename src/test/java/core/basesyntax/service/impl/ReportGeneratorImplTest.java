package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitRepository;
import core.basesyntax.dao.impl.FruitRepositoryImpl;
import core.basesyntax.db.Database;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final Map<String, Integer> db = Database.storage;
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        FruitRepository fruitRepository = new FruitRepositoryImpl();
        reportGenerator = new ReportGeneratorImpl(fruitRepository);
    }

    @AfterEach
    void tearDown() {
        db.clear();
    }

    @Test
    void generateReport_generate_ok() {
        db.put("banana", 20);
        String actual = reportGenerator.generateReport();
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,20";
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_emptyStorage_ok() {
        String actual = reportGenerator.generateReport();
        String expected = "fruit,quantity"
                + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
