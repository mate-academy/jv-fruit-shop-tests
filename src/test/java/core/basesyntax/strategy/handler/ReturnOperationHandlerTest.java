package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static final String BANANA_FRUIT = "banana";
    private static final String NOT_EXISTED_FRUIT = "apple";
    private static final Integer VALID_BANANA_QUANTITY = 20;
    private static final Integer LESS_THAN_ZERO_BANANA_QUANTITY = -2;
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final OperationHandler operation = new ReturnOperationHandler(fruitDao);

    @BeforeEach
    void setUp() {
        fruitDao.add(BANANA_FRUIT, 30);
    }

    @AfterEach
    void tearDown() {
        fruitDao.getStorage().clear();
    }

    @Test
    void process_ReturnExistedFruit_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(BANANA_FRUIT, 50);
        operation.process(new FruitTransaction(
                FruitTransaction.Operation.RETURN, BANANA_FRUIT,
                VALID_BANANA_QUANTITY));
        assertEquals(expected, fruitDao.getStorage());
    }

    @Test
    void process_ReturnNotExistedFruit_NotOk() {
        assertThrows(NoSuchElementException.class, () ->
                operation.process(new FruitTransaction(
                    FruitTransaction.Operation.RETURN, NOT_EXISTED_FRUIT, 20)));
    }

    @Test
    void process_nullData_NotOk() {
        assertThrows(NullPointerException.class,
                () -> operation.process(null));
    }

    @Test
    void process_LessThanZeroQuantity_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> operation.process(
                        new FruitTransaction(
                                FruitTransaction.Operation.RETURN,
                                BANANA_FRUIT, LESS_THAN_ZERO_BANANA_QUANTITY)
                ));
    }
}
