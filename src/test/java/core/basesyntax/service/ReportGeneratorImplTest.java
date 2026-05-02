package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.basesyntax.db.StorageImpl;
import core.basesyntax.basesyntax.service.ReportGenerator;
import core.basesyntax.basesyntax.service.impl.ReportGeneratorImpl;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String HEADER = "fruit,quantity";
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_ORANGE = "orange";
    private static final String CONTAINS_10_APPLE = "apple,10";
    private static final String CONTAINS_20_BANANA = "banana,20";
    private static final String CONTAINS_10_ORANGE = "orange,10";
    private static final int EXPECTED_SIZE_1 = 1;
    private static final int EXPECTED_SIZE_3 = 3;
    private static final int QUANTITY_10 = 10;
    private static final int QUANTITY_20 = 20;
    private static final int INDEX_0 = 0;

    private ReportGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ReportGeneratorImpl();
    }

    @Test
    void generate_validData_ok() {
        StorageImpl storage = new StorageImpl();
        storage.setQuantity(FRUIT_APPLE, QUANTITY_10);

        List<String> result = generator.generate(storage);

        assertEquals(HEADER, result.get(INDEX_0));
        assertTrue(result.contains(CONTAINS_10_APPLE));
    }

    @Test
    void generate_multipleFruits_ok() {
        StorageImpl storage = new StorageImpl();
        storage.setQuantity(FRUIT_BANANA, QUANTITY_20);
        storage.setQuantity(FRUIT_ORANGE, QUANTITY_10);

        List<String> result = generator.generate(storage);

        assertEquals(EXPECTED_SIZE_3, result.size());
        assertTrue(result.contains(CONTAINS_20_BANANA));
        assertTrue(result.contains(CONTAINS_10_ORANGE));
    }

    @Test
    void generate_emptyData_onlyHeader() {
        StorageImpl storage = new StorageImpl();
        List<String> result = generator.generate(storage);

        assertEquals(EXPECTED_SIZE_1, result.size());
        assertEquals(HEADER, result.get(INDEX_0));
    }
}
