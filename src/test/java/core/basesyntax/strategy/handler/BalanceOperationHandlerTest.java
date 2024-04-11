package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static FruitTransaction validFruitTransaction;
    private static BalanceOperationHandler balanceOperationHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        validFruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 7);
        fruitDao = new FruitDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(fruitDao);
    }

    @Test
    void process_ValidData_Ok() {
        Map<String, Integer> expected = Map.of("apple", 7);
        balanceOperationHandler.process(validFruitTransaction);
        Map<String, Integer> actual = Storage.fruitStorage;
        assertEquals(expected, actual);
    }

    @Test
    void process_NullData_notOk() {
        assertThrows(NullPointerException.class, () ->
                balanceOperationHandler.process(null));
    }
}
