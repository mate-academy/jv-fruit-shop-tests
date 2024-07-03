package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final OperationHandler operation = new BalanceOperationHandler(fruitDao);

    @AfterEach
    void tearDown() {
        fruitDao.getStorage().clear();
    }

    @Test
    void process_ValidData_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 30);
        operation.process(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 30));
        assertEquals(expected, fruitDao.getStorage());
    }

    @Test
    void process_nullData_NotOk() {
        assertThrows(NullPointerException.class,
                () -> operation.process(null));
    }
}
