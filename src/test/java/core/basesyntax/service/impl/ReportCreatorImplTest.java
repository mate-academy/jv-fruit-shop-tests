package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static final int bananaAmount = 100;
    private static final int grapeAmount = 40;
    private static final int appleAmount = 90;
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit GRAPE = new Fruit("grape");
    private static final Fruit APPLE = new Fruit("apple");
    private static final String EXPECTED = """
            fruit,quantity
            banana,100
            apple,90
            grape,40""";
    private static ReportCreator reportCreator;

    @BeforeAll
    static void beforeAll() {
        StorageDao storageDao = new StorageDaoImpl();
        reportCreator = new ReportCreatorImpl(storageDao);
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.put(BANANA, bananaAmount);
        Storage.fruits.put(GRAPE, grapeAmount);
        Storage.fruits.put(APPLE, appleAmount);
    }

    @Test
    void createReport_isOk() {
        String actualReport = reportCreator.createReport();

        assertEquals(EXPECTED, actualReport);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
