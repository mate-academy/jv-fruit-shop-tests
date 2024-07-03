package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final OperationHandler operation = new PurchaseOperationHandler(fruitDao);

    @BeforeEach
    void setUp() {
        fruitDao.add("banana", 30);
    }

    @AfterEach
    void tearDown() {
        fruitDao.getStorage().clear();
    }

    @Test
    void process_ValidData_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        operation.process(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 20));
        assertEquals(expected, fruitDao.getStorage());
    }

    @Test
    void process_quantityMoreThanBalance_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> operation.process(
                        new FruitTransaction(
                                FruitTransaction.Operation.PURCHASE, "banana", 40)
                ));
    }

    @Test
    void process_nullData_NotOk() {
        assertThrows(NullPointerException.class, () -> operation.process(null));
    }

    @Test
    void process_LessThanZeroQuantity_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> operation.process(
                        new FruitTransaction(
                                FruitTransaction.Operation.PURCHASE, "banana", -2)
                ));
    }
}
