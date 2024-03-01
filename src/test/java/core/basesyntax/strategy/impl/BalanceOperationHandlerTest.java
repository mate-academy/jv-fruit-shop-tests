package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperationHandler(new FruitDaoImpl());
        Storage.fruitStorage.clear();
    }

    @Test
    void applyOperation_ValidTransaction_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE, "apple", 50);
        operationHandler.applyOperation(transaction);
        Map<String, Integer> actual = Map.of(transaction.getFruit(),
                transaction.getQuantity());
        Map<String, Integer> expected = Storage.fruitStorage;
        assertEquals(actual, expected, "Balance strategy is not working correctly!");

    }
}
