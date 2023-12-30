package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Database;
import core.basesyntax.db.impl.DatabaseDaoServiceImpl;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseDaoServiceTest {
    private static final String PRODUCT = "apple";
    private static final Integer INITIAL_PRODUCT_AMOUNT = 100;
    private static final Integer INCREASE_REDUCE_PRODUCT_AMOUNT = 20;
    private static final Integer INCREASED_PRODUCT_AMOUNT = INITIAL_PRODUCT_AMOUNT
            + INCREASE_REDUCE_PRODUCT_AMOUNT;
    private static final Integer REDUCED_PRODUCT_AMOUNT = INITIAL_PRODUCT_AMOUNT
            - INCREASE_REDUCE_PRODUCT_AMOUNT;
    private static final String SECOND_PRODUCT_NAME = "banana";
    private static final Integer SECOND_PRODUCT_AMOUNT = 200;
    private static final Map<String, Integer> NORMAL_DATA_MAP = Map.of(
            PRODUCT, INITIAL_PRODUCT_AMOUNT,
            SECOND_PRODUCT_NAME, SECOND_PRODUCT_AMOUNT);
    private DatabaseDaoServiceImpl databaseDaoService;

    @BeforeEach
    public void setUp() {
        databaseDaoService = new DatabaseDaoServiceImpl();
    }

    @Test
    public void put_normalData_ok() {
        databaseDaoService.put(PRODUCT, INITIAL_PRODUCT_AMOUNT);
        assertEquals(INITIAL_PRODUCT_AMOUNT, Database.database.get(PRODUCT));
    }

    @Test
    public void increaseAmount_normalData_ok() {
        Database.database.put(PRODUCT, INITIAL_PRODUCT_AMOUNT);
        databaseDaoService.increaseAmount(PRODUCT, INCREASE_REDUCE_PRODUCT_AMOUNT);
        assertEquals(INCREASED_PRODUCT_AMOUNT, Database.database.get(PRODUCT));
    }

    @Test
    public void reduceAmount_normalData_ok() {
        Database.database.put(PRODUCT, INITIAL_PRODUCT_AMOUNT);
        databaseDaoService.reduceAmount(PRODUCT, INCREASE_REDUCE_PRODUCT_AMOUNT);
        assertEquals(REDUCED_PRODUCT_AMOUNT, Database.database.get(PRODUCT));
    }

    @Test
    public void getProductAmount_normalData_ok() {
        Database.database.put(PRODUCT, INITIAL_PRODUCT_AMOUNT);
        assertEquals(Database.database.get(PRODUCT), databaseDaoService.getProductAmount(PRODUCT));
    }

    @Test
    public void getAll_normalData_ok() {
        Database.database.put(PRODUCT, INITIAL_PRODUCT_AMOUNT);
        Database.database.put(SECOND_PRODUCT_NAME, SECOND_PRODUCT_AMOUNT);
        assertEquals(NORMAL_DATA_MAP, databaseDaoService.getAll());
    }

    @AfterEach
    public void afterEach() {
        Database.database.clear();
    }
}
