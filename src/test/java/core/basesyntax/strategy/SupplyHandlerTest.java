package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.IllegalQuantityException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final BalanceHandler balanceHandler = new BalanceHandler();
    private static final SupplyHandler supplyHandler = new SupplyHandler();
    private static final Map<String, Integer> RESULT_MAP = Map.of("apple", 20);

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    void handleOperation_normalValue_Ok() {
        FruitTransaction fruitTransactionBalance
                = new FruitTransaction(Operation.BALANCE, "apple", 10);
        FruitTransaction fruitTransactionSupply
                = new FruitTransaction(Operation.SUPPLY, "apple", 10);

        balanceHandler.handleOperation(fruitTransactionBalance);
        supplyHandler.handleOperation(fruitTransactionSupply);

        Assertions.assertEquals(Storage.fruitStorage, RESULT_MAP);
    }

    @Test
    void handleOperation_negativeValue_Ok() {
        FruitTransaction fruitTransactionBalance
                = new FruitTransaction(Operation.BALANCE, "apple", 10);
        FruitTransaction fruitTransactionSupply
                = new FruitTransaction(Operation.SUPPLY, "apple", -10);

        balanceHandler.handleOperation(fruitTransactionBalance);
        Assertions.assertThrows(IllegalQuantityException.class,
                () -> supplyHandler.handleOperation(fruitTransactionSupply));
    }
}
