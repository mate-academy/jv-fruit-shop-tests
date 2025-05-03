package core.basesyntax.service;

import core.basesyntax.db.FruitStorageDao;
import core.basesyntax.db.FruitStorageDaoImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private static ReportGenerator reportGenerator;
    private static FruitStorageDao fruitStorageDao;
    private static Map<String, Integer> db;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        reportGenerator = new ReportGeneratorImpl(fruitStorageDao);
        // Get FruitStorageDaoImpl private DB tests
        try {
            Field field = fruitStorageDao.getClass().getDeclaredField("db");
            field.setAccessible(true);
            db = (Map<String, Integer>) field.get(fruitStorageDao);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void generate_validData_ok() {
        // Normal data
        db.clear();
        db.put("banana", 100);
        db.put("apple", 50);
        String[] expectedReport = {"fruit,quantity", "banana,100", "apple,50"};
        String[] actualReport = reportGenerator.generate();
        Assertions.assertArrayEquals(expectedReport, actualReport);
        // Check zero count
        db.put("pear", 0);
        actualReport = reportGenerator.generate();
        Assertions.assertArrayEquals(expectedReport, actualReport);
    }
}
