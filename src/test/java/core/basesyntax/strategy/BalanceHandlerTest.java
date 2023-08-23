package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.IllegalQuantityException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;
    private static final Map<String, Integer> RESULT_MAP = Map.of(
            "apple", 10,
            "banana", 20);

    @BeforeEach
    void init() {
        Storage.fruitStorage.clear();
        balanceHandler = new BalanceHandler();
    }

    @Test
    void handleOperation_normalValue_Ok() {
        FruitTransaction fruitTransactionOne =
                new FruitTransaction(Operation.BALANCE, "apple", 10);
        FruitTransaction fruitTransactionTwo =
                new FruitTransaction(Operation.BALANCE, "banana", 20);

        balanceHandler.handleOperation(fruitTransactionOne);
        balanceHandler.handleOperation(fruitTransactionTwo);

        Assertions.assertEquals(Storage.fruitStorage, RESULT_MAP);
    }

    @Test
    void handleOperation_negativeValue_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, "apple", -10);

        Assertions.assertThrows(IllegalQuantityException.class,
                () -> balanceHandler.handleOperation(fruitTransaction));
    }
}
