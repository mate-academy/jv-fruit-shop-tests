package core.basesyntax.service.impl;

import static core.basesyntax.service.impl.TestConstants.APPLE_TRANSACTION_RESULT_EXAMPLE;
import static core.basesyntax.service.impl.TestConstants.BANANA_TRANSACTION_RESULT_EXAMPLE;
import static core.basesyntax.service.impl.TestConstants.DEFAULT_RESULT_HEADER;
import static core.basesyntax.service.impl.TestConstants.FRUIT_NAME_INDEX_IN_RESULT;
import static core.basesyntax.service.impl.TestConstants.FRUIT_QUANTITY_INDEX_IN_RESULT;
import static core.basesyntax.service.impl.TestConstants.RESULT_SEPARATOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGeneratorImpl reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
    }

    @Test
    void generate_validInput_ok() {
        String[] bananaFruitResult = BANANA_TRANSACTION_RESULT_EXAMPLE.split(RESULT_SEPARATOR);
        String[] appleFruitResult = APPLE_TRANSACTION_RESULT_EXAMPLE.split(RESULT_SEPARATOR);
        Storage.storage.put(bananaFruitResult[FRUIT_NAME_INDEX_IN_RESULT],
                Integer.parseInt(bananaFruitResult[FRUIT_QUANTITY_INDEX_IN_RESULT]));
        Storage.storage.put(appleFruitResult[FRUIT_NAME_INDEX_IN_RESULT],
                Integer.parseInt(appleFruitResult[FRUIT_QUANTITY_INDEX_IN_RESULT]));
        String expected = new StringBuilder(DEFAULT_RESULT_HEADER)
                .append(System.lineSeparator())
                .append(BANANA_TRANSACTION_RESULT_EXAMPLE)
                .append(System.lineSeparator())
                .append(APPLE_TRANSACTION_RESULT_EXAMPLE)
                .append(System.lineSeparator())
                .toString();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }
}
