package core.basesyntax.service.reporter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitQuantityDao;
import core.basesyntax.dao.FruitQuantityDaoImpl;
import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitReporterImplTest {
    private static final String REPORT_HEADER = "fruit,quantity";
    private static final int FIRST_VALID_QUANTITY = 20;
    private static final int SECOND_VALID_QUANTITY = 150;
    private static final String FIRST_VALID_FRUIT_NAME = "apple";
    private static final String SECOND_VALID_FRUIT_NAME = "banana";
    private static final String DATA_SEPARATOR = ",";
    private static FruitQuantityDao fruitQuantityDao;
    private static Reporter reporter;

    @BeforeAll
    static void beforeAll() {
        fruitQuantityDao = new FruitQuantityDaoImpl();
        reporter = new FruitReporterImpl(fruitQuantityDao);
    }

    @Test
    void createReport_validData_ok() {
        fruitQuantityDao.add(FIRST_VALID_FRUIT_NAME, FIRST_VALID_QUANTITY);
        fruitQuantityDao.add(SECOND_VALID_FRUIT_NAME, SECOND_VALID_QUANTITY);
        StringBuilder builder = new StringBuilder();
        builder
                .append(REPORT_HEADER).append(System.lineSeparator())
                .append(SECOND_VALID_FRUIT_NAME)
                .append(DATA_SEPARATOR).append(SECOND_VALID_QUANTITY)
                .append(System.lineSeparator())
                .append(FIRST_VALID_FRUIT_NAME).append(DATA_SEPARATOR).append(FIRST_VALID_QUANTITY);
        String expected = builder.toString();
        assertEquals(expected, reporter.createReport());
    }

    @Test
    void createReport_emptyData_ok() {
        assertEquals(REPORT_HEADER, reporter.createReport());
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitQuantity.clear();
    }
}
